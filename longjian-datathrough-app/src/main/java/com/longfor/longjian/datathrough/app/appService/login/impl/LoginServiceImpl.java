package com.longfor.longjian.datathrough.app.appService.login.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.longfor.longjian.common.base.LjBaseResponse;
import com.longfor.longjian.datathrough.app.appService.login.LoginService;
import com.longfor.longjian.datathrough.app.appService.murphy.MurphyService;
import com.longfor.longjian.datathrough.app.util.DateUtil;
import com.longfor.longjian.datathrough.app.util.HexPasswordUtil;
import com.longfor.longjian.datathrough.app.util.IpUtil;
import com.longfor.longjian.datathrough.app.util.LFResultBean;
import com.longfor.longjian.datathrough.app.vo.AppLoginResultVo;
import com.longfor.longjian.datathrough.app.vo.SessionVo;
import com.longfor.longjian.datathrough.app.vo.TokenVo;
import com.longfor.longjian.datathrough.app.vo.UserVo;
import com.longfor.longjian.datathrough.domain.innerService.LoginFailLogService;
import com.longfor.longjian.datathrough.domain.innerService.LoginSuccessLogService;
import com.longfor.longjian.datathrough.domain.innerService.UserService;
import com.longfor.longjian.datathrough.dto.LoginDto;
import com.longfor.longjian.datathrough.dto.LoginMurphyOneDto;
import com.longfor.longjian.datathrough.dto.LoginMurphyThreeDto;
import com.longfor.longjian.datathrough.dto.LoginMurphyTwoDto;
import com.longfor.longjian.datathrough.po.LoginFailLog;
import com.longfor.longjian.datathrough.po.LoginSuccessLog;
import com.longfor.longjian.datathrough.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wang on 2018/11/18.
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    //墨菲登录接口一
    @Value("${murphy.login.interfaceOne}")
    private String interfaceOne;

    //墨菲登录接口二
    @Value("${murphy.login.interfaceTwo}")
    private String interfaceTwo;

    //墨菲登录接口三
    @Value("${murphy.login.interfaceThree}")
    private String interfaceThree;

    //地产租户id
    @Value("${murphy.system.tenantTriggerId}")
    private String tenantTriggerId;

    /**
     * 域名
     */
    @Value("${longjian.domain}")
    private String daoMain;

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate dataThroughRedis;

    @Value("${murphy.encrypt.modulus}")
    private String pubKey;

    @Resource
    private LoginSuccessLogService loginSuccessLogService;

    @Resource
    private LoginFailLogService loginFailLogService;

    @Resource
    private MurphyService murphyService;

    //墨菲系统token
    @Value("${murphy.system.token}")
    private String murphtSystemToken;


    @Override
    public LjBaseResponse pcLogin(LoginDto dto, HttpServletRequest request, HttpServletResponse response) {

        LFResultBean lfResultBean=loginMurphy(dto);

        if ("0".equals(lfResultBean.getResult())) {//验证成功

            log.debug("PC端调用墨菲登录接口返回值:"+JSONObject.toJSONString(lfResultBean.getData()));

            JSONObject resutJson = JSONObject.parseObject(JSONObject.toJSONString(lfResultBean.getData()));

            String loginIp = IpUtil.getIp(request);//获取请求IP

            int maxAge = dto.getRemember_me() > 0 ? 2592000 : 14400;//缓存 时间 秒

            String reason="";//登录失败原因

            if ("1".equals(resutJson.getString("resultType"))) {//得到预期结果 登录成功

                String loginName = resutJson.getString("loginName");//账号

                String murphyToken = resutJson.getString("murphyToken");//生成的墨菲token

                User u = userService.getUserByMurphyLoginName(loginName);//根据墨菲账号  查找映射的龙建用户

                Map<String,Object>dataMap=new HashMap<>();

                dataMap.put("group_id",u.getGroupId());

                if(u==null){
                    reason=dto.getUser_name()+"在龙建没有找到对应的映射用户数据";
                    writeFailLoginLog(0,dto.getUser_name(),dto.getPassword(),reason,loginIp);
                    return new LjBaseResponse(1, reason,null);
                }
                //设置用户登录状态
                setLoginStaus(u.getUserId(),u.getGroupId(),maxAge,u.getUserName(),murphyToken,request);

                //写入登录成功日志
                writeSuccessLoginLog(u.getUserId(),loginIp);

                //更新登录IP
                updateUserLoginIp(u.getUserId(),loginIp);

                return new LjBaseResponse(0,"登录成功",JSON.toJSONString(dataMap));
            } else if ("2".equals(resutJson.getString("resultType"))) {//存在不确定因素 需要附加信息校验 验证完成返回结果

                return new LjBaseResponse(0,resutJson);

            } else if ("3".equals(resutJson.getString("resultType"))) {//存在不确认因素 选择账号所属租户 选择完成返回结果

                return new LjBaseResponse(0,resutJson);

            }
        } else {
            return new LjBaseResponse(1, lfResultBean.getMessage(),"");
        }
        return null;
    }

    @Override
    public LjBaseResponse appLogin(LoginDto dto, HttpServletRequest request, HttpServletResponse response) {

        LFResultBean lfResultBean=loginMurphy(dto);

        if ("0".equals(lfResultBean.getResult())) {//验证成功

            log.debug("APP端调用墨菲登录接口返回值:"+JSONObject.toJSONString(lfResultBean.getData()));

            JSONObject resutJson = JSONObject.parseObject(JSONObject.toJSONString(lfResultBean.getData()));

            String loginIp = IpUtil.getIp(request);//获取请求IP

            int maxAge = 2592000;//缓存 时间 秒

            String reason="";//登录失败原因

            if ("1".equals(resutJson.getString("resultType"))) {//得到预期结果 登录成功

                String loginName = resutJson.getString("loginName");//账号

                String murphyToken = resutJson.getString("murphyToken");//生成的墨菲token

                User u = userService.getUserByMurphyLoginName(loginName);//根据墨菲账号  查找映射的龙建用户

                if(u==null){
                    reason=dto.getUser_name()+"在龙建没有找到对应的映射用户数据";
                    writeFailLoginLog(0,dto.getUser_name(),dto.getPassword(),reason,loginIp);
                    return new LjBaseResponse(1, reason,null);
                }
                //设置用户登录状态
                setLoginStaus(u.getUserId(),u.getGroupId(),maxAge,u.getUserName(),murphyToken,request);

                //写入登录成功日志
                writeSuccessLoginLog(u.getUserId(),loginIp);

                //更新登录IP
                updateUserLoginIp(u.getUserId(),loginIp);

                return new LjBaseResponse(0,"登录成功",appLoginResult(murphyToken,u));
            } else if ("2".equals(resutJson.getString("resultType"))) {//存在不确定因素 需要附加信息校验 验证完成返回结果

                return new LjBaseResponse(0,resutJson);

            } else if ("3".equals(resutJson.getString("resultType"))) {//存在不确认因素 选择账号所属租户 选择完成返回结果

                return new LjBaseResponse(0,resutJson);

            }
        } else {
            return new LjBaseResponse(1, lfResultBean.getMessage(),"");
        }


        return null;
    }

    /**
     * 拼装app端登录成功返回值
     * @param murphyToken
     * @param u
     * @return
     */
    private String appLoginResult(String murphyToken,User u){
        UserVo userVo=new UserVo();
        userVo.setId(u.getUserId());
        userVo.setReal_name(u.getRealName());
        userVo.setUser_name(u.getUserName());
        userVo.setEmail(u.getEmail());
        userVo.setMobile(u.getMobile());
        userVo.setUpdate_at(DateUtil.dateToStamp(u.getUpdateAt()));
        userVo.setDelete_at(DateUtil.dateToStamp(u.getDeleteAt()));

        AppLoginResultVo appLoginResultVo=new AppLoginResultVo();
        appLoginResultVo.setToken(murphyToken);
        appLoginResultVo.setUser(userVo);
        return JSON.toJSONString(appLoginResultVo);
    }


    /**
     * 调用 墨菲登录接口
     * @param dto
     * @return
     */
    private LFResultBean loginMurphy(LoginDto dto){

        LFResultBean lfResultBean=null;

        if(dto.getLoginType()==1) {//类型是1 调用墨菲登录接口一

            LoginMurphyOneDto loginMurphyOneDto = new LoginMurphyOneDto();
            loginMurphyOneDto.setEncryptPassword(HexPasswordUtil.getMurphyShaPass(dto.getPassword(), pubKey));
            loginMurphyOneDto.setLoginName(dto.getUser_name());
            loginMurphyOneDto.setProjectKey("");
            loginMurphyOneDto.setExpandDataType(0);
            loginMurphyOneDto.setRsaType(2);
            loginMurphyOneDto.setTenantTriggerId(Long.parseLong(tenantTriggerId));

            lfResultBean =murphyService.baseMurphy(murphtSystemToken,loginMurphyOneDto);

        }else if(dto.getLoginType()==2){//类型是2 调用墨菲登录接口二
            LoginMurphyTwoDto loginMurphyTwoDto=new LoginMurphyTwoDto();
            loginMurphyTwoDto.setAdditionalCheckKey(dto.getAdditionalCheckKey());
            loginMurphyTwoDto.setCredentials_no(dto.getCredentials_no());
            loginMurphyTwoDto.setEmail(dto.getEmail());
            loginMurphyTwoDto.setPhone(dto.getPhone());

            lfResultBean =murphyService.baseMurphyAdditionalCheck(murphtSystemToken,loginMurphyTwoDto);

        }else if(dto.getLoginType()==3){//类型是3 调用墨菲登录接口三
            LoginMurphyThreeDto loginMurphyThreeDto=new LoginMurphyThreeDto();
            loginMurphyThreeDto.setSelectTenantId(dto.getSelectTenantId());
            loginMurphyThreeDto.setAdditionalCheckKey(dto.getAdditionalCheckKey());

            lfResultBean =murphyService.baseMurphySelectTenant(murphtSystemToken,loginMurphyThreeDto);
        }
        return lfResultBean;

    }

    /**
     * 设置登录状态
     * @param uid
     * @param groupId
     * @param maxAge
     * @param userName
     */
    private void setLoginStaus(int uid,int groupId,int maxAge,String userName,String murphyToken,HttpServletRequest request){

        TokenVo tokenVo=new TokenVo();

        tokenVo.setUid(uid);
        tokenVo.setUsername(userName);
        tokenVo.setRootTeamId(groupId);
        tokenVo.setToken(murphyToken);
        tokenVo.set_permanent(true);

        SessionVo sessionVo=new SessionVo();
        sessionVo.setPath("/");
        sessionVo.setDomain(daoMain);
        sessionVo.setMaxAge(maxAge);
        sessionVo.setSecure(false);
        sessionVo.setHttpOnly(false);

        tokenVo.setSessionVo(sessionVo);

        dataThroughRedis.opsForValue().set(("session_"+murphyToken),JSON.toJSONString(tokenVo),maxAge);
        dataThroughRedis.opsForValue().set(("zjuc:skey:"+murphyToken),uid,maxAge);
        dataThroughRedis.opsForValue().set(("zjuc:user_id_"+uid+":"+murphyToken),uid,maxAge);

    }

    /**
     * 写入登录成功日志
     * @param uid
     * @param loginIp
     */
    private void  writeSuccessLoginLog(int uid,String loginIp){

        LoginSuccessLog loginSuccessLog=new LoginSuccessLog();
        loginSuccessLog.setLoginIp(loginIp);
        loginSuccessLog.setUserId(uid);
        loginSuccessLog.setCreateAt(new Date());
        loginSuccessLog.setUpdateAt(new Date());

        loginSuccessLogService.insert(loginSuccessLog);

    }

    /**
     * 登录成功后 修改上一次登录Ip
     * @param uid
     * @param loginIp
     */
    private void updateUserLoginIp(int uid,String loginIp){

        User u=new User();
        u.setUserId(uid);
        u.setLastLoginIp(loginIp);
        u.setLastLoginTime(new Long(System.currentTimeMillis()).intValue());
        userService.updateUser(u);

    }

    /**
     * 写入登录失败日志
     * @param uid
     * @param userName
     * @param reason
     * @param loginIp
     */
    private void  writeFailLoginLog(int uid,String userName,String password,String reason,String loginIp){

        LoginFailLog loginFailLog=new LoginFailLog();
        loginFailLog.setIp(loginIp);
        loginFailLog.setPassword(password);
        loginFailLog.setReason(reason);
        loginFailLog.setUserId(uid);
        loginFailLog.setUserName(userName);
        loginFailLog.setCreateAt(new Date());
        loginFailLog.setUpdateAt(new Date());
        loginFailLogService.insert(loginFailLog);
    }
}

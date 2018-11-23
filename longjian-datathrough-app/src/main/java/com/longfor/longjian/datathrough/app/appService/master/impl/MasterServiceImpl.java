package com.longfor.longjian.datathrough.app.appService.master.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.longfor.longjian.datathrough.app.appService.master.MasterService;
import com.longfor.longjian.datathrough.domain.innerService.AdptProjService;
import com.longfor.longjian.datathrough.domain.innerService.MirrorPhaseCdcService;
import com.longfor.longjian.datathrough.domain.innerService.RelLhCompanyToCompanyService;
import com.longfor.longjian.datathrough.po.AdptProj;
import com.longfor.longjian.datathrough.po.MirrorPhaseCdc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Wang on 2018/11/17.
 */
@Service
@Slf4j
public class MasterServiceImpl implements MasterService{

    @Value("${master.msg.mdmSuccess}")
    private String mdmSuccess;

    @Value("${master.msg.mdmError}")
    private String mdmError;

    @Resource
    private RelLhCompanyToCompanyService relLhCompanyToCompanyService;

    @Resource
    private AdptProjService adptProjService;

    @Resource
    private MirrorPhaseCdcService mirrorPhaseCdcService;


    @Override
    public void excuteProject(String systemId, String data) {
        JSONObject json = JSONArray.parseObject(data);
        JSONObject result = new JSONObject();
        JSONArray itemarry = json.getJSONArray("ItemArray"); //操作的数据集合
        JSONArray errorArray = new JSONArray();
        Map<String,Object> map=relLhCompanyToCompanyService.getRelLhCompanyToCompanyMap();
        boolean hasError = false;
        for (int i = 0; i<itemarry.size(); i++) {
            JSONObject resultJson = new JSONObject();
            try {

                // 去掉地块信息，减少data字符串长度，保存到数据库
                JSONObject jsonnode = itemarry.getJSONObject(i);
                JSONObject projResult = operProject(jsonnode,map);
                if("500".equals(projResult.getString("code"))) {
                    resultJson.put("STATUS", "E");
                    hasError = true;
                } else {
                    resultJson.put("STATUS", "S");
                }
                resultJson.put("TYPE", "PRJ");
                resultJson.put("ID", itemarry.getJSONObject(i).getString("PR_ID"));
                resultJson.put("VERSION", itemarry.getJSONObject(i).get("SAP_VER"));
                resultJson.put("LOG", projResult.getString("message"));
                errorArray.add(resultJson);
            } catch(Exception e) {
                resultJson.put("TYPE", "PRJ");
                resultJson.put("STATUS", "E");
                resultJson.put("ID", itemarry.getJSONObject(i).getString("PR_ID"));
                resultJson.put("VERSION", itemarry.getJSONObject(i).get("SAP_VER"));
                resultJson.put("LOG", e.getMessage());
                errorArray.add(resultJson);
                hasError = true;
            }
        }
        result.put("ItemArray", errorArray);
        executorCallBack(result.toString(),hasError);

    }

    @Override
    public void excuteStage(String systemId, String data) {

        JSONObject json = JSON.parseObject(data);
        JSONArray itemarry = json.getJSONArray("ItemArray");
        JSONObject result = new JSONObject();
        result.put("SystemID", "PPS");
        JSONArray errorArray = new JSONArray();
        boolean hasError = false;
        for (int i = 0; i<itemarry.size(); i++) {
            JSONObject resultJson = new JSONObject();
            resultJson.put("TYPE", "P");
            JSONObject jsonnode = itemarry.getJSONObject(i);
            try {
                JSONObject opResult = operStage(jsonnode);
                if("500".equals(opResult.getString("code"))) {
                    resultJson.put("STATUS", "E");
                    hasError = true;
                } else {
                    resultJson.put("STATUS", "S");
                }
                resultJson.put("ID", itemarry.getJSONObject(i).get("PH_ID"));
                resultJson.put("VERSION", itemarry.getJSONObject(i).get("SAP_VER"));
                resultJson.put("LOG", opResult.getString("message"));
                errorArray.add(resultJson);
            } catch( Exception e ) {
                resultJson.put("STATUS", "E");
                resultJson.put("ID", itemarry.getJSONObject(i).getString("PH_ID"));
                resultJson.put("VERSION", itemarry.getJSONObject(i).get("SAP_VER"));
                resultJson.put("LOG", e.getMessage());
                errorArray.add(resultJson);
                hasError = true;
            }
        }
        result.put("ItemArray", errorArray);
        executorCallBack(result.toString(),hasError);


    }


    /**
     * 项目操作
     * */
    private JSONObject operProject(JSONObject project,Map<String,Object>map) {
        JSONObject result = new JSONObject();
        String operCode = project.getString("OP_CODE").trim();//操作码
        String projName = project.getString("PR_NAME").trim();//项目名称
        String projCode=project.getString("HIS_CODE").trim();//项目历史code
        String gsCode=project.getString("PR_REGID").trim();//公司code
        AdptProj adptProj=adptProjService.getByPrCode(projCode);
        if("C".equals(operCode)) { // 新增
                if (adptProj != null ) { // 项目已经存在
                    if (adptProj.getLhXmname().equals(projName) == false) { // 名称更新了
                        updateAptProject(adptProj,projName,gsCode);
                        result.put("code", "200");
                        result.put("message", projName + "修改成功");
                    } else {
                        result.put("code", "400");
                        result.put("message", projName + "项目已存在");
                    }
                    return result;
                }else {
                    AdptProj newAdptProj=new AdptProj();
                    newAdptProj.setLhXmcode(projCode);
                    newAdptProj.setLhXmname(projName);
                    newAdptProj.setLhGscode(gsCode);
                    newAdptProj.setCompanyId(Integer.parseInt(String.valueOf(map.get(gsCode))));
                    newAdptProj.setGroupId(4);
                    newAdptProj.setMenuType("1");
                    newAdptProj.setCreateAt(new Date());
                    newAdptProj.setUpdateAt(new Date());
                   int num= adptProjService.createAdptProj(newAdptProj);
                   if(-1==num) {
                       result.put("code", "500");
                       result.put("message", projName + "创建失败");
                   }else {
                       result.put("code", "200");
                       result.put("message", projName + "创建成功");
                    }
                }
        } else if("U".equals(operCode)) { // 更新

            if (adptProj != null&&adptProj.getLhXmname().equals(projName) == false) { // 名称更新了
                updateAptProject(adptProj,projName,gsCode);
                result.put("code", "200");
                result.put("message", projName + "修改成功");
            }else {
                result.put("code", "400");
                result.put("message", projName + " 名称和CODE无更新");
            }
        } else if("D".equals(operCode)) { // 删除
            if (adptProj != null ) {
                adptProj.setDeleteAt(new Date());
                adptProjService.updateAdptProj(adptProj);
            }
            result.put("code", "200");
            result.put("message", projName + "删除成功");
        }
        return result;
    }


    /**
     * 更新项目数据
     * @param adptProj
     * @param projName
     * @param gsCode
     */
    private void  updateAptProject(AdptProj adptProj,String projName,String gsCode){
            adptProj.setLhXmname(projName);
            adptProj.setLhGscode(gsCode);
            adptProj.setUpdateAt(new Date());
            adptProjService.updateAdptProj(adptProj);
    }


    /**
     * 项目分期
     * */
    private JSONObject operStage(JSONObject stageJson) throws ParseException {

        String operCode = stageJson.getString("OP_CODE").trim();
        String buId = stageJson.getString("BU_ID").trim();//航道
        String stageName=stageJson.getString("PH_NAME");
        String deFlg=stageJson.getString("DE_FLG");//删除标记
        JSONObject result = new JSONObject();

        if("C".equals(operCode)) { // 新增分期

            if("C1".equals(buId)){
              MirrorPhaseCdc oldMirrorPhaseCdc=  mirrorPhaseCdcService.findByBuId(buId);
              if(oldMirrorPhaseCdc!=null){
                  result.put("code", "400");
                  result.put("message", oldMirrorPhaseCdc.getPhName() + "已存在");
              }else{
                  int num =saveOrUpdateMirrorPhaseCdc(stageJson,0,0,null);
                  if(-1==num) {
                      result.put("code", "500");
                      result.put("message", stageName + "创建失败");
                  }else {
                      result.put("code", "200");
                      result.put("message", stageName + "创建成功");
                  }
              }
            }

        } else if("U".equals(operCode)) { // 更新分期
            if("C1".equals(buId)){
                MirrorPhaseCdc oldMirrorPhaseCdc=  mirrorPhaseCdcService.findByBuId(buId);
                int num =saveOrUpdateMirrorPhaseCdc(stageJson,1,oldMirrorPhaseCdc.getId(),null);
                if(-1==num) {
                    result.put("code", "500");
                    result.put("message", stageName + "修改失败");
                }else {
                    result.put("code", "200");
                    result.put("message", stageName + "修改成功");
                }

            }
        } else if("D".equals(operCode)) {
            MirrorPhaseCdc oldMirrorPhaseCdc=  mirrorPhaseCdcService.findByBuId(buId);

            oldMirrorPhaseCdc.setDeFlg(deFlg);

            int num =saveOrUpdateMirrorPhaseCdc(stageJson,2,oldMirrorPhaseCdc.getId(),deFlg);
            if(-1==num) {
                result.put("code", "500");
                result.put("message", stageName + "删除失败");
            }else {
                result.put("code", "200");
                result.put("message", stageName + "删除成功");
            }
        }
        JSONArray groups = stageJson.getJSONArray("GroupArray");//组团

        // 处理分期下组团
        if(groups != null ) {
            for (int j = 0; j< groups.size(); j++) {
                /*JSONObject groupResult = operGroup(operCode, buId, groups.getJSONObject(j));
                if("200".equals(groupResult.getString("code"))) {
                    result.put("group:"+groups.getJSONObject(j).getString("GR_ID"), "200");
                } else {
                    result.put("group:"+groups.getJSONObject(j).getString("GR_ID"), "400");
                    result.put(groups.getJSONObject(j).getString("GR_ID"), groupResult.getString("message"));
                    if (result.has("message")) {
                        result.put("message", result.getString("message")+";"+groupResult.getString("message"));
                    } else {
                        result.put("message", groupResult.getString("message"));
                    }
                }*/
            }
        }

        return result;
    }

    /**
     * 新增C1 分期数据
     * @param stageJson
     * @return
     * @throws ParseException
     */
    private int saveOrUpdateMirrorPhaseCdc(JSONObject stageJson,int type,int id,String deFlg) throws ParseException {

        DateFormat dateFormatter = DateFormat.getDateTimeInstance();

        MirrorPhaseCdc mirrorPhaseCdc=new MirrorPhaseCdc();
        mirrorPhaseCdc.setPhId(stageJson.getString("PH_ID"));
        mirrorPhaseCdc.setSapVer(stageJson.getString("SAP_VER"));
        mirrorPhaseCdc.setVerNam(stageJson.getString("VER_NAM"));
        mirrorPhaseCdc.setHisCode(stageJson.getString("HIS_CODE"));
        mirrorPhaseCdc.setHisIcard(stageJson.getString("HIS_ICARD"));
        mirrorPhaseCdc.setHisGuid(stageJson.getString("HIS_GUID"));
        mirrorPhaseCdc.setHisFlg(stageJson.getString("HIS_FLG"));
        mirrorPhaseCdc.setPhName(stageJson.getString("PH_NAME"));
        mirrorPhaseCdc.setApStatus(stageJson.getString("AP_STATUS"));
        mirrorPhaseCdc.setDeFlg(stageJson.getString("DE_FLG"));
        mirrorPhaseCdc.setPhCname(stageJson.getString("PH_CNAME"));
        mirrorPhaseCdc.setPrCompan(stageJson.getString("PR_COMPAN"));
        mirrorPhaseCdc.setPrType(stageJson.getString("PR_TYPE"));
        mirrorPhaseCdc.setBuId(stageJson.getString("BU_ID"));
        mirrorPhaseCdc.setCaTyp(stageJson.getString("CA_TYP"));
        mirrorPhaseCdc.setCaTypX(stageJson.getString("CA_TYP_X"));
        mirrorPhaseCdc.setCaTypT(stageJson.getString("CA_TYP_T"));
        mirrorPhaseCdc.setPrBugets(stageJson.getString("PR_BUGETS"));
        mirrorPhaseCdc.setPhPrdlev(stageJson.getString("PH_PRDLEV"));
        mirrorPhaseCdc.setPhDevlev(stageJson.getString("PH_DEVLEV"));
        mirrorPhaseCdc.setCrDate(dateFormatter.parse(stageJson.getString("CR_DATE")));
        mirrorPhaseCdc.setChDate(dateFormatter.parse(stageJson.getString("CH_DATE")));
        mirrorPhaseCdc.setPrId(stageJson.getString("PR_ID"));
        mirrorPhaseCdc.setPhOtypD(stageJson.getString("PH_OTYP_D"));
        mirrorPhaseCdc.setPhOtypO(stageJson.getString("PH_OTYP_O"));
        mirrorPhaseCdc.setPhEqRD(stageJson.getString("PH_EQ_R_D"));
        mirrorPhaseCdc.setPhEqRX(stageJson.getString("PH_EQ_R_X"));
        mirrorPhaseCdc.setPhEqRT(stageJson.getString("PH_EQ_R_T"));
        mirrorPhaseCdc.setPhEqRO(stageJson.getString("PH_EQ_R_O"));
        mirrorPhaseCdc.setPhEqOX(stageJson.getString("PH_EQ_O_X"));
        mirrorPhaseCdc.setPhEqOT(stageJson.getString("PH_EQ_O_T"));
        mirrorPhaseCdc.setPhPrdlin(stageJson.getString("PH_PRDLIN"));
        mirrorPhaseCdc.setPhInterv(stageJson.getString("PH_INTERV"));
        mirrorPhaseCdc.setPhBustyp(stageJson.getString("PH_BUSTYP"));
        mirrorPhaseCdc.setPhAsstyp(stageJson.getString("PH_ASSTYP"));
        mirrorPhaseCdc.setPrGettyp(stageJson.getString("PR_GETTYP"));
        mirrorPhaseCdc.setPhMaTyp(stageJson.getString("PH_MA_TYP"));
        mirrorPhaseCdc.setBugetFlg(stageJson.getString("BUGET_FLG"));
        mirrorPhaseCdc.setPhPrgets(stageJson.getString("PH_PRGETS"));
        mirrorPhaseCdc.setPhGrelev(stageJson.getString("PH_GRELEV"));
        mirrorPhaseCdc.setPhOpenD(dateFormatter.parse(stageJson.getString("PH_OPEN_D")));
        mirrorPhaseCdc.setPhLandcl(stageJson.getString("PH_LANDCL"));
        mirrorPhaseCdc.setTaxTyp(stageJson.getString("TAX_TYP"));
        mirrorPhaseCdc.setPhMeFlg(stageJson.getString("PH_ME_FLG"));
        mirrorPhaseCdc.setPhMcFlg(stageJson.getString("PH_MC_FLG"));
        mirrorPhaseCdc.setPhToFlg(stageJson.getString("PH_TO_FLG"));
        mirrorPhaseCdc.setPhMaFlg(stageJson.getString("PH_MA_FLG"));
        mirrorPhaseCdc.setPhDevsta(stageJson.getString("PH_DEVSTA"));
        int num;
        if(0==type){//新增
            num= mirrorPhaseCdcService.insert(mirrorPhaseCdc);
        }else if(1==type) {//修改
            mirrorPhaseCdc.setId(id);
            num=mirrorPhaseCdcService.update(mirrorPhaseCdc);
        }else{//删除
            mirrorPhaseCdc.setDeFlg(deFlg);
            num=mirrorPhaseCdcService.update(mirrorPhaseCdc);
        }
        return  num;
    }


    /**
     * 把成功或者失败信息返回给主数据
     * @param body
     * @param error
     */
    private void executorCallBack(String body, boolean error) {
        RestTemplate restTemplate=new RestTemplate();
        //String userAndPass = "PPSUSER:PPS12345"; //测试环境(10.49)
        String userAndPass = "PPSUSER:$jLVrn^8"; //生产环境(10.37)
        HttpHeaders headers = new HttpHeaders();

        // 待修正
       // headers.add("Authorization", "Basic " +  Base64Utility.encode(userAndPass.getBytes()));
        headers.add("Content-Type","text/plain;charset=UTF-8");
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        System.out.println("body==" + body);
		/* body是Http消息体例如json串 */
        log.debug(mdmSuccess);
        log.debug(mdmError);
        if(error) {
            restTemplate.exchange(mdmError, HttpMethod.POST, entity, String.class);
        } else {
            restTemplate.exchange(mdmSuccess, HttpMethod.POST, entity, String.class);
        }

    }
}

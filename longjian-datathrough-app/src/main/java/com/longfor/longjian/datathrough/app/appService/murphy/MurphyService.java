package com.longfor.longjian.datathrough.app.appService.murphy;

import com.longfor.gaia.gfs.web.feign.LFFeignClient;
import com.longfor.gaia.gfs.web.feign.config.LFFeignConfiguration;
import com.longfor.longjian.datathrough.app.util.LFResultBean;
import com.longfor.longjian.datathrough.dto.LoginMurphyOneDto;
import com.longfor.longjian.datathrough.dto.LoginMurphyThreeDto;
import com.longfor.longjian.datathrough.dto.LoginMurphyTwoDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Created by Wang on 2018/11/16.
 */
@LFFeignClient(group = "murphy", value ="login-murphy-one", configuration = LFFeignConfiguration.class)
public interface MurphyService {

    /**
     *  登录认证
     * @param
     * @return
     */
    @PostMapping(value="api/login/baseMurphy",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    LFResultBean  baseMurphy(
            @RequestHeader("x-authentication-token")String murphtSystemToken,
            @RequestBody LoginMurphyOneDto loginMurphyOneDto);


    /**
     *  加手机号/邮箱/身份证号码进行登录认证
     *
     *  phone、email和credentials_no任意其一，按顺序取一个不为空的字段进行校验，但不可都为空；
     *
     * @param
     * @return
     */
    @PostMapping(value="api/login/baseMurphyAdditionalCheck",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    LFResultBean  baseMurphyAdditionalCheck(
            @RequestHeader("x-authentication-token")String murphtSystemToken,
            @RequestBody LoginMurphyTwoDto loginMurphyTwoDto);


    /**
     *  选择租户 进行登录
     * @param
     * @return
     */
    @PostMapping(value="api/login/baseMurphySelectTenant",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    LFResultBean  baseMurphySelectTenant(
            @RequestHeader("x-authentication-token")String murphtSystemToken,
            @RequestBody LoginMurphyThreeDto loginMurphyThreeDto);
}

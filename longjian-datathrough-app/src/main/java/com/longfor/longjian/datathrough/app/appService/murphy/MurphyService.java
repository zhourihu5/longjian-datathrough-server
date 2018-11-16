package com.longfor.longjian.datathrough.app.appService.murphy;

import com.longfor.longjian.datathrough.app.vo.AccountVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Wang on 2018/11/16.
 */
@FeignClient(name="EDS2")
public interface MurphyService {

    /**
     * 账户 - 根据账号查询
     * @param
     * @return
     */
    @PostMapping(path="/eds2/pull/api/account",headers = {"x-authentication-token=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwMDM3ODI0NTY4OTEzOTIwIiwic3QiOiIxIiwibG4iOiJsb25namlhbjExMTYiLCJwaSI6IjE2MDAzNzgyNDU2ODkxMzkyMCIsInJuIjoi6b6Z5bu657O757ufIn0.eRexfAemDGFi2_gBLBrq4qHrLHdJWU5F8a9DbWYlrCM"})
    LFResultBean<AccountVo> getAccount(@RequestBody String beginTime);
}

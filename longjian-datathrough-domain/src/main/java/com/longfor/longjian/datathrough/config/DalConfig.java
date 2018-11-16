package com.longfor.longjian.datathrough.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lipeishuai
 * @date 2018-11-11 18:13
 */
@Configuration
@tk.mybatis.spring.annotation.MapperScan("com.longfor.longjian.datathrough.dao")
@EnableTransactionManagement
public class DalConfig {

}

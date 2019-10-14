package com.nari.guangzhou.oauth2.config;

import com.nari.guangzhou.oauth2.hessian.PlatformHessianProxyFactoryBean;
import com.nariit.pi6000.ua.bizc.IUserBizc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * 定义hessian接口的bean
 *
 * @author Zongwei
 * @date 2019/9/29 11:35
 */
@Configuration
public class HessianConfig {

    @Bean
    public HessianProxyFactoryBean userBizcClient(){
        PlatformHessianProxyFactoryBean factoryBean = new PlatformHessianProxyFactoryBean();
        // 这个配置项会被PlatformHessianProxyFactoryBean动态替换，这里只是为了初始化成功
        factoryBean.setServiceUrl("http://127.0.0.1");
        factoryBean.setServiceInterface(IUserBizc.class);
        factoryBean.setServiceName("PI6000-UA-WEB");
        return factoryBean;
    }

}

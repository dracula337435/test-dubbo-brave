package io.dracula.test.dubbo.brave.A;

import brave.spring.webmvc.DelegatingTracingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dk
 */
@Configuration
public class ServletConfiguration {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new DelegatingTracingFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }
}

package net.iyiguo.html5.sse.demo.poker.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leeyee
 * @date 2021/8/9
 */
@Configuration
public class PokerConfig {

    @Bean
    public FilterRegistrationBean<HttpHeaderFilter> registrationBean() {
        FilterRegistrationBean<HttpHeaderFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new HttpHeaderFilter());
        registration.addUrlPatterns("/demo/*");
        registration.setName("HttpHeaderFilter");
        registration.setOrder(1);
        // isAsyncSupported() default is true
        return registration;
    }
}


package com.lms.config;

import com.lms.service.LemonadeStandService;
import com.lms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ApplicationConfig {

    @Bean
    public UserService getUserService() {
        return new UserService();
    }

    @Bean
    public LemonadeStandService getLemonadeStandService() {
        return new LemonadeStandService();
    }
}

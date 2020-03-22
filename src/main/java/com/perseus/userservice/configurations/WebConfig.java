package com.perseus.userservice.configurations;

import com.perseus.userservice.converters.UserConverter;
import com.perseus.userservice.converters.UserModelConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(new UserConverter());
        registry.addConverter(new UserModelConverter());
    }
}

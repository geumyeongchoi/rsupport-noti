package com.support.notice.configs;

import com.support.notice.util.MultipartJackson2HttpMessageConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private MultipartJackson2HttpMessageConverter converter;

    @Autowired
    public WebConfig(MultipartJackson2HttpMessageConverter converter) {
        this.converter = converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter);
    }

}

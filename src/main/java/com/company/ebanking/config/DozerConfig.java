package com.company.ebanking.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.company.ebanking.mapper.CustomDozerMapper;

@Configuration
public class DozerConfig {

    @Bean
    public CustomDozerMapper dozer() {
	return new CustomDozerMapper(Arrays.asList("dozer/global-configuration.xml", "dozer/bean-mappings.xml"));

    }

}

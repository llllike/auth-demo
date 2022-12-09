package com.dozer;


import com.github.dozermapper.core.Mapper;
import org.springframework.context.annotation.Bean;


public class DozerAutoConfiguration {
    @Bean
    public DozerUtils getDozerUtils(Mapper mapper) {
        return new DozerUtils(mapper);
    }
}

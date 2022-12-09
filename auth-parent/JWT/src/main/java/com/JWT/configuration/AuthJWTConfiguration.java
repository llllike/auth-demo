package com.JWT.configuration;


import com.JWT.properties.JWTProperties;
import com.JWT.utils.JwtTokenUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(value = {
        JWTProperties.class,
})
public class AuthJWTConfiguration {
    @Bean
    public JwtTokenUtils getJwtTokenUtils(JWTProperties jwtProperties) {
        return new JwtTokenUtils(jwtProperties);
    }


}

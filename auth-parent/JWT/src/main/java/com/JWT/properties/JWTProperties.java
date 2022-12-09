package com.JWT.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 认证服务端 属性
 *
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {

    private TokenInfo user=new TokenInfo();

    @Data
    public static class TokenInfo {
        /**
         * 过期时间
         */
        private Integer expire = 60*60;
        /**
         * 加密 服务使用
         */
        private String priKey="keys/pri";
        /**
         * 解密
         */
        private String pubKey="keys/pub";
        /**
         * 加密算法
         */
        private String alg="RS256";//HS256
        /**
         * 算法的key
         */
        private String key="r5j<.'/*(?`~}#&";
    }

}

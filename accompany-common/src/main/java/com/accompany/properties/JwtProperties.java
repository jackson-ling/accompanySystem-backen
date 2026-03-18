package com.accompany.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@NoArgsConstructor
@ConfigurationProperties(prefix = "accompany.jwt")
public class JwtProperties {

    /**
     *  签名密码
     */
    private String base64EncodedSecretKey;

    /**
     *  有效时间
     */
    private Integer ttl;
}

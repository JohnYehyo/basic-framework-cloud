package com.rongji.rjsoft.system.config;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Jasypt配置
 * @author: JohnYehyo
 * @create: 2021-10-09 10:37:09
 */
@Configuration
public class JasyptConfig {

    @Bean(name="encryptablePropertyDetector")
    public EncryptablePropertyDetector encryptablePropertyDetector(){
        return new MyEncryptablePropertyDetector();
    }

    @Bean(name="encryptablePropertyResolver")
    public EncryptablePropertyResolver encryptablePropertyResolver(){
        return new MyEncryptablePropertyResolver();
    }
}

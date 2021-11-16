package com.rongji.rjsoft.search;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description: 启动类
 * @author: JohnYehyo
 * @create: 2021-04-25 19:09:03
 */
@EnableConfigurationProperties
@EnableEncryptableProperties
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com"}, exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class RjsoftSearchApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(RjsoftSearchApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter("./rjsoft.pid"));
        springApplication.run(args);
    }

}

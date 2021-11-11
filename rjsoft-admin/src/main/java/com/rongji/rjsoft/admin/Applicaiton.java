package com.rongji.rjsoft.admin;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @description: 启动类
 * @author: JohnYehyo
 * @create: 2021-04-25 19:09:03
 */
@EnableConfigurationProperties
@EnableEncryptableProperties
@SpringBootApplication(scanBasePackages = {"com.rongji.rjsoft.admin",
        "com.rongji.rjsoft.common",
        "com.rongji.rjsoft.core"})
public class Applicaiton {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Applicaiton.class);
        springApplication.addListeners(new ApplicationPidFileWriter("./rjsoft.pid"));
        springApplication.run(args);
    }
}

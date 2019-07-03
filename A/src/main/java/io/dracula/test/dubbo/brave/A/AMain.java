package io.dracula.test.dubbo.brave.A;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author dk
 */
@SpringBootApplication
@DubboComponentScan
@ImportResource({"classpath:io/dracula/test/dubbo/brave/tracing.xml",
        "classpath:io/dracula/test/dubbo/brave/http-tracing.xml"})
public class AMain {

    public static void main(String[] args){
        SpringApplication.run(AMain.class, args);
    }

}

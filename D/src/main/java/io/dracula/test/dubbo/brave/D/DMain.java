package io.dracula.test.dubbo.brave.D;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author dk
 */
@SpringBootApplication
@DubboComponentScan
@ImportResource("classpath:io/dracula/test/dubbo/brave/tracing.xml")
public class DMain {

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        SpringApplication.run(DMain.class, args);
    }

}

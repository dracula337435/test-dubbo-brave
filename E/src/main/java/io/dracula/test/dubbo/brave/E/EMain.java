package io.dracula.test.dubbo.brave.E;

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
public class EMain {

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        SpringApplication.run(EMain.class, args);
    }

}

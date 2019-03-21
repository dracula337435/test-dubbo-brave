package io.dracula.test.dubbo.brace.B;

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
public class BMain {

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        SpringApplication.run(BMain.class, args);
    }

}

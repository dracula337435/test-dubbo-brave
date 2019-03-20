package io.dracula.test.dubbo.brace.A;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dk
 */
@SpringBootApplication
@DubboComponentScan
public class AMain {

    public static void main(String[] args){
        SpringApplication.run(AMain.class, args);
    }

}

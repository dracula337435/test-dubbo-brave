package io.dracula.test.dubbo.brave.A;

import com.alibaba.dubbo.config.annotation.Reference;
import io.dracula.test.dubbo.brave.InterfaceB;
import io.dracula.test.dubbo.brave.InterfaceE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dk
 */
@RestController
public class ToBController {

    private static Logger logger = LoggerFactory.getLogger(ToBController.class);

    @Reference
    private InterfaceB interfaceB;

    @GetMapping("/toB")
    public String toB(@RequestParam(value="name", defaultValue="gxk") String name){
        logger.info("在ToB中");
        return interfaceB.toB("经过A的controller的toB" + name);
    }

    @Reference
    private InterfaceE interfaceE;

    @GetMapping("/toE")
    public String toE(@RequestParam(value="name", defaultValue="gxk") String name){
        logger.info("在ToE中");
        return interfaceE.toE("经过A的controller的toE" + name);
    }

}

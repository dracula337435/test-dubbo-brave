package io.dracula.test.dubbo.brave.E;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import io.dracula.test.dubbo.brave.InterfaceB;
import io.dracula.test.dubbo.brave.InterfaceE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dk
 */
@Service
public class InterfaceEImpl implements InterfaceE {

    private static Logger logger = LoggerFactory.getLogger(InterfaceEImpl.class);

    @Reference
    private InterfaceB interfaceB;

    @Override
    public String toE(String name) {
        logger.info("in E");
        return interfaceB.toB("在E内，" + name);
    }

}
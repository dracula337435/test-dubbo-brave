package io.dracula.test.dubbo.brace.B;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dk
 */
@Service
public class InterfaceBImpl implements InterfaceB {

    private static Logger logger = LoggerFactory.getLogger(InterfaceBImpl.class);

    @Override
    public String toB(String name) {
        logger.info(RpcContext.getContext().getAttachments().toString());
        logger.info("in B");
        return "在B内，" + name;
    }

}

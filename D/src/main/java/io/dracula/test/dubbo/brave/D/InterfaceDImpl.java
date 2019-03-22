package io.dracula.test.dubbo.brave.D;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import io.dracula.test.dubbo.brave.InterfaceD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dk
 */
@Service
public class InterfaceDImpl implements InterfaceD {

    private static Logger logger = LoggerFactory.getLogger(InterfaceDImpl.class);

    @Override
    public String toD(String name) {
        logger.info("in D");
        logger.info("RpcContext中的Attachments为" + RpcContext.getContext().getAttachments().toString());
        return "在D内，" + name;
    }

}

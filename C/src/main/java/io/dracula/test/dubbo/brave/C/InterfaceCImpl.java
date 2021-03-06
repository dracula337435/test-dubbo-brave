package io.dracula.test.dubbo.brave.C;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import io.dracula.test.dubbo.brave.InterfaceC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dk
 */
@Service
public class InterfaceCImpl implements InterfaceC {

    private static Logger logger = LoggerFactory.getLogger(InterfaceCImpl.class);

    @Override
    public String toC(String name) {
        logger.info("in C");
        logger.info("RpcContext中的Attachments为" + RpcContext.getContext().getAttachments().toString());
        return "在C内，" + name;
    }

}

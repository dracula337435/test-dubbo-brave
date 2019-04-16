package io.dracula.test.dubbo.brave.E;

import brave.Tracing;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import io.dracula.test.dubbo.brave.InterfaceB;
import io.dracula.test.dubbo.brave.InterfaceE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dk
 */
@Service
public class InterfaceEImpl implements InterfaceE {

    private static Logger logger = LoggerFactory.getLogger(InterfaceEImpl.class);

    @Reference
    private InterfaceB interfaceB;

    @Autowired
    private Tracing tracing;

    @Override
    public String toE(String name) {
        logger.info("in E");
        logger.info("RpcContext中的Attachments为" + RpcContext.getContext().getAttachments().toString());
        logger.info("打通背后和业务代码，spanId="+tracing.currentTraceContext().get().spanIdString());
        return interfaceB.toB("在E内，" + name);
    }

}

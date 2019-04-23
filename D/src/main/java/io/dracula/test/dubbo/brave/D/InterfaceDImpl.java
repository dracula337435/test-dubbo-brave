package io.dracula.test.dubbo.brave.D;

import brave.Tracing;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import io.dracula.test.dubbo.brave.InterfaceD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dk
 */
@Service
public class InterfaceDImpl implements InterfaceD {

    private static Logger logger = LoggerFactory.getLogger(InterfaceDImpl.class);

    @Autowired
    private Tracing tracing;

    @Override
    public String toD(String name) {
        logger.info("in D");
        logger.info("RpcContext中的Attachments为" + RpcContext.getContext().getAttachments().toString());
        logger.info("打通背后和业务代码，spanId="+Long.toHexString(tracing.currentTraceContext().get().spanId()));
        return "在D内，" + name;
    }

}

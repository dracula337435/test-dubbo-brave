package io.dracula.test.dubbo.brave.B;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import io.dracula.test.dubbo.brave.InterfaceB;
import io.dracula.test.dubbo.brave.InterfaceC;
import io.dracula.test.dubbo.brave.InterfaceD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dk
 */
@Service
public class InterfaceBImpl implements InterfaceB {

    private static Logger logger = LoggerFactory.getLogger(InterfaceBImpl.class);

    @Reference
    private InterfaceC interfaceC;

    @Reference
    private InterfaceD interfaceD;

    @Override
    public String toB(String name) {
        logger.info("RpcContext中的Attachments为" + RpcContext.getContext().getAttachments().toString());
        logger.info("in B");
        String tmp = "在B内，" + name;
        logger.info("先调C");
        tmp = interfaceC.toC(tmp);
        logger.info("再调D");
        tmp = interfaceD.toD(tmp);
        return tmp;
    }

}

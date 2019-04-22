package io.dracula.test.dubbo.brave;

import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dk
 */
public class ProductFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(ProductFilter.class);

    private String product = UNKNOWN_PRODUCT_NAME;

    public static final String UNKNOWN_PRODUCT_NAME = "unknown";

    public static final String PRODUCT_ATTACHMENTS_KEY = "product";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        logger.info("in filter for product");
        RpcContext rpcContext = RpcContext.getContext();
        boolean isProviderSide = rpcContext.isProviderSide();
        // 如果是提供者
        if(isProviderSide){
            String remoteProduct = rpcContext.getAttachment(PRODUCT_ATTACHMENTS_KEY);
            logger.info("remoteProduct"+remoteProduct);
            // 如果本product为null
            // 如果传来的product为null
            // 如果不一致
            if(!product.equals(remoteProduct)){

            }
        }
        // 如果是消费者
        else{
            if(invoker.getInterface().getName().contains("a")){
                logger.info("放入attachment");
                rpcContext.setAttachment(PRODUCT_ATTACHMENTS_KEY, "product-a");
            }else{
                logger.info("不放入attachment");
            }
        }
        return invoker.invoke(invocation);
    }

}

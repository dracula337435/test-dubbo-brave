package io.dracula.test.dubbo.brave;

import com.alibaba.dubbo.rpc.*;
import io.dracula.test.dubbo.brave.product.InfoHolderForFilter;
import io.dracula.test.dubbo.brave.product.ProductChainContext;
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

    public static final String SEND_ID_ATTACHMENTS_KEY = "send-id";

    private InfoHolderForFilter infoHolderForFilter;

    public void setInfoHolderForFilter(InfoHolderForFilter infoHolderForFilter){
        this.infoHolderForFilter = infoHolderForFilter;
        this.product = infoHolderForFilter.getProduct();
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        logger.info("in filter for product");
        RpcContext rpcContext = RpcContext.getContext();
        boolean isProviderSide = rpcContext.isProviderSide();
        ProductChainContext productChainContext = ProductChainContext.getCurrent();
        // 如果是提供者
        if(isProviderSide){
            String remoteProduct = rpcContext.getAttachment(PRODUCT_ATTACHMENTS_KEY);
            String sendId = rpcContext.getAttachment(SEND_ID_ATTACHMENTS_KEY);
            if(sendId == null || "".equals(sendId)){
                sendId = getNewSendId(null);
            }
            productChainContext.setPreviousProduct(remoteProduct);
            productChainContext.setSendId(sendId);
            logger.info("previousProduct="+productChainContext.getPreviousProduct());
            // 如果本product为null
            // 如果传来的product为null
            // 如果不一致
            if(product==null || !product.equals(remoteProduct)){
                productChainContext.setFrontId(sendId);
                productChainContext.setSendId(getNewSendId(productChainContext.getFrontId()));
                logger.info("到了一个新产品，frontId="+productChainContext.getFrontId()+"，sendId="+productChainContext.getSendId());
            }
        }
        // 如果是消费者
        else{
            rpcContext.setAttachment(PRODUCT_ATTACHMENTS_KEY, product);
            rpcContext.setAttachment(SEND_ID_ATTACHMENTS_KEY, productChainContext.getSendId());
        }
        return invoker.invoke(invocation);
    }


    private String getNewSendId(String frontId){
        if(frontId == null || "".equals(frontId)){
            return "原sendId为空于是生成一个";
        }else{
            return frontId+"，根据原sendId生成一个新sendId";
        }
    }

}

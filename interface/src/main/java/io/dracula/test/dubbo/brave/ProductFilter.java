package io.dracula.test.dubbo.brave;

import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dk
 */
public class ProductFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(ProductFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        logger.info("in filter for product");
        return invoker.invoke(invocation);
    }

}

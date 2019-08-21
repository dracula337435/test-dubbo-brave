package io.dracula.test.dubbo.brave;

import brave.handler.FinishedSpanHandler;
import brave.handler.MutableSpan;
import brave.propagation.TraceContext;

/**
 * @author dk
 */
public class AppendProductTagFinishedSpanHandler extends FinishedSpanHandler {

    private String product;

    @Override
    public boolean handle(TraceContext context, MutableSpan span) {
        //span中有localServiceName，但是其为null
//        String localServiceName = span.localServiceName();
        span.tag("LocalProduct", product);
        return true;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}

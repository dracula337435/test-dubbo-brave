package io.dracula.test.dubbo.brave;

import brave.handler.FinishedSpanHandler;
import brave.handler.MutableSpan;
import brave.propagation.TraceContext;

public class AppendProductTagFinishedSpanHandler extends FinishedSpanHandler {

    @Override
    public boolean handle(TraceContext context, MutableSpan span) {
        String localServiceName = span.localServiceName();
        span.tag("LocalProduct", localServiceName+"-product");
        return true;
    }

}

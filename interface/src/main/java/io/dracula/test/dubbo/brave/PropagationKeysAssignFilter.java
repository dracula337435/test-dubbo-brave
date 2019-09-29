package io.dracula.test.dubbo.brave;

import brave.dubbo.rpc.PackageAccessBridge;
import brave.propagation.TraceContext;
import brave.propagation.TraceContextOrSamplingFlags;
import com.alibaba.dubbo.rpc.*;

import java.util.Map;

/**
 * @author dk
 */
public abstract class PropagationKeysAssignFilter extends PackageAccessBridge implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Map<String, String> ori = getOriMap(invoker, invocation);
        Map<String, String> des = getDesMap(invoker, invocation);
        if(ori != null && des != null){
            TraceContextOrSamplingFlags extracted = extractor.extract(ori);
            if(extracted != null){
                TraceContext extractedTraceContext = extracted.context();
                if(extractedTraceContext != null){
                    injector.inject(extractedTraceContext, des);
                }
            }
        }
        return invoker.invoke(invocation);
    }

    /**
     *
     * @param invoker
     * @param invocation
     * @return
     */
    abstract public Map<String, String> getOriMap(Invoker<?> invoker, Invocation invocation);

    /**
     *
     * @param invoker
     * @param invocation
     * @return
     */
    abstract public Map<String, String> getDesMap(Invoker<?> invoker, Invocation invocation);

}

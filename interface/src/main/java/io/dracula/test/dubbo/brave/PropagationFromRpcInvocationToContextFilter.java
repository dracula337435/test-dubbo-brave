package io.dracula.test.dubbo.brave;

import brave.Tracing;
import brave.propagation.Propagation;
import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

import java.util.List;
import java.util.Map;

/**
 * 按brave-propagation，从Invocation里把attachment放到RpcContext
 * @author dk
 */
@Activate(group = {Constants.CONSUMER}, value = "inv2context")
public class PropagationFromRpcInvocationToContextFilter implements Filter {

    private List<String> keys;

    public void setTracing(Tracing tracing) {
        if(tracing != null){
            Propagation<String> propagation = tracing.propagation();
            if(propagation != null){
                List<String> tmp = propagation.keys();
                if(tmp != null && tmp.size() != 0){
                    keys = tmp;
                }
            }
        }
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if(keys != null){
            Map<String, String> attachmentsInContext = RpcContext.getContext().getAttachments();
            Map<String, String> attachmentsInInv = invocation.getAttachments();
            if(attachmentsInContext != null && attachmentsInInv != null){
                for(String key: keys){
                    String valueInContext = attachmentsInInv.get(key);
                    if(valueInContext != null){
                        attachmentsInContext.put(key, valueInContext);
                    }
                }
            }
        }
        return invoker.invoke(invocation);
    }

}

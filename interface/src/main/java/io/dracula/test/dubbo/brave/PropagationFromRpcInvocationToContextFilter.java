package io.dracula.test.dubbo.brave;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

import java.util.Map;

/**
 * 按brave-propagation，从Invocation里把attachment放到RpcContext
 * @author dk
 */
@Activate(group = {Constants.CONSUMER}, value = "inv2context")
public class PropagationFromRpcInvocationToContextFilter extends PropagationKeysAssignFilter {

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

package io.dracula.test.dubbo.brave;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

import java.util.Map;

/**
 * 按brave-propagation，从RpcContext里把attachment放到Invocation
 * @author dk
 */
@Activate(group = {Constants.PROVIDER}, value = "context2inv")
public class PropagationFromRpcContextToInvocationFilter extends PropagationKeysAssignFilter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if(keys != null){
            Map<String, String> attachmentsInContext = RpcContext.getContext().getAttachments();
            Map<String, String> attachmentsInInv = invocation.getAttachments();
            if(attachmentsInContext != null && attachmentsInInv != null){
                for(String key: keys){
                    String valueInContext = attachmentsInContext.get(key);
                    if(valueInContext != null){
                        attachmentsInInv.put(key, valueInContext);
                    }
                }
            }
        }
        return invoker.invoke(invocation);
    }

}

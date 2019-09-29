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
    public Map<String, String> getOriMap(Invoker<?> invoker, Invocation invocation) {
        return invocation.getAttachments();
    }

    @Override
    public Map<String, String> getDesMap(Invoker<?> invoker, Invocation invocation) {
        return RpcContext.getContext().getAttachments();
    }

}

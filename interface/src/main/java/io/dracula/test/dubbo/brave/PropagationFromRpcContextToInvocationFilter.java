package io.dracula.test.dubbo.brave;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcContext;

import java.util.Map;

/**
 * 按brave-propagation，从RpcContext里把attachment放到Invocation
 * @author dk
 */
@Activate(group = {Constants.PROVIDER}, value = "context2inv")
public class PropagationFromRpcContextToInvocationFilter extends PropagationKeysAssignFilter {

    @Override
    public Map<String, String> getOriMap(Invoker<?> invoker, Invocation invocation) {
        return RpcContext.getContext().getAttachments();
    }

    @Override
    public Map<String, String> getDesMap(Invoker<?> invoker, Invocation invocation) {
        return invocation.getAttachments();
    }
}

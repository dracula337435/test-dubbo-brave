package io.dracula.test.dubbo.brave;

import brave.Tracing;
import brave.propagation.Propagation;
import com.alibaba.dubbo.rpc.Filter;

import java.util.List;

/**
 * @author dk
 */
public abstract class PropagationKeysAssignFilter implements Filter {

    protected List<String> keys;

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

}

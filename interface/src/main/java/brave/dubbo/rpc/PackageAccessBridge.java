package brave.dubbo.rpc;

import brave.propagation.TraceContext;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Filter;

import java.util.Map;

/**
 * @author dk
 */
public class PackageAccessBridge {

    protected TraceContext.Extractor<Map<String, String>> extractor;

    protected TraceContext.Injector<Map<String, String>> injector;

    public PackageAccessBridge() {
        Filter someFilter = ExtensionLoader.getExtensionLoader(Filter.class).getExtension("tracing");
        if(someFilter instanceof TracingFilter){
            TracingFilter tracingFilter = (TracingFilter)someFilter;
            extractor = tracingFilter.extractor;
            injector = tracingFilter.injector;
        }
    }

}

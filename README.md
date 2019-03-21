# 试验dubbo

和brave集成，向zipkin发送链路跟踪数据

调用关系如下图形所示  
```
       ┌> C
A -> B ┤
       └> D
```
访问[A的/toB端点](http://localhost:8080/toB)发起整个链路的一次调用

参考了文章[服务化改造实践（三） | Dubbo + Zipkin](https://www.jianshu.com/p/923677e56253)

```spring-cloud```中加入```sleuth```，默认可以打出```trace-id```和```span-id```，
通过搜索约定俗成的名称```X-B3-```，可见```spring-cloud-sleuth-core```中
```org.springframework.cloud.sleuth.autoconfig.TraceEnvironmentPostProcessor```
在做修改日志```pattern```，可见其中代码如下：
```
@Override
public void postProcessEnvironment(ConfigurableEnvironment environment,
        SpringApplication application) {
    Map<String, Object> map = new HashMap<String, Object>();
    // This doesn't work with all logging systems but it's a useful default so you see
    // traces in logs without having to configure it.
    if (Boolean.parseBoolean(environment.getProperty("spring.sleuth.enabled", "true"))) {
        map.put("logging.pattern.level",
                "%5p [${spring.zipkin.service.name:${spring.application.name:-}},%X{X-B3-TraceId:-},%X{X-B3-SpanId:-},%X{X-Span-Export:-}]");
    }
    addOrReplace(environment.getPropertySources(), map);
}
```
知其修改了```logging.pattern.level```，在其中加入```X-B3-```系列。  
继续探索，知```spring-boot```利用```org.springframework.boot.logging.logback.DefaultLogbackConfiguration```定义了默认```pattern```，代码如下：
```
private static final String CONSOLE_LOG_PATTERN = "%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}){faint} "
        + "%clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} "
        + "%clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} "
        + "%clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}";
```
注意其中的```%clr(${LOG_LEVEL_PATTERN:-%5p})```，和```spring-cloud```中的配置对应起来了

```brave```自带和```dubbo```集成的方式，传```attachments```使用```X-B3-```系列，放入```mdc```则没有使用```X-B3-```，
可见```InterfaceBImpl```打出的日志：
```
INFO [bb1160909becc04d,bb1160909becc04d] 9512 --- [:20880-thread-2] i.d.test.dubbo.brave.B.InterfaceBImpl    : MDC为{traceId=bb1160909becc04d, spanId=bb1160909becc04d}
INFO [bb1160909becc04d,bb1160909becc04d] 9512 --- [:20880-thread-2] i.d.test.dubbo.brave.B.InterfaceBImpl    : RpcContext中的Attachments为{input=300, X-B3-Sampled=1, X-B3-TraceId=bb1160909becc04d, interface=io.dracula.test.dubbo.brave.InterfaceB, X-B3-SpanId=bb1160909becc04d}
```

如果```B```没有用```brave```的```Filter```，结果很奇特，zipkin检测到两条链路，按时间先后
```
仅D
A -> C
```
A日志为：
```
INFO [,] 14220 --- [io-8080-exec-10] i.d.test.dubbo.brave.A.ToBController     : 在ToBController中
```
B日志为：
```
INFO [,] 10360 --- [:20880-thread-2] i.d.test.dubbo.brave.B.InterfaceBImpl    : RpcContext中的Attachments为{input=300, X-B3-Sampled=1, X-B3-TraceId=927070df401f873c, interface=io.dracula.test.dubbo.brave.InterfaceB, X-B3-SpanId=927070df401f873c}
INFO [,] 10360 --- [:20880-thread-2] i.d.test.dubbo.brave.B.InterfaceBImpl    : in B
INFO [,] 10360 --- [:20880-thread-2] i.d.test.dubbo.brave.B.InterfaceBImpl    : 先调C
INFO [,] 10360 --- [:20880-thread-2] i.d.test.dubbo.brave.B.InterfaceBImpl    : 再调D
```
C日志为：
```
INFO [927070df401f873c,927070df401f873c] 15128 --- [:20881-thread-5] i.d.test.dubbo.brave.C.InterfaceCImpl    : in C
INFO [927070df401f873c,927070df401f873c] 15128 --- [:20881-thread-5] i.d.test.dubbo.brave.C.InterfaceCImpl    : RpcContext中的Attachments为{input=310, X-B3-Sampled=1, X-B3-TraceId=927070df401f873c, interface=io.dracula.test.dubbo.brave.InterfaceC, X-B3-SpanId=927070df401f873c}
```
D日志为：
```
INFO [7acef2e32e227f65,7acef2e32e227f65] 18648 --- [:20882-thread-5] i.d.test.dubbo.brave.D.InterfaceDImpl    : in D
INFO [7acef2e32e227f65,7acef2e32e227f65] 18648 --- [:20882-thread-5] i.d.test.dubbo.brave.D.InterfaceDImpl    : RpcContext中的Attachments为{interface=io.dracula.test.dubbo.brave.InterfaceD, input=236}
```
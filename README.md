# 试验dubbo

和brave集成，向zipkin发送链路跟踪数据

调用关系如下图形所示  
```
       |-> C
A -> B |
       |-> D
```    
参考了文章[服务化改造实践（三） | Dubbo + Zipkin](https://www.jianshu.com/p/923677e56253)
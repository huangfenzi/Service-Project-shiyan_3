package ynu.edu.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ynu.edu.entity.CommonResult;
import ynu.edu.entity.User;
import ynu.edu.feign.ServiceProviderService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ServiceProviderService serviceProviderService;

    @GetMapping("/getCartById/{userId}")
    @CircuitBreaker(name="backendA",fallbackMethod = "getCartByIdDown")
    @Bulkhead(name="bulkhead1",type = Bulkhead.Type.THREADPOOL,fallbackMethod = "getCartById")
    @RateLimiter(name="rate1",fallbackMethod = "getCartById")
    public CommonResult<User> getCartById(@PathVariable("userId") Integer userId) {
        return serviceProviderService.getCartById(userId);
    }

    public CompletableFuture<User> getCartByIdDown(Integer userId, Throwable e){
        e.printStackTrace();
        String message ="获取用户"+userId+"信息的服务当前被熔断，因此方法降级";//可修改内容
        System.out.println(message);
//        CommonResult<User> result = serviceProviderService.getCartById(userId);
        // 返回一个降级结果，可以根据具体业务需求进行定义
        return CompletableFuture.completedFuture(serviceProviderService.getCartById(userId).getResult());

    }
}
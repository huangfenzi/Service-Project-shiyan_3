package ynu.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ynu.edu.rule.CustomerLoadBalancerConfig;


@SpringBootApplication
@LoadBalancerClient(name ="provider-server",configuration = CustomerLoadBalancerConfig.class)
public class ConsumerApplication16001 {

    /**
     * 使用spring提供的RestTemplate发送http请求调用微服务
     * 在主启动类中，将RestTemplate实例放入Spring容器中
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication16001.class, args);
    }
}

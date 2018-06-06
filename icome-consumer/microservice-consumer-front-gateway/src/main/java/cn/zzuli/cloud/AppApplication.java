package cn.zzuli.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author fredlau
 * @version 1.0.0
 * @date 17/12/12
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AppApplication {
    /**
     * 启动方法
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}

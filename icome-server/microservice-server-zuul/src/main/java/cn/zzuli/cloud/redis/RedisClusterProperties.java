package cn.zzuli.cloud.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author fredlau
 * @date 2017/12/15/015
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisClusterProperties {
    private List<String> nodes=new ArrayList<>();

    private String maxRedirects;
    private String maxActive;
    private String maxWait;
    private String maxIdle;
    private String minIdle;
    private String timeout;
    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public String getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(String maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public String getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }

    public String getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(String maxWait) {
        this.maxWait = maxWait;
    }

    public String getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}

package cn.zzuli.cloud.redis;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.util.Assert;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 *
 *
 * @author fredlau
 * @date 2017/12/15/015
 */
@Configuration
@ConditionalOnClass(RedisClusterConfig.class)
@EnableConfigurationProperties(RedisClusterProperties.class)
public class RedisClusterConfig {
    @Autowired
    private RedisClusterProperties redisClusterProperties;

    @Bean
    public JedisCluster redisCluster() {

        Set<HostAndPort> nodes = new HashSet<>();
        for (String node : redisClusterProperties.getNodes()) {
            String[] parts = StringUtils.split(node, ":");
            Assert.state(parts.length == 2, "redis node shoule be defined as 'host:port', not '" + Arrays.toString(parts) + "'");
            nodes.add(new HostAndPort(parts[0], Integer.valueOf(parts[1])));
        }

        return new JedisCluster(nodes);
    }

    public List<RedisNode> redisNodeList() {

        List<RedisNode> list = new ArrayList<>();
        for (String node : redisClusterProperties.getNodes()) {
            String[] parts = StringUtils.split(node, ":");
            Assert.state(parts.length == 2, "redis node shoule be defined as 'host:port', not '" + Arrays.toString(parts) + "'");
            list.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
        }
        return list;
    }
}

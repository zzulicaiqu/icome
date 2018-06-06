package cn.zzuli.cloud.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author fredlau
 * @date 2017/12/15/015
 */
@Component
public class RedisBean {

    private static final Logger logger = LoggerFactory.getLogger(RedisBean.class);
    private final RedisTemplate redisTemplate;

    public RedisBean(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        this.redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        this.redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        this.redisTemplate.setKeySerializer(this.redisTemplate.getStringSerializer());
        this.redisTemplate.setHashKeySerializer(this.redisTemplate.getStringSerializer());
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception ee) {
            logger.error("redis写入缓存异常", ee);
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception ee) {
            logger.error("redis写入缓存异常", ee);
        }
        return result;
    }

    public boolean sets(final String key, Object value) {
        boolean result = false;
        try {
            SetOperations<Serializable, Object> operations = redisTemplate.opsForSet();
            operations.add(key, value);
            result = true;
        } catch (Exception ee) {
            logger.error("redis写入缓存异常", ee);
        }
        return result;
    }

    public Set gets(final String key) {
        try {
            SetOperations<Serializable, Object> operations = redisTemplate.opsForSet();
            return operations.members(key);
        } catch (Exception ee) {
            logger.error("redis读取缓存异常", ee);
        }
        return null;
    }

    public boolean insertList(final String key, Object value) {
        boolean result = false;
        try {
            ListOperations<Serializable, Object> operations = redisTemplate.opsForList();
            operations.leftPush(key, value);
            result = true;
        } catch (Exception ee) {
            logger.error("redis写入缓存异常", ee);
        }
        return result;
    }

    public Object getListFirst(final String key) {

        ListOperations<Serializable, Object> operations = redisTemplate.opsForList();
        return operations.index(key, 0L);
    }

    public Object getByListIndex(final String key, final long index) {
        ListOperations<Serializable, Object> operations = redisTemplate.opsForList();
        return operations.index(key, index);
    }

    public List getList(final String key) {
        try {
            ListOperations<Serializable, Object> operations = redisTemplate.opsForList();
            return operations.range(key, 0L, Long.MAX_VALUE);
        } catch (Exception ee) {
            logger.error("redis读取缓存异常", ee);
        }
        return null;
    }

    public boolean setHash(final String key, String hkey, Object hvalue) {
        boolean result = false;
        try {
            HashOperations<Serializable, String, Object> operations = redisTemplate.opsForHash();
            operations.put(key, hkey, hvalue);
            result = true;
        } catch (Exception ee) {
            logger.error("redis写入缓存异常", ee);
        }
        return result;
    }

    public boolean setHash(final String key, Map<String, Object> map) {
        boolean result = false;
        try {
            HashOperations<Serializable, String, Object> operations = redisTemplate.opsForHash();
            operations.putAll(key, map);
            result = true;
        } catch (Exception ee) {
            logger.error("redis写入缓存异常", ee);
        }
        return result;
    }

    public List<Object> getHash(final String key) {
        List<Object> list = null;
        try {
            HashOperations<Serializable, String, Object> operations = redisTemplate.opsForHash();
            list = operations.values(key);
        } catch (Exception ee) {
            logger.error("redis读取缓存异常", ee);
        }
        return list == null ? new ArrayList<>(0) : list;
    }

    public Object getHash(final String key, final String hkey) {
        HashOperations<Serializable, String, Object> operations = redisTemplate.opsForHash();
        return operations.get(key, hkey);
    }
}

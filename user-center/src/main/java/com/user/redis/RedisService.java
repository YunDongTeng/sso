package com.user.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by admin on 2018/1/18.
 */
@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取redis对象
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(Prefix prefix, String key, Class<T> clazz) {
        Jedis jedis = jedisPool.getResource();
        try {
            String redisKey = prefix.getPrefix() + key;
            String value = jedis.get(redisKey);
            T t = stringToBean(value, clazz);
            return t;
        } finally {
            retrunToPool(jedis);
        }
    }

    public <T> boolean set(String key, T value, int expire) {
        Jedis jedis = jedisPool.getResource();
        try {
            String result;
            String redisKey = key;
            if (expire == 0) {
                result = jedis.set(redisKey, beanToString(value));
            } else {
                result = jedis.setex(redisKey, expire, beanToString(value));
            }
            if (result == null) {
                return false;
            }
            return true;
        } finally {
            retrunToPool(jedis);
        }
    }

    public <T> boolean set(Prefix prefix, String key, T value) {
        Jedis jedis = jedisPool.getResource();
        try {
            int expire = prefix.getExpire();
            String result;
            String redisKey = prefix.getPrefix() + key;
            if (expire == 0) {
                result = jedis.set(redisKey, beanToString(value));
            } else {
                result = jedis.setex(redisKey, expire, beanToString(value));
            }
            if (result == null) {
                return false;
            }
            return true;
        } finally {
            retrunToPool(jedis);
        }
    }

    public <T> boolean desc(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            Long result = jedis.decr(key);
            if (result <= 0) {
                return false;
            }
            return true;
        } finally {
            retrunToPool(jedis);
        }
    }

    public boolean delete(String key) {
        Jedis jedis = jedisPool.getResource();

        Long result = jedis.del(key);
        if (result <= 0) {
            return false;
        }
        return true;

    }

    public static <T> String beanToString(T value) {

        if (value == null) {
            return null;
        } else if (value.getClass() == int.class || value.getClass() == Integer.class) {
            return String.valueOf(value);
        } else if (value.getClass() == String.class) {
            return value + "";
        } else if (value.getClass() == long.class || value.getClass() == Long.class) {
            return String.valueOf(value);
        }
        return JSON.toJSONString(value);
    }

    public static <T> T stringToBean(String value, Class<T> clazz) {

        if (value == null || value.length() == 0 || clazz == null) {
            return null;
        } else if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(value);
        } else if (clazz == String.class) {
            return (T) (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) String.valueOf(clazz);
        }
        return JSON.toJavaObject((JSON) JSON.parse(value), clazz);
    }


    public void retrunToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}

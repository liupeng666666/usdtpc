package com.whp.usdtpc.business.utils;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Set;

public class RedisUtils {
//
//    static Jedis jedis;
//
//    static {
//        jedis = new Jedis(PropertiesUtils.KeyValue("redis.host"), Integer.parseInt(PropertiesUtils.KeyValue("redis.port")));
//        if (PropertiesUtils.KeyValue("redis.state").equals("true")) {
//            jedis.auth(PropertiesUtils.KeyValue("redis.auth"));
//        }
//    }

    private static JedisPool pool = null;

    static {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(500);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(5);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 100);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            if (PropertiesUtils.KeyValue("redis.state").equals("true")) {
                pool = new JedisPool(config, PropertiesUtils.KeyValue("redis.host"), Integer.parseInt(PropertiesUtils.KeyValue("redis.port")), 10000, PropertiesUtils.KeyValue("redis.auth"));

            } else {
                pool = new JedisPool(config, PropertiesUtils.KeyValue("redis.host"), Integer.parseInt(PropertiesUtils.KeyValue("redis.port")));

            }
        }
    }

    public static void returnResource(Jedis redis) {
        if (redis != null) {
            redis.close();
        }
    }

    /**
     * 方法描述 获取Jedis实例
     *
     * @return
     * @author yaomy
     * @date 2018年1月11日 下午4:56:58
     */
    public static Jedis getJedis() {
        Jedis jedis = pool.getResource();
        return jedis;
    }


    public static String get(String minute, String currency) {
        Jedis jedis = getJedis();
        String time = jedis.get("time." + currency + "." + minute);
        returnResource(jedis);
        return time;
    }

    public static void set(String minute, String currency, String time) {
        Jedis jedis = getJedis();
        jedis.set("time." + currency + "." + minute, time);
        returnResource(jedis);
    }

    public static void LPUSH(String key, String value) {
        Jedis jedis = getJedis();
        jedis.lpush(key, value);
        returnResource(jedis);
    }

    public static String LRANGE(String key) {
        Jedis jedis = getJedis();
        List<String> value = jedis.lrange(key, 0, 0);
        returnResource(jedis);
        if (value.size() == 0) {
            return "";
        } else {
            JSONObject json = JSONObject.parseObject(value.get(0));
            return json.getString("phase");
        }

    }

    public static List<String> lrange(String key, int num, int start) {
        Jedis jedis = getJedis();
        List<String> value = jedis.lrange(key, start, num);
        returnResource(jedis);
        return value;
    }

    public static Set<String> keys(String key) {
        Jedis jedis = getJedis();
        Set<String> z = jedis.keys(key);
        returnResource(jedis);
        return z;
    }

    public static String yget(String key) {
        Jedis jedis = getJedis();
        String time = jedis.get(key);
        returnResource(jedis);
        return time;
    }

    public static String getSelect(String key, int select) {
        Jedis jedis = getJedis();
        jedis.select(select);
        String time = jedis.get(key);
        returnResource(jedis);
        return time;
    }
}

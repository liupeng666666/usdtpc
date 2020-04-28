package com.whp.usdt.user.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Redis操作类
 *
 * @author WuJingpeng
 */
public class Redis {
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

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        try {
            Jedis redis = getJedis();
            List<String> list = redis.lrange("A.5a75a78d-8825-11e8-9c87-507b9d9c3062.1", 0, 1);
            System.out.println(list.size());
            JSONArray jsons = JSONArray.parseArray(list.toString());
            for (int i = 0; i < jsons.size(); i++) {

                System.out.println(jsons.getJSONObject(i).get("phase"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 101);
        }
    }

    public static JSONObject getKlinByCurrency(String key, int hours, String sys_minute_id, int minute) {
        JSONObject json = new JSONObject();

        try {
            Jedis redis = getJedis();
            int total = hours * 60 / minute;
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowdate = format.format(now);
            long endtotal = redis.llen(key);
            List<String> list = redis.lrange(key, endtotal - total, endtotal);
            if (list != null && list.size() > 0) {
                JSONArray jsons = JSONArray.parseArray(list.toString());
                json.put("code", 100);
                json.put("kline", jsons);
            } else {
                json.put("code", 101);
            }
            returnResource(redis);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 101);
        }

        return json;
    }

    public static JSONObject setGoogleInviteCode(String key, String content) {
        JSONObject json = new JSONObject();
        try {
            Jedis redis = getJedis();
            redis.set(key, content, "NX", "EX", 86400);
            json.put("code", 100);
            returnResource(redis);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 101);
        }
        return json;
    }

    public static String getInviteCode(String key) {
        Jedis redis = getJedis();
        redis.select(2);
        String value = redis.get(key);
        returnResource(redis);
        return value;
    }

    public static JSONObject setInviteCode(String key, String content) {
        JSONObject json = new JSONObject();
        try {
            Jedis redis = getJedis();
            redis.select(2);
            String value = redis.get(key);
            if (value == null || "".equals(value)) {
                redis.set(key, content);
                redis.set(content, key);
            } else {
                setInviteCode(MD5Util.YaoQingMa(), content);
            }
            json.put("code", 100);
            returnResource(redis);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 101);
        }
        return json;
    }

    public static String getGoogleInviteCode(String key) {
        Jedis redis = getJedis();
        return redis.get(key);
    }


    public static int setScanLoginInfo(String key, String content) {
        try {
            Jedis redis = getJedis();
            redis.set(key, content, "NX", "EX", 86400);
            returnResource(redis);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static JSONObject getScanLoginUserInfo(String key) {
        JSONObject json = null;
        try {
            Jedis redis = getJedis();
            if (redis.exists(key)) {
                String str = redis.get(key);
                json = new JSONObject();
                json = JSONObject.parseObject(str);
                redis.del(key);
            } else {

            }
            returnResource(redis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static int checkScanLoginInfo(String key) {
        try {
            Jedis redis = getJedis();
            if (redis.exists(key)) {
                returnResource(redis);
                return 1;
            } else {
                returnResource(redis);
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int setLoginPlatformInfo(String key, String content) {
        try {
            System.out.println("rediskey:" + key);
            System.out.println("redis===:" + content);
            Jedis redis = getJedis();
            redis.set(key, content);
            returnResource(redis);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static JSONObject getLoginPlatformInfo(String key) {
        JSONObject json = new JSONObject();
        try {
            Jedis redis = getJedis();
            if (redis.exists(key)) {
                String str = redis.get(key);
                json.put("code", 100);
                json.put("content", str);
                returnResource(redis);
            } else {
                returnResource(redis);
                json.put("code", 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 101);
        }
        return json;
    }

    public static String get_yzm(String key, int select) {
        String value = "";
        try {
            Jedis redis = getJedis();
            redis.select(select);
            value = redis.get(key);
            returnResource(redis);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public static void set_yzm(String key, int select, String value) {

        try {
            Jedis redis = getJedis();
            redis.select(select);
            redis.del(key);
            redis.set(key, value, "NX", "EX", 900);
            returnResource(redis);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void del_yzm(String key, int select) {
        try {
            Jedis redis = getJedis();
            redis.select(select);
            redis.del(key);
            returnResource(redis);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

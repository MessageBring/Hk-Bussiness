package cn.hk.common.service.impl;

import cn.hk.common.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@Service
public class RedisService implements IRedisService {

    private final Jedis jedis;

    public RedisService(Jedis jedis) {
        this.jedis = jedis;
    }


    /**
     * 向redis添加key，value，带过期时间
     * @param key
     * @param value
     * @param ttl 过期时间，单位为秒
     * @return
     */
    @Override
    public boolean setVal(String key, String value, long ttl) {
        String val = jedis.setex(key,ttl,value);
        if("OK".equals(val)){
            return true;
        }
        return false;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean setVal(String key, String value) {
        String val = jedis.set(key,value);
        if ("OK".equals(val)){
            return true;
        }
        return false;
    }

    @Override
    public boolean hSetVal(String key, String field, String value) {
        long val = jedis.hset(key,field,value);
        if (val>0) {
            return true;
        }
        return false;
    }

    @Override
    public String getVal(String key) {
        String val = jedis.get(key);
        return val;
    }

    @Override
    public String hGetVal(String key, String field) {
        String val = jedis.hget(key,field);
        return val;
    }

    @Override
    public Map<String,String> hGetAll(String key) {
        Map<String,String> map = jedis.hgetAll(key);
        return map;
    }

    /**
     * 防重锁，如果相同的key做redis保存，只有一个能保存成功，设置过期时间，保证哪怕代码错误也能在指定时间内自动释放锁
     * @param key
     * @param ttl
     * @return
     */
    @Override
    public boolean tryLock(String key, long ttl) {
        long val = jedis.setnx(key,"1");
        jedis.expire(key,ttl);
        if (val>0){
            return true;
        }
        return false;
    }

    /**
     * 释放锁，其实就是把key删掉
     * @param key
     */
    @Override
    public void unlock(String key) {
        jedis.del(key);
    }

}

package cn.hk.common.service;

import java.util.Map;

public interface IRedisService {

    boolean setVal(String key,String value,long ttl);

    boolean setVal(String key,String value);

    boolean hSetVal(String key,String field,String value);

    String getVal(String key);

    String hGetVal(String key,String field);

    Map<String,String> hGetAll(String key);

    boolean tryLock(String key,long ttl);

    void unlock(String key);
}

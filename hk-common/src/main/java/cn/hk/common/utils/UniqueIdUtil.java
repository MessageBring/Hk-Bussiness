package cn.hk.common.utils;

import cn.hk.common.config.SnowflakeIdWorker;

import java.util.UUID;

public class UniqueIdUtil {

    private static long dataCenterId=01;

    private static long workerId=01;

    public static long getSnowFlakeId(){
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(workerId,dataCenterId);
        return snowflakeIdWorker.nextId();
    }

    public static String getRandomId(){
        return UUID.randomUUID().toString();
    }
}

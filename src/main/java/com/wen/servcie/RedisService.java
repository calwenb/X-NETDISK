package com.wen.servcie;

/**
 * RedisService 业务类
 * 对redis进行预热操作
 *
 * @author Mr.文
 */
public interface RedisService {
    //boolean setString(String key, String value, long time);

    void redisWarmUp();
}

package com.wen.servcie;

import com.wen.pojo.User;

/**
 * TokenService  业务类
 * 1.生成token令牌
 * 2.从请求头获取token令牌
 * 3.解析token令牌成user类
 *
 * @author Mr.文
 */
public interface TokenService {
    void saveToken(String token, Object value, int hour);

    boolean verifyToken(String token);

    String getToken(User user);

    String getTokenUserId();

    User getTokenUser();

    boolean removeToken(String token);

    Long getExpireTime(String token);

    boolean renew(String token, int hour);


}

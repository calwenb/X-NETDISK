package com.wen.servcie.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wen.mapper.UserMapper;
import com.wen.pojo.User;
import com.wen.servcie.TokenService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/***
 * token 下发
 * @author
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    UserMapper userMapper;
    @Resource
    RedisTemplate redisTemplate;
    private final static String JWT_SECRET = "wen";
    private final static String TOKEN_PREFIX = "token:";

    @Override
    public void saveToken(String token, Object value, int hour) {
        System.out.println("save");
        redisTemplate.opsForValue().set(TOKEN_PREFIX + token, value, hour, TimeUnit.HOURS);
    }

    @Override
    public boolean removeToken(String token) {
        return redisTemplate.delete(TOKEN_PREFIX + token);
    }

    @Override
    public Long getExpireTime(String token) {
        return redisTemplate.opsForValue().getOperations().getExpire(TOKEN_PREFIX + token);
    }

    @Override
    public boolean renew(String token, int hour) {
        return redisTemplate.expire(TOKEN_PREFIX + token, hour, TimeUnit.HOURS);
    }


    @Override
    public boolean verifyToken(String token) {
        Object o = redisTemplate.opsForValue().get(TOKEN_PREFIX + token);
        return o != null;
    }

    @Override
    public String getToken(User user) {
        return JWT.create()
                .withAudience(String.valueOf(user.getId()))
                .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    @Override
    public String getTokenUserId() {
        // 从 http 请求头中取出 token
        //String token = getRequest().getHeader("token");
        String token = getRequest().getParameter("token");
        String userId = JWT.decode(token).getAudience().get(0);
        return userId;
    }

    @Override
    public User getTokenUser() {
        return userMapper.getUserById(Integer.parseInt(this.getTokenUserId()));
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}
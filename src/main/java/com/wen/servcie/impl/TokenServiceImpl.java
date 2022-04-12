package com.wen.servcie.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wen.mapper.UserMapper;
import com.wen.pojo.User;
import com.wen.servcie.TokenService;
import com.wen.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/***
 * token 下发
 * @author
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    UserMapper userMapper;

    @Override
    public String getToken(User user) {
        Date start = new Date();
        //一小时有效时间
        long currentTime = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(360 * 2);
        Date end = new Date(currentTime);

        String token = "";
        token = JWT.create()
                .withAudience(String.valueOf(user.getId()))
                .withIssuedAt(start)
                .withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassWord()));

        return token;
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
        return userMapper.getUserById(Integer.parseInt(getTokenUserId()));
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}
package com.wen.servcie;

import com.wen.pojo.User;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    String getToken(User user);
    String getTokenUserId();
    User getTokenUser();
}

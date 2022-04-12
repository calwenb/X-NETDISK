package com.wen.controller;

import com.alibaba.fastjson.JSON;
import com.wen.annotation.PassToken;
import com.wen.pojo.User;
import com.wen.utils.NullUtil;
import com.wen.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @PassToken
    @GetMapping("/login")
    public String login(@RequestParam("loginName") String loginName,
                        @RequestParam("password") String password) {
        if (NullUtil.hasNull(loginName, password)) {
            return NullUtil.msg();
        }
        User user = userService.login(loginName, password);
        if (user == null) {
            return ResponseUtil.error("账号或者密码错误");
        }
        System.out.println(user.getUserName() + "登录");
        String token = tokenService.getToken(user);
        return ResponseUtil.success(token);
    }

    @PassToken
    @PostMapping("/register")
    public String register(@RequestParam("loginName") String userName,
                           @RequestParam("loginName") String loginName,
                           @RequestParam("password") String password) {
        if ((NullUtil.hasNull(userName, loginName, password))) {
            return NullUtil.msg();
        }
        Map<String, Object> rs = userService.register(userName, loginName, password);
        if (rs.containsKey("error")) {
            return ResponseUtil.error(rs.get("error").toString());
        }
        String token = tokenService.getToken((User) rs.get("user"));
        return ResponseUtil.success(token);
    }

    @GetMapping("/getUser")
    public String getUserByToken(@RequestParam("token") String token) {
        if ((NullUtil.hasNull(token))) {
            return NullUtil.msg();
        }

        User user = tokenService.getTokenUser();
        if (user == null) {
            return ResponseUtil.error("错误令牌!!");
        }
        return ResponseUtil.success(JSON.toJSONString(user));
    }

    @PutMapping("/updatePassword")
    public String updatePassword(@RequestParam("token") String token,
                                 @RequestParam("password") String password) {
        if ((NullUtil.hasNull(token))) {
            return NullUtil.msg();
        }
        User user = tokenService.getTokenUser();
        if (user == null) {
            return ResponseUtil.error("错误令牌!!");
        }
        try {
            user.setPassWord(password);
            userService.updateUser(user);
            return ResponseUtil.success(JSON.toJSONString(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.error("修改错误");
        }

    }
}

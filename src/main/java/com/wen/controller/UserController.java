package com.wen.controller;

import com.alibaba.fastjson.JSON;
import com.wen.annotation.PassToken;
import com.wen.pojo.User;
import com.wen.utils.NullUtil;
import com.wen.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * UserController类
 */
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
                           @RequestParam("email") String email,
                           @RequestParam("loginName") String loginName,
                           @RequestParam("password") String password) {
        if ((NullUtil.hasNull(userName, email, loginName, password))) {
            return NullUtil.msg();
        }
        User user = new User(-1, userName, loginName, password, 2, "", email, "/#", new Date());
        Map<String, Object> rs = userService.register(user);
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

    @PassToken
    @PostMapping("/sendCode")
    public String sendCode(@RequestParam("loginName") String loginName,
                           @RequestParam("email") String email) {
        if ((NullUtil.hasNull(loginName, email))) {
            return NullUtil.msg();
        }
        if (userService.sendCode(loginName, email)) {
            return ResponseUtil.success("发送成功，三分钟内有效");
        }
        return ResponseUtil.error("发送失败。输入用户预留邮箱，未预留邮箱暂不支持服务");
    }


    @PassToken
    @PostMapping("/re_pwd")
    public String repwd(@RequestParam("loginName") String loginName,
                        @RequestParam("password") String password,
                        @RequestParam("code") String code) {
        if ((NullUtil.hasNull(loginName, password))) {
            return NullUtil.msg();
        }
        if (!userService.verifyCode(loginName, code)) {
            return ResponseUtil.error("验证码不正确或已失效");
        }
        if (userService.repwd(loginName, password)) {
            return ResponseUtil.success("密码重置成功");
        }
        return ResponseUtil.error("密码重置失败");
    }
}

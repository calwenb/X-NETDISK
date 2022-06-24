package com.wen.controller;

import com.alibaba.fastjson.JSON;
import com.wen.annotation.PassToken;
import com.wen.pojo.User;
import com.wen.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                        @RequestParam("password") String password,
                        @RequestParam(value = "remember", defaultValue = "false") boolean remember) {

        User user = userService.login(loginName, password);
        if (user == null) {
            return ResponseUtil.error("账号或者密码错误");
        }
        System.out.println(user.getUserName() + "登录");
        String token = tokenService.getToken(user);
        //记住密码给30天，否则12小时
        if (remember) {
            tokenService.saveToken(token, user.getUserType(), 30 * 24);
        } else {
            tokenService.saveToken(token, user.getUserType(), 12);
        }
        return ResponseUtil.success(token);

    }

    @PassToken
    @PostMapping("/register")
    public String register(@RequestParam("loginName") String userName,
                           @RequestParam("email") String email,
                           @RequestParam("loginName") String loginName,
                           @RequestParam("password") String password) {

        User user = new User(-1, userName, loginName, password, 2, "", email, "/#", new Date());
        Map<String, Object> rs = userService.register(user);
        if (rs.containsKey("error")) {
            return ResponseUtil.error(rs.get("error").toString());
        }
        String token = tokenService.getToken((User) rs.get("user"));
        tokenService.saveToken(token, user.getUserType(), 12);
        return ResponseUtil.success(token);
    }

    @GetMapping("/out_login")
    public String outLogin(@RequestParam("token") String token) {

        if (tokenService.removeToken(token)) {
            return ResponseUtil.success("令牌删除成功");
        }
        return ResponseUtil.error("令牌删除失败");
    }

    @GetMapping("/getUser")
    public String getUserByToken(@RequestParam("token") String token) {


        User user = tokenService.getTokenUser();
        if (user == null) {
            return ResponseUtil.error("错误令牌!!");
        }
        return ResponseUtil.success(JSON.toJSONString(user));
    }

    @PutMapping("/updatePassword")
    public String updatePassword(@RequestParam("token") String token,
                                 @RequestParam("password") String password) {

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

        if (!userService.verifyCode(loginName, code)) {
            return ResponseUtil.error("验证码不正确或已失效");
        }
        if (userService.repwd(loginName, password)) {
            return ResponseUtil.success("密码重置成功");
        }
        return ResponseUtil.error("密码重置失败");
    }

    @PostMapping("/uploadHead")
    public String uploadHead(@RequestParam("file") MultipartFile file,
                             @RequestParam("userId") String userId) {
        if (file.isEmpty()) {
            return ResponseUtil.error("空文件");
        }
        if (userService.uploadHead(file, userId)) {
            return ResponseUtil.success("头像上传成功");
        }
        return ResponseUtil.error("头像上传失败");
    }

    @PutMapping("/up_user")
    public String updateUser(@RequestParam("userId") String userId,
                             @RequestParam("userName") String userName,
                             @RequestParam("phoneNumber") String phoneNumber,
                             @RequestParam("email") String email) {
        try {
            User user = userService.getUserById(Integer.parseInt(userId));
            if (!userName.isEmpty()) {
                user.setUserName(userName);
            }
            if (!phoneNumber.isEmpty()) {
                user.setPhoneNumber(phoneNumber);
            }
            if (!email.isEmpty()) {
                user.setEmail(email);
            }
            userService.updateUser(user);
            return ResponseUtil.success("修改信息成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.error("修改信息失败");
    }

    @PassToken
    @GetMapping("/get_avatar")
    public Object getAvatar(@RequestParam("token") String token) {

        try {
            User user = tokenService.getTokenUser();
            String avatarPath = user.getAvatar();
            if (avatarPath == null) {
                return null;
            }
            return fileService.downloadUtil(avatarPath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

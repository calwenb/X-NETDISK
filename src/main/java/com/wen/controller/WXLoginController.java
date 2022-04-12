package com.wen.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wen.annotation.PassToken;
import com.wen.utils.HttpUtil;
import com.wen.utils.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 1 第一步：用户同意授权，获取code
 * <p>
 * 2 第二步：通过code换取网页授权access_token
 * <p>
 * 3 第三步：刷新access_token（如果需要）
 * <p>
 * 4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
 * <p>
 * 5 附：检验授权凭证（access_token）是否有效
 */
@Controller
@RequestMapping("/wxlogin")
public class WXLoginController {
    String APPID = "wxd2cd8d720ce064ea";
    String SECRET = "a16cf5ca9fa2a948d783ac23c3482233";
    String localUrl = "http%3A%2F%2Fpan-after.wenyo.top%2Fwxlogin%2F";


    @PassToken
    @GetMapping("/getUrl")
    public String getCode(HttpServletRequest request) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=12#wechat_redirect";
        String apiUrl = String.format(url, APPID, localUrl + "getinfo");
        System.out.println("redirect:"+apiUrl);
        return "redirect:"+apiUrl;
    }

    @PassToken
    @ResponseBody
    @GetMapping("/getinfo")
    public String getToken(@RequestParam("code") String code,
                           @RequestParam("state")String state) {
        System.out.println(state);
        System.out.println("getToken");
        System.out.println(code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        String apiUrl = String.format(url, APPID, SECRET, code);
        String resp = HttpUtil.sendGet(apiUrl);
        JSONObject object = JSON.parseObject(resp);
        System.out.println(object.toString());

        String accessToken = object.getString("access_token");
        String openId = object.getString("openid");
        url = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
        apiUrl = String.format(url, accessToken, openId);
        resp = HttpUtil.sendGet(apiUrl);
        object = JSON.parseObject(resp);
        System.out.println(object.toString());
        String openid = object.getString("openid");
        String userName = object.getString("nickname");
        String headImgUrl = object.getString("headimgurl");

        System.out.println(openid);
        System.out.println(userName);
        System.out.println(headImgUrl);

        return openid + " " + userName + " " + headImgUrl;

    }
}

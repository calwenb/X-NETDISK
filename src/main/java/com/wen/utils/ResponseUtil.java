package com.wen.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

/**
 * ResponseUtil类
 * 约束响应规则
 *
 * @author Mr.文
 */
public class ResponseUtil {

    private static final String SUCCESS_CODE = "200";
    private static final String FILE_MISS_CODE = "404";
    private static final String ERROR_CODE = "700";
    private static final String U_FILE_ERROR_CODE = "701";

    private static final String D_FILE_ERROR_CODE = "702";
    private static final String POWER_ERROR_CODE = "401";
    private static final String BAD_REQUEST_CODE = "400";


    public static String success(String msg) {
        HashMap<String, String> resp = new HashMap<>(4);
        resp.put("code", SUCCESS_CODE);
        resp.put("msg", msg);
        return JSON.toJSONString(resp);
    }

    public static String error(String msg) {
        HashMap<String, String> resp = new HashMap<>(4);
        resp.put("code", ERROR_CODE);
        resp.put("msg", msg);
        return JSON.toJSONString(resp);
    }

    public static String uploadFileError(String msg) {
        HashMap<String, String> resp = new HashMap<>(4);
        resp.put("code", D_FILE_ERROR_CODE);
        resp.put("msg", msg);
        return JSON.toJSONString(resp);
    }

    public static String downloadFileError(String msg) {
        HashMap<String, String> resp = new HashMap<>(4);
        resp.put("code", U_FILE_ERROR_CODE);
        resp.put("msg", msg);
        return JSON.toJSONString(resp);
    }

    public static String fileMiss(String msg) {
        HashMap<String, String> resp = new HashMap<>(4);
        resp.put("code", FILE_MISS_CODE);
        resp.put("msg", msg);
        return JSON.toJSONString(resp);
    }

    public static String powerError(String msg) {
        HashMap<String, String> resp = new HashMap<>(4);
        resp.put("code", POWER_ERROR_CODE);
        resp.put("msg", msg);
        return JSON.toJSONString(resp);

    }

    public static String badRequest(String msg) {
        HashMap<String, String> resp = new HashMap<>(4);
        resp.put("code", BAD_REQUEST_CODE);
        resp.put("msg", msg);
        return JSON.toJSONString(resp);
    }
}

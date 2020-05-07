package com.example.demo.utils;

import com.qiniu.util.StringMap;

public class Constants {

    public static final String USER_KEY = "user:"; // 用户缓存
    public static final String ROLE_KEY = "role:"; // 角色缓存
    public static final String PERMISSION_KEY = "permission:"; // 权限缓存

    public static final String PRODUCT_STOCK = "product:stock:"; // 库存
    public static final String PRODUCT_CART = "product:cart:"; // 购物车

    public static final Integer REDIS_CACHE_EXPIRE = 60 * 60; // Redis Cache 缓存过期时间 60*60s
    public static final Integer USER_EXPIRE = 60 * 60; // 用户缓存过期时间 60*60s
    public static final Integer ROLE_EXPIRE = 60 * 60; // 角色缓存过期时间 60*60s
    public static final Integer PERMISSION_EXPIRE = 60 * 60; // 权限缓存过期时间 60*60s

    public static final String LUA_SCRIPT =
            "if redis.call('get', KEYS[1]) >= ARGV[1] "
                    + "then "
                    + "return redis.call('decrby', KEYS[1], ARGV[1]) "
                    + "else "
                    + "return -1 "
                    + "end";

    public static final String ORDER_TOPIC = "order";
    public static final String ORDER_TTL_EXCHANGE = "order_ttl_exchange";
    public static final String ORDER_TTL_QUEUE = "order_ttl_queue";
    public static final String ORDER_TTL_KEY = "order_ttl_key";
    public static final String ORDER_TTL = String.valueOf(5 * 60 * 1000); // 5min
    public static final String ORDER_CANCEL_QUEUE = "order_cancel_queue";
    public static final String ORDER_CANCEL_EXCHANGE = "order_cancel_exchange";
    public static final String ORDER_CANCEL_KEY = "order_cancel_key";

    public static final Integer UPLOAD_RETRY = 3; // 重传次数
    public static final Integer EXPIRE_SECONDS = 60 * 60; // 过期时间
    public static final StringMap PUT_POLICY = new StringMap().put("returnBody", // 返回格式
            "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":\"$(fsize)\",\"width\":\"$(imageInfo.width)\", \"height\":\"$(imageInfo.height)\"}");

    public static final String PAY_PRODUCT_CODE = "FAST_INSTANT_TRADE_PAY";
    public static final String PAY_SUBJECT = "PC支付测试";
    public static final String PAY_BODY = "支付宝PC支付测试";
    public static final String PAY_SUCCESS_RETURN = "<html>" +
            "<head>" +
            "<meta charset=\"utf-8\">" +
            "</head>" +
            "<body onload='alert(\"支付成功\");window.history.go(-3);'>" +
            "</body>" +
            "</html>"; // 回退历史-3
    public static final String PAY_FAILURE_RETURN = "<html>" +
            "<head>" +
            "<meta charset=\"utf-8\">" +
            "</head>" +
            "<body onload='alert(\"支付失败\");window.history.go(-3);'>" +
            "</body>" +
            "</html>"; // 回退历史-3

    public static final Integer CODE_WIDTH = 120; // 验证码宽度
    public static final Integer CODE_HEIGHT = 38; // 验证码高度
    public static final Integer CODE_LENGTH = 4; //验证码字符长度
    public static final Integer CODE_EXPIRE = 60; // 验证码过期时间 60s
}

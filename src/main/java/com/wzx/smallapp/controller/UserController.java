package com.wzx.smallapp.controller;

import com.wzx.smallapp.domain.Address;
import com.wzx.smallapp.domain.Order;
import com.wzx.smallapp.service.UserService;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by wzx on 2019/9/5.
 */
@RestController
public class UserController {

    /**
     * code2Session地址
     */
    @Value("${wx.code2Session}")
    private String code2Session;
    /**
     * appid
     */
    @Value("${wx.appid}")
    private String appid;
    /**
     * secret
     */
    @Value("${wx.secret}")
    private String secret;

    @Resource
    private UserService userService;
    /**
     * 获取openid
     * @return
     */
    @RequestMapping(value = "/getOpenId")
    public String getOpenId(String code){
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder =HttpUrl.parse(code2Session)
                .newBuilder();
        urlBuilder.addQueryParameter("appid", appid);
        urlBuilder.addQueryParameter("secret", secret);
        urlBuilder.addQueryParameter("js_code", code);
        urlBuilder.addQueryParameter("grant_type", "authorization_code");
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 创建订单
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Integer create(@RequestBody Order order){
        return userService.createOrder(order);
    }
    /**
     * 订单列表
     * @return
     */
    @RequestMapping(value = "/orderList",method = RequestMethod.GET)
    public List<Order> orderList(String openid,Integer status){
        return userService.orderList(openid,status);
    }

    /**
     * 更新订单状态
     * @return
     */
    @RequestMapping(value = "/changeOrder",method = RequestMethod.POST)
    public void changeOrder(@RequestBody Order order){
         userService.changeOrder(order);
    }

    /**
     * 订单支付(临时更新支付状态)
     * @return
     */
    @RequestMapping(value = "/pay",method = RequestMethod.GET)
    public Map pay(Integer orderId){
        return userService.pay(orderId);
    }

    /**
     * 订单数字
     * @return
     */
    @RequestMapping(value = "/orderStatistics",method = RequestMethod.GET)
    public Map orderStatistics(String openid){
        return userService.orderStatistics(openid);
    }
}


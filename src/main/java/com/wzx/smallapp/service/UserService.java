package com.wzx.smallapp.service;

import com.wzx.smallapp.domain.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by wzx on 2019/9/6.
 */
public interface UserService {

    /**
     * 创建订单
     * @param order
     * @return 订单id
     */
    Integer createOrder(Order order);

    /**
     * 订单列表
     * @param openid
     * @return
     */
    List<Order> orderList(String openid,Integer status);

    /**
     * 订单count
     * @param openid
     * @return
     */
    Map orderStatistics(String openid);

    /**
     * 更新订单状态
     * @param order
     */
    void changeOrder(Order order);

    /**
     * 支付订单
     * @param orderId
     * @return
     */
    Map pay(Integer orderId);
}

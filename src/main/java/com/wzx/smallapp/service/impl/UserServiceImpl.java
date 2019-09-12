package com.wzx.smallapp.service.impl;

import com.wzx.smallapp.domain.Address;
import com.wzx.smallapp.domain.Order;
import com.wzx.smallapp.mapper.AddressMapper;
import com.wzx.smallapp.mapper.OrderMapper;
import com.wzx.smallapp.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by wzx on 2019/9/6.
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private OrderMapper orderMapper;

    @Transactional
    @Override
    public Integer createOrder(Order order) {
        //查询商品价格
        Address address = order.getAddress();
        //插入数据库
        addressMapper.insert(address);
        order.setAddressId(address.getId());
        order.setCreateTime(new Date());
        //生成订单号
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmSS");
        String date = sf.format(new Date());
        Random ne=new Random();//实例化一个random的对象ne
        int x = ne.nextInt(899)+100;//为变量赋随机值100-999
        String orderNumber = date+x;
        order.setOrderNumber(orderNumber);
        orderMapper.insertSelective(order);
        return order.getId();
    }

    @Override
    public List<Order> orderList(String openid,Integer status) {
        List<Order> orders = orderMapper.selectByOpenId(openid,status);
        return orders;
    }

    @Override
    public Map orderStatistics(String openid) {
        return orderMapper.countByStatus(openid);
    }

    @Override
    public void changeOrder(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Map pay(Integer orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        order.setPayStatus(1);//待发货
        orderMapper.updateByPrimaryKeySelective(order);
        return null;
    }
}

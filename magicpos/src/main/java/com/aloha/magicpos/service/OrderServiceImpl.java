package com.aloha.magicpos.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.magicpos.domain.Orders;
import com.aloha.magicpos.domain.OrdersDetails;
import com.aloha.magicpos.mapper.OrderDetailMapper;
import com.aloha.magicpos.mapper.OrderMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("OrderService")
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public boolean insertOrder(Orders order) throws Exception {
        return orderMapper.insert(order) > 0;
    }

    @Override
    public boolean insertOrderDetail(Long oNo, OrdersDetails detail) throws Exception {
        detail.setONo(oNo);
        return orderDetailMapper.insert(detail) > 0;
    }

    @Override
    public boolean updateStatus(Long no, String orderStatus, int paymentStatus) throws Exception {
        return orderMapper.updateStatus(no, orderStatus, paymentStatus) >0;
    }

    @Override
    public boolean deleteOrder(Long no) throws Exception {
        int detailsDeleted = orderDetailMapper.deleteByOrderNo(no);  // 상세 먼저 삭제
        int orderDeleted = orderMapper.delete(no);                 // 주문 삭제
        return detailsDeleted >= 0 && orderDeleted > 0;
    }

    @Override
    public List<Orders> findAllOrders() throws Exception {
        return orderMapper.findAll();
    }

    @Override
    public List<Orders> findOrdersByUser(Long uNo) throws Exception {
        return orderMapper.findByUser(uNo);
    }

    @Override
    public Orders findOrderByNo(Long no) throws Exception {
        return orderMapper.findByNo(no);
    }

    @Override
    public List<OrdersDetails> findOrderDetails(Long oNo) throws Exception {
        return orderDetailMapper.findByOrderNo(oNo);
    }

    @Override
    public List<Map<String, Object>> findDetailsWithProductNames(Long oNo) throws Exception {
        return orderDetailMapper.findDetailsWithProductNamesByOrderNo(oNo);
    }

    @Override
    public boolean updateOrderDetailQuantity(Long oNo, Long pNo, int quantity) throws Exception {
        return orderDetailMapper.updateQuantity(oNo, pNo, quantity) >0;
    }

    @Override
    public boolean deleteOrderDetail(Long oNo, Long pNo) throws Exception {
        return orderDetailMapper.delete(oNo, pNo) > 0;
    }
    
}

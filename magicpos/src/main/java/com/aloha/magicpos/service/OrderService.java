package com.aloha.magicpos.service;

import java.util.List;
import java.util.Map;

import com.aloha.magicpos.domain.Orders;
import com.aloha.magicpos.domain.OrdersDetails;

public interface OrderService {
    public boolean insertOrder(Orders order) throws Exception;
    public boolean insertOrderDetail(Long oNo, OrdersDetails detail) throws Exception;
    public boolean updateStatus(Long no, String orderStatus, int paymentStatus) throws Exception;
    public boolean deleteOrder(Long no) throws Exception;
    public List<Orders> findAllOrders() throws Exception;
    public List<Orders> findOrdersByUser(Long uNo) throws Exception;
    public Orders findOrderByNo(Long no) throws Exception;
    public List<OrdersDetails> findOrderDetails(Long oNo) throws Exception;
    public List<Map<String, Object>> findDetailsWithProductNames(Long oNo) throws Exception;
    public boolean updateOrderDetailQuantity(Long oNo, Long pNo, int quantity) throws Exception;
    public boolean deleteOrderDetail(Long oNo, Long pNo) throws Exception;
}

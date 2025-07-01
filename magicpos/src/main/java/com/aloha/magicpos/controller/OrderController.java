package com.aloha.magicpos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.magicpos.domain.Orders;
import com.aloha.magicpos.domain.OrdersDetails;
import com.aloha.magicpos.mapper.OrderDetailMapper;
import com.aloha.magicpos.mapper.OrderMapper;

public class OrderController {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    // 🔸 주문 등록
    @PostMapping
    public String insertOrder(@RequestBody Orders order) {
        orderMapper.insert(order);
        return "order_created";
    }

    // 🔸 주문 상세 등록
    @PostMapping("/{oNo}/details")
    public String insertOrderDetail(@PathVariable Long oNo, @RequestBody OrdersDetails detail) {
        detail.setONo(oNo);
        orderDetailMapper.insert(detail);
        return "order_detail_created";
    }

    // 🔸 주문 상태/결제 상태 수정
    @PutMapping("/{no}/status")
    public String updateStatus(@PathVariable Long no,
                               @RequestParam String orderStatus,
                               @RequestParam int paymentStatus) {
        orderMapper.updateStatus(no, orderStatus, paymentStatus);
        return "order_status_updated";
    }

    // 🔸 주문 삭제 (주문 + 상세 함께 삭제)
    @DeleteMapping("/{no}")
    public String deleteOrder(@PathVariable Long no) {
        orderDetailMapper.deleteByOrderNo(no);  // 상세 먼저 삭제
        orderMapper.delete(no);                 // 주문 삭제
        return "order_deleted";
    }

    // 🔸 모든 주문 조회
    @GetMapping
    public List<Orders> findAllOrders() {
        return orderMapper.findAll();
    }

    // 🔸 특정 사용자 주문 목록 조회
    @GetMapping("/user/{uNo}")
    public List<Orders> findOrdersByUser(@PathVariable Long uNo) {
        return orderMapper.findByUser(uNo);
    }

    // 🔸 단일 주문 조회
    @GetMapping("/{no}")
    public Orders findOrderByNo(@PathVariable Long no) {
        return orderMapper.findByNo(no);
    }

    // 🔸 주문 상세 목록 조회 (단순)
    @GetMapping("/{oNo}/details")
    public List<OrdersDetails> findOrderDetails(@PathVariable Long oNo) {
        return orderDetailMapper.findByOrderNo(oNo);
    }

    // 🔸 주문 상세 목록 조회 (상품명 + 가격 포함)
    @GetMapping("/{oNo}/details/products")
    public List<Map<String, Object>> findDetailsWithProductNames(@PathVariable Long oNo) {
        return orderDetailMapper.findDetailsWithProductNamesByOrderNo(oNo);
    }

    // 🔸 주문 상세 수량 수정
    @PutMapping("/{oNo}/details/{pNo}/quantity")
    public String updateOrderDetailQuantity(@PathVariable Long oNo,
                                            @PathVariable Long pNo,
                                            @RequestParam int quantity) {
        orderDetailMapper.updateQuantity(oNo, pNo, quantity);
        return "order_detail_quantity_updated";
    }

    // 🔸 주문 상세 삭제 (단일 상품)
    @DeleteMapping("/{oNo}/details/{pNo}")
    public String deleteOrderDetail(@PathVariable Long oNo, @PathVariable Long pNo) {
        orderDetailMapper.delete(oNo, pNo);
        return "order_detail_deleted";
    }
}

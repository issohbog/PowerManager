package com.aloha.magicpos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.magicpos.domain.Orders;
import com.aloha.magicpos.domain.OrdersDetails;
import com.aloha.magicpos.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 🔸 주문 등록
    @PostMapping
    public String insertOrder(@RequestBody Orders order) throws Exception{
        orderService.insertOrder(order);
        return "order_created";
    }

    // 🔸 주문 상세 등록
    @PostMapping("/{oNo}/details")
    public String insertOrderDetail(@PathVariable Long oNo, @RequestBody OrdersDetails detail) throws Exception{
        orderService.insertOrderDetail(oNo, detail);
        return "order_detail_created";
    }

    // 🔸 주문 상태/결제 상태 수정
    @PutMapping("/{no}/status")
    public String updateStatus(@PathVariable Long no,
                               @RequestParam String orderStatus,
                               @RequestParam int paymentStatus) 
        throws Exception{
        orderService.updateStatus(no, orderStatus, paymentStatus);
        return "order_status_updated";
    }

    // 🔸 주문 삭제 (주문 + 상세 함께 삭제)
    @DeleteMapping("/{no}")
    public String deleteOrder(@PathVariable Long no) throws Exception{
        orderService.deleteOrder(no);
        return "order_deleted";
    }

    // 🔸 모든 주문 조회
    @GetMapping
    public List<Orders> findAllOrders() throws Exception {
        return orderService.findAllOrders();
    }

    // 🔸 특정 사용자 주문 목록 조회
    @GetMapping("/user/{uNo}")
    public List<Orders> findOrdersByUser(@PathVariable Long uNo) throws Exception {
        return orderService.findOrdersByUser(uNo);
    }

    // 🔸 단일 주문 조회
    @GetMapping("/{no}")
    public Orders findOrderByNo(@PathVariable Long no) throws Exception {
        return orderService.findOrderByNo(no);
    }

    // 🔸 주문 상세 목록 조회 (단순)
    @GetMapping("/{oNo}/details")
    public List<OrdersDetails> findOrderDetails(@PathVariable Long oNo) throws Exception {
        return orderService.findOrderDetails(oNo);
    }

    // 🔸 주문 상세 목록 조회 (상품명 + 가격 포함)
    @GetMapping("/{oNo}/details/products")
    public List<Map<String, Object>> findDetailsWithProductNames(@PathVariable Long oNo) throws Exception{
        return orderService.findDetailsWithProductNames(oNo);
    }

    // 🔸 주문 상세 수량 수정
    @PutMapping("/{oNo}/details/{pNo}/quantity")
    public String updateOrderDetailQuantity(@PathVariable Long oNo,
                                            @PathVariable Long pNo,
                                            @RequestParam int quantity) 
        throws Exception{
        orderService.updateOrderDetailQuantity(oNo, pNo, quantity);
        return "order_detail_quantity_updated";
    }

    // 🔸 주문 상세 삭제 (단일 상품)
    @DeleteMapping("/{oNo}/details/{pNo}")
    public String deleteOrderDetail(@PathVariable Long oNo, @PathVariable Long pNo) throws Exception{
        orderService.deleteOrderDetail(oNo, pNo);
        return "order_detail_deleted";
    }
}

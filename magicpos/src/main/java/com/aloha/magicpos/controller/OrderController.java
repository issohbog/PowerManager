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
import com.aloha.magicpos.service.CartService;
import com.aloha.magicpos.service.OrderService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    // 🔸 주문 등록
    @PostMapping("/create")
    public String insertOrder(
        Orders order, // 기본 주문 정보는 그대로 받고
        @RequestParam("seatId") String seatId,
        @RequestParam("pNoList") List<Long> pNoList,
        @RequestParam("quantityList") List<Long> quantityList,
        RedirectAttributes rttr, // 리다이렉트 시 플래시 속성 사용
        HttpSession session // 세션에서 사용자 정보 가져오기
    ) throws Exception {
        // ✅ 1. 세션에서 userNo 가져오기
        Long userNo = (Long) session.getAttribute("userNo");

        // ✅ 2. 세션에 없으면 임시 userNo로 설정
        if (userNo == null) {
            userNo = 1L; // 임시 유저 번호
            session.setAttribute("userNo", userNo);
        }
        // 🔽 여기서 seatId 로그 확인
        log.debug("넘어온 seatId: {}", order.getSeatId());
        order.setUNo(userNo); // 주문에 사용자 번호 설정
        order.setOrderStatus(0L); // 기본 주문 상태 설정
        order.setPaymentStatus(0L); // 기본 결제 상태 설정
        order.setSeatId(seatId);
        boolean inserted = orderService.insertOrder(order);
        if (!inserted) return "redirect:/orders/fail";

        Long oNo = order.getNo(); // insert 후에 받아온 주문 번호

        // 상품별 주문 상세 넣기
        for (int i = 0; i < pNoList.size(); i++) {
            OrdersDetails detail = new OrdersDetails();
            detail.setONo(oNo);
            detail.setPNo(pNoList.get(i));
            detail.setQuantity(quantityList.get(i));
            orderService.insertOrderDetail(oNo, detail);
        }
        // 장바구니 비우기
        cartService.deleteAllByUserNo(userNo);

        rttr.addFlashAttribute("orderSuccess", true);
        return "redirect:/menu";
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

    // 🔸 특정 사용자 주문 목록 조회(사용자페이지 사용)
    @GetMapping("/user")
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

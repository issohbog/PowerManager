package com.aloha.magicpos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aloha.magicpos.domain.Orders;
import com.aloha.magicpos.domain.Seats;
import com.aloha.magicpos.service.OrderService;
import com.aloha.magicpos.service.SeatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
public class AdminController {


    private final SeatService seatService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/admin")
    public String findAllSeat(Model model) {
        
        Map<String, List<Seats>> seatMap = seatService.getSeatSections();

        model.addAttribute("topSeats", seatMap.get("topSeats"));
        model.addAttribute("middleSeats", seatMap.get("middleSeats"));
        model.addAttribute("bottomSeats", seatMap.get("bottomSeats"));
    
        return "pages/admin/seat_status";
    
    }
    @GetMapping("/admin/orderpopup")
    public String orderpopup(Model model) {
        try {
            List<Orders> orderList = orderService.findAllOrders();
            model.addAttribute("orderList", orderList);

            Map<Long, List<Map<String, Object>>> orderDetailsMap = new HashMap<>();
            for (Orders order : orderList) {
                Long oNo = order.getNo();
                List<Map<String, Object>> details = orderService.findDetailsWithProductNames(oNo);
                if (details == null || details.isEmpty()) {
                    log.warn("❗ 주문 상세 없음: orderNo = {}", oNo);
                    details = new ArrayList<>();
                }
                orderDetailsMap.put(oNo, details);
            }
            // ✅ 여기부터 메뉴 이름 , 로 이어붙이기
            Map<Long, String> menuNamesMap = new HashMap<>();

            for (Orders order : orderList) {
                Long oNo = order.getNo();
                List<Map<String, Object>> details = orderDetailsMap.get(oNo);

                if (details != null && !details.isEmpty()) {
                    String names = details.stream()
                        .map(d -> {
                            String name = d.get("p_name").toString();
                            Object quantityObj = d.get("quantity");
                            int quantity = (quantityObj != null) ? Integer.parseInt(quantityObj.toString()) : 1;
                            return name + "(" + quantity + ")";
                        })
                        .collect(Collectors.joining(", "));
                    menuNamesMap.put(oNo, names);
                } else {
                    menuNamesMap.put(oNo, "");
                }
            }
            model.addAttribute("menuNamesMap", menuNamesMap);

            model.addAttribute("menuNamesMap", menuNamesMap);
            model.addAttribute("orderDetailsMap", orderDetailsMap);
            model.addAttribute("orderCount", orderList.size());
            model.addAttribute("preparingCount", orderList.stream().filter(o -> o.getOrderStatus() == 1).count());
            // 주문별 대기시간 계산 (현재시간 - orderTime)
            Map<Long, Long> waitTimeMap = new HashMap<>();
            long now = System.currentTimeMillis();
            for (Orders order : orderList) {
                if (order.getOrderTime() != null) {
                    long waitMillis = now - order.getOrderTime().getTime();
                    long waitMinutes = waitMillis / (60 * 1000);
                    waitTimeMap.put(order.getNo(), waitMinutes);
                } else {
                    waitTimeMap.put(order.getNo(), 0L);
                }
            }
            model.addAttribute("waitTime", waitTimeMap);
        } catch (Exception e) {
            log.error("❗ 관리자 주문팝업 오류", e);
            model.addAttribute("errorMessage", "주문 정보를 불러오는 중 오류 발생");
        }

        return "pages/admin/orderpopup";
    }
}
    


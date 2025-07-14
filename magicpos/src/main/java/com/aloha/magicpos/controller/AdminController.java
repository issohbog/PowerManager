package com.aloha.magicpos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.aloha.magicpos.domain.Carts;
import com.aloha.magicpos.domain.Categories;
import com.aloha.magicpos.domain.Orders;
import com.aloha.magicpos.domain.OrdersDetails;
import com.aloha.magicpos.domain.Products;
import com.aloha.magicpos.domain.Seats;
import com.aloha.magicpos.service.CartService;
import com.aloha.magicpos.service.CategoryService;
import com.aloha.magicpos.service.OrderService;
import com.aloha.magicpos.service.ProductService;
import com.aloha.magicpos.service.SeatService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;




@RequiredArgsConstructor
@Slf4j
@Controller
public class AdminController {


    private final SeatService seatService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CartService cartService;


    @GetMapping("/admin/orderpopup/fetch")
    public String fetchOrderPopup(@RequestParam(name = "status", required = false) String status, Model model) throws Exception {
    List<Long> statusList = "1".equals(status) ? List.of(1L) : List.of(0L, 1L);
    List<Orders> orderList = orderService.findOrdersByStatus(statusList);
    
    System.out.println("🔥 orderList size: " + orderList.size()); // 이거 넣어서 확인해봐
    model.addAttribute("orderList", orderList);

    Map<Long, List<Map<String, Object>>> orderDetailsMap = new HashMap<>();
    Map<Long, String> menuNamesMap = new HashMap<>();
    Map<Long, Long> waitTimeMap = new HashMap<>();
    long now = System.currentTimeMillis();

    for (Orders order : orderList) {
        Long oNo = order.getNo();
        List<Map<String, Object>> details = orderService.findDetailsWithProductNames(oNo);

        if (details == null) details = new ArrayList<>();
        orderDetailsMap.put(oNo, details);

        // 메뉴 이름 조합
        String names = details.stream()
            .map(d -> {
                String name = d.get("p_name") != null ? d.get("p_name").toString() : "이름없음";
                Object qObj = d.get("quantity");
                int quantity = (qObj != null) ? Integer.parseInt(qObj.toString()) : 1;
                return name + "(" + quantity + ")";
            })
            .collect(Collectors.joining(", "));
        menuNamesMap.put(oNo, names);

        // 대기 시간 계산
        if (order.getOrderTime() != null) {
            long waitMillis = now - order.getOrderTime().getTime();
            waitTimeMap.put(oNo, waitMillis / (60 * 1000));
        } else {
            waitTimeMap.put(oNo, 0L);
        }
    }

    model.addAttribute("menuNamesMap", menuNamesMap);
    model.addAttribute("orderDetailsMap", orderDetailsMap);
    model.addAttribute("orderCount", orderService.countByStatus(List.of(0L, 1L)));
    model.addAttribute("preparingCount", orderService.countByStatus(List.of(1L)));
    model.addAttribute("waitTime", waitTimeMap);
    model.addAttribute("requestURI", "/admin/orderpopup");

    return "fragments/admin/orderpopup :: orderpopup"; // ✅ fragment만!
    }


    @GetMapping("/admin")
    public String findAllSeat(Model model) throws Exception {
        
        Map<String, List<Seats>> seatMap = seatService.getSeatSections();

        model.addAttribute("topSeats", seatMap.get("topSeats"));
        model.addAttribute("middleSeats", seatMap.get("middleSeats"));
        model.addAttribute("bottomSeats", seatMap.get("bottomSeats"));
    
        return "pages/admin/seat_status";
    
    }

    // @PostMapping("/admin/seats/clear/{seatId}")
    // @ResponseBody
    // public String clearSeat(@PathVariable String seatId) {
    //     try {
    //         // boolean result = seatService.clearSeat(seatId);
    //         // return result ? "success" : "fail";
    //     } catch (Exception e) {
    //         return "error";
    //     }   
    // }
    

    @GetMapping("/admin/sell/counter")
    public String sellcounter(@RequestParam(name = "keyword", required = false) String keyword,Model model, HttpSession session) throws Exception {
        // ✅ 1. 세션에서 userNo 가져오기
        Long userNo = (Long) session.getAttribute("userNo");

        // ✅ 2. 세션에 없으면 임시 userNo로 설정
        if (userNo == null) {
            userNo = 1L; // 임시 유저 번호
            session.setAttribute("userNo", userNo);
        }
        // ✅ 3. 상품 목록 조회(검색 기능 포함)
        List<Products> products;
        if (keyword != null && !keyword.trim().isEmpty()) {
            products = productService.searchProductsAll(keyword);
        } else {
            products = productService.findAll();
        }
        model.addAttribute("products", products);
        // -------------------------------------------------------------------
        // 장바구니
        List<Map<String, Object>> cartList = cartService.getCartWithProductByUser(userNo);
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        model.addAttribute("cartList", cartList);

        // 장바구니 총 주문 금액
        int totalPrice = cartService.getTotalPrice(userNo);
        model.addAttribute("totalPrice", totalPrice);

        List<Categories> categories = categoryService.findAll();
        
        
        // List<Categories>를 MCategories 객체들을 카테고리번호(no)를 키, 카테고리이름(cName)을 값으로 해서 
        // Map<번호, 이름> 형태로 변환
        Map<Long, String> categoryMap = categories.stream()
        .collect(Collectors.toMap(Categories:: getNo, Categories::getCName));
        
        model.addAttribute("products", products);
        model.addAttribute("categoryMap", categoryMap);
        return "pages/admin/sellcounter";
    }
    
    // 장바구니에 항목 추가
    @PostMapping("/admin/sellcounter/add")
    public String addToCart(Carts carts, HttpSession session) throws Exception {
        Long uNo = (Long) session.getAttribute("userNo"); // 로그인 시 저장해뒀던 세션에서 꺼냄
        carts.setUNo(uNo); // 서버에서 직접 넣어줌
        if (carts.getQuantity() == null) {
            carts.setQuantity(1L); // 기본값 1
        }
        cartService.addToCart(carts);
        return "redirect:/admin/sell/counter";
    }

    // 장바구니 항목 삭제
    @PostMapping("/admin/sellcounter/delete")
    public String deleteItem(@RequestParam("cNo") Long cNo) throws Exception{
        cartService.delete(cNo);
        return "redirect:/admin/sell/counter";
    }
    
    // 장바구니 수량 증가
    @PostMapping("/admin/sellcounter/increase")
    public String increaseQuantity(@RequestParam("pNo") Long pNo, HttpSession session) throws Exception{
        Long uNo = (Long) session.getAttribute("userNo");
        cartService.increaseQuantity(uNo, pNo);
        return "redirect:/admin/sell/counter";
    }

    // 장바구니 수량 감소
    @PostMapping("/admin/sellcounter/decrease")
    public String decreaseQuantity(@RequestParam("pNo") Long pNo, HttpSession session) throws Exception{
        Long uNo = (Long) session.getAttribute("userNo");
        cartService.decreaseQuantity(uNo,pNo);
        return "redirect:/admin/sell/counter";
    }
    // 🔸 주문 등록
    @PostMapping("/admin/sellcounter/create")
    public String insertOrder(
        Orders order, // 기본 주문 정보는 그대로 받고
        @RequestParam("seatId") String seatId,
        @RequestParam("pNoList") List<Long> pNoList,
        @RequestParam("quantityList") List<Long> quantityList,
        @RequestParam("pNameList") List<String> pNameList, // 상품 이름 리스트 추가
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
            
            // ✅ 3. 주문 전 재고 체크
            for (int i = 0; i < pNoList.size(); i++) {
                Long pNo = pNoList.get(i);
                Long quantity = quantityList.get(i);
                String pName = pNameList.get(i);

                // 이 메서드에서 재고 수량 조회
                Long currentStock = productService.selectStockByPNo(pNo);  // 아래에 구현 설명 있음

                if (currentStock == null || currentStock < quantity) {
                    rttr.addFlashAttribute("error", pName + "의 재고가 부족합니다.");
                    return "redirect:/admin/sell/counter";
                }
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
            // 상품 재고 감소
            productService.decreaseStock(pNoList.get(i), quantityList.get(i));
        }
        // 장바구니 비우기
        cartService.deleteAllByUserNo(userNo);
        
        rttr.addFlashAttribute("orderSuccess", true);
        return "redirect:/admin/sell/counter";
    }
    

    /**
     * 관리자 주문 팝업 - 주문 취소
     * @param orderNo
     * @param model
     * @return
     * @throws Exception 
     */
    @GetMapping("/admin/cancel/{orderNo}")
    public String cancelOrderPopup(@PathVariable("orderNo") Long orderNo, Model model) throws Exception {

        Orders order = orderService.findOrderByNo(orderNo);
        List<Map<String, Object>> orderDetails = orderService.findDetailsWithProductNames(orderNo); // ← 여기 수정!

        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);

        return "fragments/admin/modal/orderCancel :: orderCancel";
    }


    // /**
    //  * 관리자 주문 팝업 - 준비중 주문 조회
    //  * @param model
    //  * @return
    //  */
    // @GetMapping("/admin/orderpopup/preparing")
    // public String orderpopupPreparing(Model model, HttpServletRequest request) {
    //     try {
    //         model.addAttribute("requestURI", request.getRequestURI());
    //         List<Orders> orderList = orderService.findOrdersByStatus(List.of(1L)); // 주문 상태가 1 인 주문만 조회
    //         model.addAttribute("orderList", orderList);

    //         Map<Long, List<Map<String, Object>>> orderDetailsMap = new HashMap<>();
    //         for (Orders order : orderList) {
    //             Long oNo = order.getNo();
    //             List<Map<String, Object>> details = orderService.findDetailsWithProductNames(oNo);
    //             if (details == null || details.isEmpty()) {
    //                 log.warn("❗ 주문 상세 없음: orderNo = {}", oNo);
    //                 details = new ArrayList<>();
    //             }
    //             orderDetailsMap.put(oNo, details);
    //         }
    //         // ✅ 여기부터 메뉴 이름 , 로 이어붙이기
    //         Map<Long, String> menuNamesMap = new HashMap<>();
        
    //         for (Orders order : orderList) {
    //             Long oNo = order.getNo();
    //             List<Map<String, Object>> details = orderDetailsMap.get(oNo);
        
    //             if (details != null && !details.isEmpty()) {
    //                 String names = details.stream()
    //                     .map(d -> {
    //                         String name = d.get("p_name").toString();
    //                         Object quantityObj = d.get("quantity");
    //                         int quantity = (quantityObj != null) ? Integer.parseInt(quantityObj.toString()) : 1;
    //                         return name + "(" + quantity + ")";
    //                     })
    //                     .collect(Collectors.joining(", "));
    //                 menuNamesMap.put(oNo, names);
    //             } else {
    //                 menuNamesMap.put(oNo, "");
    //             }
    //         }
    //         model.addAttribute("menuNamesMap", menuNamesMap);
    //         model.addAttribute("orderDetailsMap", orderDetailsMap);
    //         model.addAttribute("orderCount", orderService.countByStatus(List.of(0L, 1L)));
    //         model.addAttribute("preparingCount", orderService.countByStatus(List.of(1L)));
    //         // 주문별 대기시간 계산 (현재시간 - orderTime)
    //         Map<Long, Long> waitTimeMap = new HashMap<>();
    //         long now = System.currentTimeMillis();
    //         for (Orders order : orderList) {
    //             if (order.getOrderTime() != null) {
    //                 long waitMillis = now - order.getOrderTime().getTime();
    //                 long waitMinutes = waitMillis / (60 * 1000);
    //                 waitTimeMap.put(order.getNo(), waitMinutes);
    //             } else {
    //                 waitTimeMap.put(order.getNo(), 0L);
    //             }
    //         }
    //         model.addAttribute("waitTime", waitTimeMap);
    //     } catch (Exception e) {
    //         log.error("❗ 관리자 주문팝업 오류", e);
    //         model.addAttribute("errorMessage", "주문 정보를 불러오는 중 오류 발생");
    //     }

    //     return "fragments/admin/orderpopup :: orderpopup";

    // }



    // /**
    //  * 관리자 주문 팝업 - 전체 주문 조회
    //  * @param model
    //  * @return
    //  */
    // @GetMapping("/admin/orderpopup")
    // public String orderpopup(Model model, HttpServletRequest request) {
    //     try {
    //         model.addAttribute("requestURI", request.getRequestURI());
    //         List<Orders> orderList = orderService.findOrdersByStatus(List.of(0L, 1L)); // 주문 상태가 0, 1 인 주문만 조회
    //         model.addAttribute("orderList", orderList);

    //         Map<Long, List<Map<String, Object>>> orderDetailsMap = new HashMap<>();
    //         for (Orders order : orderList) {
    //             Long oNo = order.getNo();
    //             List<Map<String, Object>> details = orderService.findDetailsWithProductNames(oNo);
    //             if (details == null || details.isEmpty()) {
    //                 log.warn("❗ 주문 상세 없음: orderNo = {}", oNo);
    //                 details = new ArrayList<>();
    //             }
    //             orderDetailsMap.put(oNo, details);
    //         }
    //         // ✅ 여기부터 메뉴 이름 , 로 이어붙이기
    //         Map<Long, String> menuNamesMap = new HashMap<>();

    //         for (Orders order : orderList) {
    //             Long oNo = order.getNo();
    //             List<Map<String, Object>> details = orderDetailsMap.get(oNo);

    //             if (details != null && !details.isEmpty()) {
    //                 String names = details.stream()
    //                     .map(d -> {
    //                         String name = d.get("p_name").toString();
    //                         Object quantityObj = d.get("quantity");
    //                         int quantity = (quantityObj != null) ? Integer.parseInt(quantityObj.toString()) : 1;
    //                         return name + "(" + quantity + ")";
    //                     })
    //                     .collect(Collectors.joining(", "));
    //                 menuNamesMap.put(oNo, names);
    //             } else {
    //                 menuNamesMap.put(oNo, "");
    //             }
    //         }
    //         model.addAttribute("menuNamesMap", menuNamesMap);

    //         model.addAttribute("menuNamesMap", menuNamesMap);
    //         model.addAttribute("orderDetailsMap", orderDetailsMap);
    //         model.addAttribute("orderCount", orderService.countByStatus(List.of(0L, 1L)));
    //         model.addAttribute("preparingCount", orderService.countByStatus(List.of(1L)));
    //         // 주문별 대기시간 계산 (현재시간 - orderTime)
    //         Map<Long, Long> waitTimeMap = new HashMap<>();
    //         long now = System.currentTimeMillis();
    //         for (Orders order : orderList) {
    //             if (order.getOrderTime() != null) {
    //                 long waitMillis = now - order.getOrderTime().getTime();
    //                 long waitMinutes = waitMillis / (60 * 1000);
    //                 waitTimeMap.put(order.getNo(), waitMinutes);
    //             } else {
    //                 waitTimeMap.put(order.getNo(), 0L);
    //             }
    //         }
    //         model.addAttribute("waitTime", waitTimeMap);
    //     } catch (Exception e) {
    //         log.error("❗ 관리자 주문팝업 오류", e);
    //         model.addAttribute("errorMessage", "주문 정보를 불러오는 중 오류 발생");
    //     }

    //     return "fragments/admin/orderpopup :: orderpopup";
    // }
}
    


package com.aloha.magicpos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.magicpos.domain.Carts;
import com.aloha.magicpos.service.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;
    
    // 장바구니에 항목 추가
    @PostMapping("/add")
    public String addToCart(Carts carts, HttpSession session) throws Exception {
        Long uNo = (Long) session.getAttribute("userNo"); // 로그인 시 저장해뒀던 세션에서 꺼냄
        carts.setUNo(uNo); // 서버에서 직접 넣어줌
        if (carts.getQuantity() == null) {
            carts.setQuantity(1L); // 기본값 1
        }
        cartService.addToCart(carts);
        return "redirect:/menu";
    }

    // 장바구니 항목 삭제
    @PostMapping("/delete")
    public String deleteItem(@RequestParam("cNo") Long cNo) throws Exception{
        cartService.delete(cNo);
        return "redirect:/menu";
    }
    
    // 장바구니 수량 증가
    @PostMapping("/increase")
    public String increaseQuantity(@RequestParam("pNo") Long pNo, HttpSession session) throws Exception{
        Long uNo = (Long) session.getAttribute("userNo");
        cartService.increaseQuantity(uNo, pNo);
        return "redirect:/menu";
    }

    // 장바구니 수량 감소
    @PostMapping("/decrease")
    public String decreaseQuantity(@RequestParam("pNo") Long pNo, HttpSession session) throws Exception{
        Long uNo = (Long) session.getAttribute("userNo");
        cartService.decreaseQuantity(uNo,pNo);
        return "redirect:/menu";
    }


    // 사용자 장바구니 전체 조회
    @GetMapping("/{uNo}")
    public List<Carts> getUserCart(@PathVariable Long uNo) throws Exception{
        return cartService.getUserCart(uNo);
    }
}

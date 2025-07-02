package com.aloha.magicpos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.magicpos.domain.Carts;
import com.aloha.magicpos.service.CartService;

@Controller
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;
    
    // 장바구니에 항목 추가
    @PostMapping
    public String addToCart(@RequestBody Carts carts) throws Exception {
        cartService.addToCart(carts);
        return "success";
    }

    // 장바구니 수량 증가
    @PostMapping("/increase")
    public String increaseQuantity(@RequestParam Long uNo, @RequestParam Long pNo) throws Exception{
        cartService.increaseQuantity(uNo, pNo);
        return "increased";
    }

    // 장바구니 수량 감소
    @PostMapping("/decrease")
    public String decreaseQuantity(@RequestParam Long uNo, @RequestParam Long pNo) throws Exception{
        cartService.decreaseQuantity(uNo,pNo);
        return "decreased";
    }

    // 장바구니 항목 삭제
    @DeleteMapping
    public String deleteItem(@RequestParam Long uNo, @RequestParam Long pNo) throws Exception{
        cartService.deleteItem(uNo, pNo);
        return "deleted";
    }

    // 사용자 장바구니 전체 조회
    @GetMapping("/{uNo}")
    public List<Carts> getUserCart(@PathVariable Long uNo) throws Exception{
        return cartService.getUserCart(uNo);
    }
}

package com.aloha.magicpos.service;

import java.util.List;

import com.aloha.magicpos.domain.Carts;

public interface CartService {
    public boolean addToCart(Carts carts) throws Exception;
    public boolean increaseQuantity(Long uNo,Long pNo) throws Exception;
    public boolean decreaseQuantity(Long uNo, Long pNo) throws Exception;
    public boolean deleteItem(Long uNo, Long pNo) throws Exception;
    public List<Carts> getUserCart(Long uNo) throws Exception;
}

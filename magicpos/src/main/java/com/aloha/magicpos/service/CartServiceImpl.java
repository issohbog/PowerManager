package com.aloha.magicpos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aloha.magicpos.domain.Carts;
import com.aloha.magicpos.mapper.CartMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("CartService")
public class CartServiceImpl implements CartService{

    @Autowired
    private CartMapper cartMapper;

    @Override
    public boolean addToCart(Carts carts) throws Exception {
        Carts existing = cartMapper.findByUserAndProduct(carts.getUNo(), carts.getPNo());
        if (existing != null) {
            return cartMapper.increaseQuantity(carts.getUNo(), carts.getPNo()) > 0;
        } else {
            return cartMapper.insert(carts) > 0;
        }
    }

    @Override
    public boolean increaseQuantity(Long uNo, Long pNo) throws Exception {
        return cartMapper.increaseQuantity(uNo, pNo) > 0;
    }

    @Override
    public boolean decreaseQuantity(Long uNo, Long pNo) throws Exception {
        return cartMapper.decreaseQuantity(uNo, pNo) > 0;
    }

    @Override
    public boolean deleteItem(Long uNo, Long pNo) throws Exception {
        return cartMapper.delete(uNo, pNo) > 0;
    }

    @Override
    public List<Carts> getUserCart(Long uNo) throws Exception {
        return cartMapper.findByUser(uNo);
    }


}

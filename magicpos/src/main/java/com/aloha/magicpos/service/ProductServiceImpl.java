package com.aloha.magicpos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.magicpos.domain.Products;
import com.aloha.magicpos.mapper.ProductMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("ProductService")
public class ProductServiceImpl implements ProductService  {

    @Autowired private ProductMapper productMapper;

    @Override
    public boolean insert(Products product) throws Exception {
        return productMapper.insert(product) > 0;
    }

    @Override
    public boolean update(Products product) throws Exception {
        return productMapper.update(product) > 0;
    }


    @Override
    public boolean delete(Long no) throws Exception {
        return productMapper.delete(no) > 0;
    }

    @Override
    public List<Products> findAll() throws Exception {
        return productMapper.findAll();
    }

    @Override
    public Products findById(Long no) throws Exception {
        return productMapper.findById(no);
    }

    @Override
    public List<Products> findByCategory(Long cNo) throws Exception {
        return productMapper.findByCategory(cNo);
    }

    @Override
    public boolean decreaseStock(Long pNo, int quantity) throws Exception {
        return productMapper.decreaseStock(pNo, quantity) > 0;
    }

    @Override
    public boolean increaseStock(Long pNo, int quantity) throws Exception {
        return productMapper.increaseStock(pNo, quantity) > 0;
    }

    @Override
    public List<Products> searchProducts(Long cNo, String keyword) throws Exception {
        return productMapper.searchProducts(cNo, keyword);
    }

    @Override
    public List<Products> searchProductsAll(String keyword) throws Exception {
        return productMapper.searchProductsAll(keyword);
    }   
    
}

package com.aloha.magicpos.service;

import java.util.List;

import com.aloha.magicpos.domain.Products;

public interface ProductService {
    // 상품 등록
    public boolean insert(Products product) throws Exception;

    // 상품 수정
    public boolean update(Products product) throws Exception;

    // 상품 삭제
    public boolean delete(Long no) throws Exception;

    // 전체 상품 조회
    public List<Products> findAll() throws Exception;

    // 단건 조회
    public Products findById(Long no) throws Exception;

    // 카테고리로 조회
    public List<Products> findByCategory(Long cNo) throws Exception;

    // 재고 감소
    public boolean decreaseStock(Long pNo, int quantity) throws Exception;

    // 재고 증가
    public boolean increaseStock(Long pNo, int quantity) throws Exception;

    // 🔍 상품 검색 (분류 + 키워드) throws Exception
    public List<Products> searchProducts(Long cNo, String keyword) throws Exception;

    // 🔍 상품 통합 검색
    public List<Products> searchProductsAll(String keyword) throws Exception;
}

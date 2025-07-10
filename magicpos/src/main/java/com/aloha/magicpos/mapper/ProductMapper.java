package com.aloha.magicpos.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.magicpos.domain.Products;

@Mapper
public interface ProductMapper {
    // 상품 등록
    int insert(Products product);

    // 상품 수정
    int update(Products product);

    // 상품 삭제
    int delete(@Param("no") Long no);

    // 전체 상품 조회
    List<Products> findAll();

    // 단건 조회
    Products findById(@Param("no") Long no);

    // 카테고리로 조회
    List<Products> findByCategory(@Param("cNo") Long cNo);

    // 상품 단건에 대해 당일 판매량 조회 
    List<Map<String, Object>> findTodaySalesMap();

    // 재고 감소
    int decreaseStock(@Param("pNo") Long pNo, @Param("quantity") int quantity);

    // 재고 증가
    int increaseStock(@Param("pNo") Long pNo, @Param("quantity") int quantity);

    // 재고 수정 
    int updateStock(@Param("pNo") Long pNo, @Param("stock") int stock);

    // 🔍 상품 검색 (분류 + 키워드)
    List<Products> searchProducts(@Param("cNo") Long cNo, @Param("keyword") String keyword);

    // 🔍 상품 통합 검색
    List<Products> searchProductsAll(@Param("keyword") String keyword);
}

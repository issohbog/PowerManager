package com.aloha.magicpos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.magicpos.domain.Products;
import com.aloha.magicpos.mapper.CategoryMapper;
import com.aloha.magicpos.mapper.ProductMapper;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;
    
    // 전체 상품 목록
    @GetMapping
    public String list(Model model) {
        List<Products> products = productMapper.findAll();
        model.addAttribute("products", products);
        return "product/list";
    }

    // 상품 등록 폼
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("product", new Products());
        model.addAttribute("categories", categoryMapper.findAll());
        return "product/form";
    }

    // 상품 등록 처리
    @PostMapping
    public String insert(Products product) {
        productMapper.insert(product);
        return "redirect:/products";
    }

    // 상품 수정 폼
    @GetMapping("/{no}/edit")
    public String edit(@PathVariable Long no, Model model) {
        Products product = productMapper.findById(no);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryMapper.findAll());
        return "product/form";
    }

    // 상품 수정 처리
    @PostMapping("/{no}")
    public String update(@PathVariable Long no, Products product) {
        product.setNo(no);
        productMapper.update(product);
        return "redirect:/products";
    }

    // 상품 삭제
    @PostMapping("/{no}/delete")
    public String delete(@PathVariable Long no) {
        productMapper.delete(no);
        return "redirect:/products";
    }

    // 🔍 상품 검색 (통합 검색)
    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<Products> products = productMapper.searchProductsAll(keyword);
        model.addAttribute("products", products);
        return "product/list";
    }

    // 🔍 상품 검색 + 분류 필터
    @GetMapping("/filter")
    public String filter(@RequestParam("cNo") long cNo,
                         @RequestParam("keyword") String keyword,
                         Model model) {
        List<Products> products = productMapper.searchProducts(cNo, keyword);
        model.addAttribute("products", products);
        return "product/list";
    }
}

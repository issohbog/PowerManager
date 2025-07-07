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
import com.aloha.magicpos.service.CategoryService;
import com.aloha.magicpos.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    // 전체 상품 목록
    @GetMapping("/productlist")
    public String list(Model model) throws Exception{
        List<Products> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/list";
    }


    // 상품 등록 폼
    @GetMapping("/new")
    public String form(Model model) throws Exception{
        model.addAttribute("product", new Products());
        model.addAttribute("categories", categoryService.findAll());
        return "product/form";
    }

    // 상품 등록 처리
    @PostMapping
    public String insert(Products product) throws Exception{
        productService.insert(product);
        return "redirect:/products";
    }

    // 상품 수정 폼
    @GetMapping("/{no}/edit")
    public String edit(@PathVariable Long no, Model model) throws Exception{
        Products product = productService.findById(no);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        return "product/form";
    }

    // 상품 수정 처리
    @PostMapping("/{no}")
    public String update(@PathVariable Long no, Products product) throws Exception {
        product.setNo(no);
        productService.update(product);
        return "redirect:/products";
    }

    // 상품 삭제
    @PostMapping("/{no}/delete")
    public String delete(@PathVariable Long no) throws Exception {
        productService.delete(no);
        return "redirect:/products";
    }

    // 🔍 상품 검색 (통합 검색)
    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) throws Exception {
        List<Products> products = productService.searchProductsAll(keyword);
        model.addAttribute("products", products);
        return "product/list";
    }

    // 🔍 상품 검색 + 분류 필터
    @GetMapping("/filter")
    public String filter(@RequestParam("cNo") long cNo,
                         @RequestParam("keyword") String keyword,
                         Model model)
        throws Exception {
        List<Products> products = productService.searchProducts(cNo, keyword);
        model.addAttribute("products", products);
        return "product/list";
    }
}

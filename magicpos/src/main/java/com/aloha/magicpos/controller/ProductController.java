package com.aloha.magicpos.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aloha.magicpos.domain.Categories;
import com.aloha.magicpos.domain.Products;
import com.aloha.magicpos.service.CategoryService;
import com.aloha.magicpos.service.ProductService;

import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ServletContext servletContext;


    // 전체 상품 목록
    @GetMapping("/productlist")
    public String list(Model model) throws Exception{
        List<Products> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/list";
    }

    // 전체 상품 목록(관리자용)
    @GetMapping("/admin/productlist")
    public String productlist(Model model) throws Exception{
        List<Products> products = productService.findAll();
        List<Categories> categories = categoryService.findAll();

        // 오늘 판매량 Map<p_no, quantity>
        Map<Long, Long> todaySalesMap = productService.findTodaySalesMap();
        // 상품에 금일 판매량 주입
        for (Products product : products) {
            Long sales = todaySalesMap.getOrDefault(product.getNo(), 0L);
            product.setTodaySales(sales);
        }

        // List<Categories>를 MCategories 객체들을 카테고리번호(no)를 키, 카테고리이름(cName)을 값으로 해서 
        // Map<번호, 이름> 형태로 변환
        Map<Long, String> categoryMap = categories.stream()
                    .collect(Collectors.toMap(Categories:: getNo, Categories::getCName));

        model.addAttribute("products", products);
        model.addAttribute("categoryMap", categoryMap);
        return "pages/admin/admin_product_list";
    }

    // 상품 등록 폼(사용 안함)
    @GetMapping("/new")
    public String form(Model model) throws Exception{
        model.addAttribute("product", new Products());
        model.addAttribute("categories", categoryService.findAll());
        return "product/form";
    }

    // 상품 등록 처리
    @PostMapping("/admin/create")
    @ResponseBody
    public String insert(@ModelAttribute Products product) throws Exception{
         // 이미지 저장 처리
        MultipartFile file = product.getImageFile();
        String savedPath = null;
        if (file != null && !file.isEmpty()) {
            // 저장 경로 설정 + 저장
            String uploadDir = "/upload/images/products/";
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID() + ext;

            File dir = new File(servletContext.getRealPath(uploadDir));
            if (!dir.exists()) dir.mkdirs();

            File saveFile = new File(dir, fileName);
            file.transferTo(saveFile);

            savedPath = uploadDir + fileName;
            product.setImgPath(savedPath); // DB에 저장할 이미지 경로
        }

        // 재고 기본값
        product.setStock(0L);

        // 서비스에 저장
        productService.insert(product);
        return "ok";
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

package com.aloha.magicpos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.magicpos.domain.Categories;
import com.aloha.magicpos.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 카테고리 등록
    @PostMapping
    @ResponseBody
    public String create(@RequestBody Categories category) throws Exception{
        categoryService.create(category);
        return "created";
    }

    // 카테고리 수정
    @PutMapping("/{no}")
    @ResponseBody
    public String update(@PathVariable Long no, @RequestBody Categories category)  throws Exception{
        categoryService.update(no, category);
        return "updated";
    }

    // 카테고리 삭제
    @DeleteMapping("/{no}")
    @ResponseBody
    public String delete(@PathVariable Long no)  throws Exception{
        categoryService.delete(no);
        return "deleted";
    }

    // 전체 조회
    @GetMapping
    @ResponseBody
    public List<Categories> findAll()  throws Exception{
        return categoryService.findAll();
    }

    // 단일 조회
    @GetMapping("/{no}")
    @ResponseBody
    public Categories findByNo(@PathVariable Long no)  throws Exception{
        return categoryService.findByNo(no);
    }
}

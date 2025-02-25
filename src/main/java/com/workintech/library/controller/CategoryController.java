package com.workintech.library.controller;

import com.workintech.library.entity.Category;
import com.workintech.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public List<Category> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable int id){
        return categoryService.findById(id);
    }

    @PostMapping("/")
    public Category save(@RequestBody Category category){
        return categoryService.save(category);
    }

    @PutMapping("/{id}")
    public Category update(@RequestBody Category category, @PathVariable int id){
        Category category1 = categoryService.findById(id);
        if(category1 != null){
            category.setId(id);
            return categoryService.save(category);
        }
        return null;
    }
    @DeleteMapping("/{id}")
    public Category delete(@PathVariable int id){
        Category category1 = categoryService.findById(id);
        categoryService.delete(category1);
        return category1;
}
}


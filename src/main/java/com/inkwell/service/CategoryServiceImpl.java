package com.inkwell.service;

import com.inkwell.entity.Category;
import com.inkwell.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public Category createCategory(Category category) {
        return categoryRepo.save(category);

    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElseThrow(()->new RuntimeException("category not found"));
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepo.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        return categoryRepo.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {

        Category existingCategory = categoryRepo.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));

        categoryRepo.delete(existingCategory);

    }
}

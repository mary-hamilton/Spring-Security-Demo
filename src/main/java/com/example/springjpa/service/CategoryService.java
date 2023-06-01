package com.example.springjpa.service;

import com.example.springjpa.domain.Category;
import com.example.springjpa.domain.dto.CategoryDto;
import com.example.springjpa.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(Category::dtoWithBooks)
                .collect(Collectors.toList());
    }

    public CategoryDto create(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getTitle());
        return categoryRepository.save(category).dto();
    }
}

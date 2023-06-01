package com.example.springjpa.domain;

import com.example.springjpa.domain.dto.CategoryDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    @OneToMany(mappedBy = "category")
    private List<Book> books;

    public Category(String title) {
        this.title = title;
    }

    public Category() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public CategoryDto dto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(this.id);
        categoryDto.setTitle(this.title);
        return categoryDto;
    }

    public CategoryDto dtoWithBooks() {
        CategoryDto categoryDto = dto();
        if (this.books == null || this.books.isEmpty()) {
            categoryDto.setBooks(new ArrayList<>());
        } else {
            categoryDto.setBooks(this.books
                    .stream()
                    .map(Book::dto)
                    .collect(Collectors.toList())
            );
        }
        return categoryDto;
    }
}

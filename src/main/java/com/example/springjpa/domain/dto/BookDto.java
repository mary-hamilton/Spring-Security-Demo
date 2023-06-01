package com.example.springjpa.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BookDto {

    private Integer id;

    @NotEmpty(message = "Book needs a title")
    private String title;

    @NotEmpty(message = "Book needs a synopsis")
    private String synopsis;

    @NotNull(message = "Book needs a category ID")
    private Integer categoryId;

    private String categoryTitle;

    private List<String> borrowedBy = new ArrayList<>();

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

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public List<String> getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(List<String> borrowedBy) {
        this.borrowedBy = borrowedBy;
    }
}

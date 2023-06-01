package com.example.springjpa.domain.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class MemberDto {

    private Integer id;

    @NotEmpty(message = "Member needs a username")
    private String username;

    @NotEmpty(message = "Member needs a password")
    private String password;

    private List<BookDto> borrowedBooks = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BookDto> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BookDto> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}

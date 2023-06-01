package com.example.springjpa.domain;

import com.example.springjpa.domain.dto.MemberDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Member {

    public static final String ADMIN_ROLE = "ROLE_ADMIN";
    public static final String USER_ROLE = "ROLE_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String roles;

    @ManyToMany
    @JoinTable(
            name = "lendings",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> borrowedBooks = new ArrayList<>();

    public Member(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Member(String username) {
        this.username = username;
    }

    public Member() {

    }

    public Integer getId() {
        return id;
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

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public MemberDto dto() {
        MemberDto dto = new MemberDto();
        dto.setUsername(this.username);
        dto.setId(this.id);
        return dto;
    }

    public MemberDto dtoWithBorrowedBooks() {
        MemberDto dto = this.dto();
        if (this.borrowedBooks != null && !this.borrowedBooks.isEmpty()) {
            dto.setBorrowedBooks(this.borrowedBooks
                    .stream()
                    .map(Book::dto)
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }
}

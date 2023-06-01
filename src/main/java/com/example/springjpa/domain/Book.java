package com.example.springjpa.domain;

import com.example.springjpa.domain.dto.BookDto;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private String synopsis;

    @ManyToOne
    private Category category;

    @ManyToMany(mappedBy = "borrowedBooks")
    private List<Member> borrowedBy;

    public Book(String title, String synopsis, Category category) {
        this.title = title;
        this.synopsis = synopsis;
        this.category = category;
    }

    public Book() {
    }

    public List<Member> getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(List<Member> borrowedBy) {
        this.borrowedBy = borrowedBy;
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

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BookDto dto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(this.id);
        bookDto.setTitle(this.title);
        bookDto.setSynopsis(this.synopsis);
        bookDto.setCategoryTitle(this.category.getTitle());
        bookDto.setCategoryId(this.category.getId());
        return bookDto;
    }

    public BookDto dtoWithBorrowingMembers() {
        BookDto dto = dto();
        if (this.borrowedBy != null && !this.borrowedBy.isEmpty()) {
            dto.setBorrowedBy(this.borrowedBy
                    .stream()
                    .map(Member::getUsername)
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }
}

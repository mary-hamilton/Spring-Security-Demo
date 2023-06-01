package com.example.springjpa.service;

import com.example.springjpa.domain.Book;
import com.example.springjpa.domain.Category;
import com.example.springjpa.domain.Member;
import com.example.springjpa.domain.dto.BookDto;
import com.example.springjpa.repository.BookRepository;
import com.example.springjpa.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final MemberService memberService;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository, MemberService memberService) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.memberService = memberService;
    }

    public BookDto create(BookDto bookDto) {
        Category category = categoryRepository
                .findById(bookDto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find category"));
        Book bookToSave = new Book(bookDto.getTitle(), bookDto.getSynopsis(), category);
        return bookRepository.save(bookToSave).dto();
    }

    public List<BookDto> getAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(Book::dtoWithBorrowingMembers)
                .collect(Collectors.toList());
    }

    public BookDto borrow(String username, Integer bookId) {
        Member borrowingMember = memberService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member does not exist"));
        Book bookToBorrow = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book does not exist"));
        borrowingMember.getBorrowedBooks().add(bookToBorrow);
        memberService.save(borrowingMember);
        return bookToBorrow.dtoWithBorrowingMembers();
    }

}

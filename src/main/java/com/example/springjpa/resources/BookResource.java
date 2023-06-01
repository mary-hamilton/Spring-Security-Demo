package com.example.springjpa.resources;

import com.example.springjpa.domain.dto.BookDto;
import com.example.springjpa.service.BookService;
import com.example.springjpa.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/books")
public class BookResource {

    private final BookService bookService;

    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDto> saveBook(@Valid @RequestBody BookDto bookDto) {
        BookDto createdBook = bookService.create(bookDto);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PostMapping("/{bookId}/borrow")
    public ResponseEntity<BookDto> borrowBook(JwtAuthenticationToken principal, @PathVariable Integer bookId) {
        BookDto borrowedBook = bookService.borrow(principal.getName(), bookId);
        return new ResponseEntity<>(borrowedBook, HttpStatus.OK);
    }
}

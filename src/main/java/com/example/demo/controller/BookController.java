package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Validated 
public class BookController {

    private final BookService bookService;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        logger.info("Fetching book with ID: {}", id);
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        logger.info("Creating book: {}", book);
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        logger.info("Updating book with ID: {}", id);
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        logger.info("Deleting book with ID: {}", id);
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/by-name")
    public ResponseEntity<List<Book>> searchByName(@RequestParam String name) {
        logger.info("Searching books by name: {}", name);
        return ResponseEntity.ok(bookService.searchByName(name));
    }

    @GetMapping("/search/by-author")
    public ResponseEntity<List<Book>> searchByAuthor(@RequestParam String author) {
        logger.info("Searching books by author: {}", author);
        return ResponseEntity.ok(bookService.searchByAuthor(author));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        logger.info("Fetching all books");
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}

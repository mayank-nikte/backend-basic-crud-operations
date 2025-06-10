package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookRepository bookRepo;

    public BookController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        logger.info("Fetching book with ID: {}", id);
        return bookRepo.findById(id)
                .map(book -> {
                    logger.info("Book found: {}", book);
                    return ResponseEntity.ok(book);
                })
                .orElseGet(() -> {
                    logger.warn("Book with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book updatedBook) {
        logger.info("Updating book with ID: {}", id);
        return bookRepo.findById(id).map(book -> {
            book.setName(updatedBook.getName());
            book.setAuthor(updatedBook.getAuthor());
            book.setPrice(updatedBook.getPrice());
            Book savedBook = bookRepo.save(book);
            logger.info("Book updated successfully: {}", savedBook);
            return ResponseEntity.ok(savedBook);
        }).orElseGet(() -> {
            logger.warn("Book with ID {} not found for update", id);
            return ResponseEntity.notFound().build();
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        logger.info("Deleting book with ID: {}", id);
        return bookRepo.findById(id).map(book -> {
            bookRepo.delete(book);
            logger.info("Book with ID {} deleted", id);
            return ResponseEntity.ok().<Void>build();
        }).orElseGet(() -> {
            logger.warn("Book with ID {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        });
    }
}



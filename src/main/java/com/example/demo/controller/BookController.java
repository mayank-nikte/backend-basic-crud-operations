package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Validated 
public class BookController {
	
	@Value("${app.message.check}")
    private String welcomeMsg;


    private final BookService bookService;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping("/")
    public String home() {
    	logger.info(welcomeMsg);
        return welcomeMsg;
    }
    
    //http://localhost:8080/books/557
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        logger.info("Fetching book with ID: {}", id);
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<List<Book>> createBooks(@Valid @RequestBody List<Book> books) {
        logger.info("Creating books: {}", books);
        return ResponseEntity.ok(bookService.createBooks(books));
    }
    
    //http://localhost:8080/books/557, Give the update value with the whole book object.
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
    
    // http://localhost:8080/books/search/by-name?name=<name_of_book>
    @GetMapping("/search/by-name")
    public ResponseEntity<List<Book>> searchByName(@RequestParam String name) {
        logger.info("Searching books by name: {}", name);
        return ResponseEntity.ok(bookService.searchByName(name));
    }
    
    // http://localhost:8080/books/search/by-author?author=<name_of_author>
    @GetMapping("/search/by-author")
    public ResponseEntity<List<Book>> searchByAuthor(@RequestParam String author) {
        logger.info("Searching books by author: {}", author);
        return ResponseEntity.ok(bookService.searchByAuthor(author));
    }

    // /books?page=<page_number>&size=<number_of_records_per_page>&sortBy=<sort_by>
    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Book> booksPage = bookService.getAllBooks(pageable);
        logger.info("Fetching all books from page " + page  );
        return ResponseEntity.ok(booksPage);
    }

    
    
}
	
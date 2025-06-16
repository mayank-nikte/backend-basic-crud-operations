package com.example.demo.service;

import com.example.demo.exception.BookNotFoundException;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Book getBookById(Long id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book createBook(Book book) {
        return bookRepo.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        return bookRepo.findById(id)
                .map(book -> {
                    book.setName(updatedBook.getName());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setPrice(updatedBook.getPrice());
                    return bookRepo.save(book);
                })
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public void deleteBook(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookRepo.delete(book);
    }

    public List<Book> searchByName(String name) {
        return bookRepo.findByNameContainingIgnoreCase(name);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepo.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }
}

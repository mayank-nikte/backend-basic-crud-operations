package com.example.demo.service;

import com.example.demo.exception.BookNotFoundException;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;

    public BookService(BookRepository bookRepo, AuthorRepository authorRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    public Book getBookById(Long id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public List<Book> createBooks(List<Book> books) {
        List<Book> processedBooks = books.stream().map(book -> {
            Author resolvedAuthor = resolveAuthor(book.getAuthor());
            book.setAuthor(resolvedAuthor);
            return book;
        }).collect(Collectors.toList());

        return bookRepo.saveAll(processedBooks);
    }


    public Book updateBook(Long id, Book updatedBook) {
        return bookRepo.findById(id)
                .map(book -> {
                    book.setName(updatedBook.getName());
                    Author resolvedAuthor = resolveAuthor(updatedBook.getAuthor());
                    book.setAuthor(resolvedAuthor);
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
        return bookRepo.findByAuthor_NameContainingIgnoreCase(author);
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }
    
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepo.findAll(pageable);
    }
    
    private Author resolveAuthor(Author inputAuthor) {
        if (inputAuthor == null || inputAuthor.getName() == null) {
            throw new IllegalArgumentException("Author name must not be null");
        }

        return authorRepo.findByNameIgnoreCase(inputAuthor.getName())
                .orElseGet(() -> authorRepo.save(new Author(inputAuthor.getName())));
    }
}

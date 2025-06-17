package com.example.demo.service;

import com.example.demo.exception.BookNotFoundException;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Book createBook(Book book) {
        Author resolvedAuthor = resolveAuthor(book.getAuthor());
        book.setAuthor(resolvedAuthor);
        return bookRepo.save(book);
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
    
    private Author resolveAuthor(Author inputAuthor) {
        if (inputAuthor == null || inputAuthor.getName() == null) {
            throw new IllegalArgumentException("Author name must not be null");
        }

        return authorRepo.findByNameIgnoreCase(inputAuthor.getName())
                .orElseGet(() -> authorRepo.save(new Author(inputAuthor.getName())));
    }
}

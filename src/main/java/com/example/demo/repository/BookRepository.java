package com.example.demo.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	public List<Book> findByNameContainingIgnoreCase(String name);
	public List<Book> findByAuthor_NameContainingIgnoreCase(String author);
	
	
}
package com.example.demo.repository;

import com.example.demo.entity.Author;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	
	Optional<Author> findByNameIgnoreCase(String name);

}


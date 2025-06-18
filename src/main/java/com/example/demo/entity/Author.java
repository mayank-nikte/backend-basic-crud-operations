package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @NotBlank(message="Author name should be present")
    private String name;

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + "]";
	}

	public Author(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Author() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Author(String name) {
        this.name = name;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

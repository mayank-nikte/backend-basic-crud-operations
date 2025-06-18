package com.example.demo.entity;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Entity
public class Book {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank(message="Book name should not be empty")
    private String name;
	
	@NotNull(message="Price can not be empty")
	@Positive(message="Price must be Positive")
    private Double price;
    
	
	@Valid
	@NotNull(message="Author details are required")
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "author_id")
    private Author author;

    @Override
   	public String toString() {
   		return "Book [id=" + id + ", name=" + name + ", author=" + author + ", price=" + price + "]";
   	}
    
    public Book(Long id, String name, Author author, Double price) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.price = price;
	}
    
	public Book() {
		super();
		// TODO Auto-generated constructor stub
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

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}

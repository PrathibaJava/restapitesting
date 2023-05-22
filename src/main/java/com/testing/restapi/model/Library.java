package com.testing.restapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Library {

	private String book_name;
	@Id
	private String id;
	private String isbn;
	private int aisle;
	private String author;
	
	public Library() {
		
	}
	
	public Library(String book_name, String id, String isbn, int aisle, String author) {
		super();
		this.book_name = book_name;
		this.id = id;
		this.isbn = isbn;
		this.aisle = aisle;
		this.author = author;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getAisle() {
		return aisle;
	}

	public void setAisle(int aisle) {
		this.aisle = aisle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}

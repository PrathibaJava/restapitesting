package com.testing.restapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.testing.restapi.controller.LibraryController;
import com.testing.restapi.model.AddBookResponse;
import com.testing.restapi.model.Library;
import com.testing.restapi.repository.LibraryRepository;
import com.testing.restapi.service.LibraryService;

@SpringBootTest
class RestapiApplicationTests {
	
	@MockBean
	LibraryService libraryService;
	
	@MockBean
	LibraryRepository repository;
	
	@Autowired
	LibraryController controller;

	@Test
	void contextLoads() {
	}

	@Test
	public void buildIDTest() {
		LibraryService service=new LibraryService();
		String id=service.buildId("ZMAN", 24);
		assertEquals("OLDZMAN24", id);
		String id1=service.buildId("MAN", 24);
		assertEquals("MAN24", id1);
	}
	
	@Test
	public void addBookTest() {
		Library lib=buildLibrary();
		when(libraryService.buildId(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());
		when(libraryService.checkIfBookExists(lib.getId())).thenReturn(false);
		ResponseEntity<AddBookResponse> response=controller.addBook(lib);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		AddBookResponse ad=response.getBody();
		assertEquals(lib.getId(),ad.getId());
		assertEquals("Book is added successfully",ad.getMessage());
		
	}
	
	public Library buildLibrary() {
		Library lib = new Library();
		lib.setAisle(1234);
		lib.setAuthor("Rahul");
		lib.setBook_name("RESTApi");
		lib.setIsbn("ASDFG");
		lib.setId("ASDFG1234");
		return lib;
	}
}

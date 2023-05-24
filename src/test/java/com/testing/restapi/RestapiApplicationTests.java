package com.testing.restapi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.restapi.controller.LibraryController;
import com.testing.restapi.model.AddBookResponse;
import com.testing.restapi.model.Library;
import com.testing.restapi.repository.LibraryRepository;
import com.testing.restapi.service.LibraryService;

@SpringBootTest
@AutoConfigureMockMvc
class RestapiApplicationTests {
	
	@MockBean
	LibraryService libraryService;
	
	@MockBean
	LibraryRepository repository;
	
	@Autowired
	LibraryController controller;
	
	@Autowired
	MockMvc mockMvc;

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
	
	@Test
	public void addBookControllerTest() throws Exception {
		Library lib=buildLibrary();
		ObjectMapper mapper=new ObjectMapper();
		String jsonString=mapper.writeValueAsString(lib);
		
		when(libraryService.buildId(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());
		when(libraryService.checkIfBookExists(lib.getId())).thenReturn(false);
		when(repository.save(any())).thenReturn(lib);
		
		mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)).andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(lib.getId()));
	}
	
	@Test
	public void updateBookTest() throws Exception {
		Library lib=buildLibrary();
		ObjectMapper mapper=new ObjectMapper();
		String jsonString=mapper.writeValueAsString(updateLibrary());
		when(libraryService.findBookById(any())).thenReturn(lib);
		//when(repository.save(any())).thenReturn();
		mockMvc.perform(put("/books/"+lib.getId()).contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andDo(print())		
				.andExpect(status().isOk())
				.andExpect(content().json("{\"book_name\":\"Changed Book\",\"id\":\"ASDFG1234\",\"isbn\":\"ASDFG\",\"aisle\":123456789,\"author\":\"Shetty\"}"));
	}
	
	@Test
	public void deleteBookTest() throws Exception {
		Library lib=buildLibrary();
		when(libraryService.findBookById(any())).thenReturn(lib);
		doNothing().when(repository).delete(lib);
		mockMvc.perform(delete("/books").contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"hello202314\"}"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("Book is deleted"));
	}
	
	@Test
	public void getBookByAuthorTest() throws Exception {
		List<Library> newLib=new ArrayList<>();
		newLib.add(buildLibrary());
		newLib.add(buildLibrary());
		when(repository.findBooksByAuthor(any())).thenReturn(newLib);	
		mockMvc.perform(get("/books/author").param("authorname", "Rahul")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").isNumber())
		.andExpect(jsonPath("$.[0].id").value("ASDFG1234"));
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
	
	public Library updateLibrary() {
		Library lib = new Library();
		lib.setAisle(123456789);
		lib.setAuthor("Shetty");
		lib.setBook_name("Changed Book");
		lib.setIsbn("ASDFG");
		lib.setId("ASDFG1234");
		return lib;
	}
}

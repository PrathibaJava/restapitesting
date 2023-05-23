package com.testing.restapi.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.testing.restapi.model.AddBookResponse;
import com.testing.restapi.model.Library;
import com.testing.restapi.repository.LibraryRepository;
import com.testing.restapi.service.LibraryService;

import ch.qos.logback.classic.Logger;

@RestController
public class LibraryController {

	@Autowired
	AddBookResponse ad;

	@Autowired
	LibraryRepository repository;

	@Autowired
	LibraryService libraryService;

	private static final Logger logger = (Logger) LoggerFactory.getLogger(LibraryController.class);

	@PostMapping("/books")
	public ResponseEntity<AddBookResponse> addBook(@RequestBody Library library) {
		String id = libraryService.buildId(library.getIsbn(), library.getAisle());
		if (!libraryService.checkIfBookExists(id)) {
			library.setId(id);
			repository.save(library);
			ad.setMessage("Book is added successfully");
			logger.info("Book is added successfully");
			ad.setId(id);
			return new ResponseEntity<AddBookResponse>(ad, HttpStatus.CREATED);
		} else {
			ad.setId(id);
			ad.setMessage("Book already exists");
			return new ResponseEntity<AddBookResponse>(ad, HttpStatus.ACCEPTED);
		}
	}

	@GetMapping("/books/{id}")
	public Library getBookById(@PathVariable(value = "id") String id) {
		try {
			Library lib = repository.findById(id).get();
			return lib;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/books/author")
	public List<Library> getBookByAuthor(@RequestParam(value = "authorname") String author) {
		return repository.findBooksByAuthor(author);
	}

	@PutMapping("/books/{id}")
	public ResponseEntity<Library> updateBook(@RequestBody Library library, @PathVariable(value = "id") String id) {

		Library existingBook = repository.findById(id).get();
		existingBook.setAisle(library.getAisle());
		existingBook.setAuthor(library.getAuthor());
		existingBook.setBook_name(library.getBook_name());
		repository.save(existingBook);
		return new ResponseEntity<Library>(existingBook, HttpStatus.OK);
	}

	@DeleteMapping("/books")
	public ResponseEntity<String> deleteBookById(@RequestBody Library library) {
		Library toBeDeleted = repository.findById(library.getId()).get();
		repository.delete(toBeDeleted);
		return new ResponseEntity<>("Book is deleted", HttpStatus.OK);
	}
}

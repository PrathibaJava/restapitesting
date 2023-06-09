package com.testing.restapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testing.restapi.model.Library;
import com.testing.restapi.repository.LibraryRepository;

@Service
public class LibraryService {

	@Autowired
	LibraryRepository repository;
	
	public String buildId(String isbn, int aisle) {
		if(isbn.startsWith("Z")) {
			return "OLD"+isbn+aisle;
		}
		return isbn+aisle;
	}
	
	public boolean checkIfBookExists(String id) {
		 Optional<Library> exists = repository.findById(id);
		 if(exists.isPresent())
			 return true;
		 return false;
	}
	
	public Library findBookById(String id) {
		return repository.findById(id).get();
	}
}

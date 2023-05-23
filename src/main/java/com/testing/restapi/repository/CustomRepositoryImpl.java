package com.testing.restapi.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.testing.restapi.model.Library;

public class CustomRepositoryImpl implements CustomRepository {

	@Lazy
	@Autowired
	LibraryRepository repository;

	@Override
	public List<Library> findBooksByAuthor(String authorname) {

		List<Library> booksList = new ArrayList<Library>();
		List<Library> books = repository.findAll();
		for (Library l1 : books) {
			if (l1.getAuthor().equals(authorname)) {
				booksList.add(l1);
			}
		}

		return booksList;
	}

}

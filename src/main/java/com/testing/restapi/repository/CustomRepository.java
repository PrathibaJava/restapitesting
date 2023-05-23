package com.testing.restapi.repository;

import java.util.List;

import com.testing.restapi.model.Library;

public interface CustomRepository {
	
	public List<Library> findBooksByAuthor(String authorname);

}

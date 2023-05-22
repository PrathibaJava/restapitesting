package com.testing.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testing.restapi.model.Library;

public interface LibraryRepository extends JpaRepository<Library, String> {

}

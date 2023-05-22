package com.testing.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.testing.restapi.model.Library;
import com.testing.restapi.repository.LibraryRepository;

@SpringBootApplication
public class RestapiApplication implements CommandLineRunner {

	@Autowired
	LibraryRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}

	@Override
	public void run(String[] args) {
		Library lib=repository.findById("fdsefr343").get();
		System.out.println(lib.getAuthor());
		Library en=new Library("Devops", "lkhs123", "lkhs", 123, "Rahul");
		/* repository.save(en); */
		List<Library> findAll = repository.findAll();
		for(Library list1:findAll) {
			System.out.println(list1.getAuthor());
		}
		repository.delete(en);
	}
}

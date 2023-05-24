package com.testing.restapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.testing.restapi.model.Library;

/*To run integration tests from command prompt, we need to have failsafe plugin in our pom.xml file
mvn failsafe:integration-test -> to run integration tests only
mvn integration-test -> To run both integration tests and unit tests
mvn test -> to run unit tests only, but I tried it ran integration tests also */

@SpringBootTest
public class IntegrationTests {

	@Test
	public void getBookByAuthorIntegrationTest() throws JSONException {
		String expectedOutput="[\r\n"
				+ "    {\r\n"
				+ "        \"book_name\": \"Appium\",\r\n"
				+ "        \"id\": \"fdsefr343\",\r\n"
				+ "        \"isbn\": \"fdsefr3\",\r\n"
				+ "        \"aisle\": 43,\r\n"
				+ "        \"author\": \"Rahul\"\r\n"
				+ "    }\r\n"
				+ "]";
		TestRestTemplate template=new TestRestTemplate();
		ResponseEntity<String> response=template.getForEntity("http://localhost:8080/books/author?authorname=Rahul", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		JSONAssert.assertEquals(expectedOutput, response.getBody(), false);
	}
	
	@Test
	public void addBookIntegrationTest() {
		TestRestTemplate template=new TestRestTemplate();
		
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Library> request=new HttpEntity<Library>(buildLibrary(), headers);
		ResponseEntity<String> response=template.postForEntity("http://localhost:8080/books", request, String.class);
		System.out.println(response.getStatusCode());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
	}

	private Library buildLibrary() {
		Library lib = new Library();
		lib.setAisle(1234);
		lib.setAuthor("ShettyRahul");
		lib.setBook_name("RESTApi");
		lib.setIsbn("ASDFG");
		lib.setId("ASDFG1234");
		return lib;
	}
}

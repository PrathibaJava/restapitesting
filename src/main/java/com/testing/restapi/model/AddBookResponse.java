package com.testing.restapi.model;

import org.springframework.stereotype.Component;

@Component
public class AddBookResponse {

	private String message;
	private String id;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

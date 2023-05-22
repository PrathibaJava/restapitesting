package com.testing.restapi.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testing.restapi.model.Greeting;

@RestController
public class GreetingController {
	
	AtomicLong counter=new AtomicLong();
	
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name") String name) {
		return new Greeting(counter.incrementAndGet(),"Welcome to the course " + name);
	}
}

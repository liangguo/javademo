package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/ping")
	public String hi() {
		return "pong";
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		String imgserver = System.getProperty("imgserver","http://127.0.0.1");
		return "<html>"
			+ "<body>"
			+ String.format("<h1>Hello, %s</h1>",name)
			+ String.format(" <img src=\"%s/cat.jpg\" width=\"104\" height=\"142\">", imgserver)
			+ "</body>"
			+ "</html>";
	}

}





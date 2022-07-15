package com.fiserv.wiki;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fiserv.wiki.exception.WikiException;
import com.fiserv.wiki.service.WikiService;

@SpringBootApplication
public class WikiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WikiApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(WikiService wikiService) {
		return args -> {
			int result;
			var scanner = new Scanner(System.in);
			System.out.println("Enter the topic");
			var topic = scanner.next();

			try {
				result = wikiService.countOccurrences(topic);
			} catch (WikiException e) {
				System.out.println("Error: " + e.getMessage());
				return;
			}
			System.out.println(result);
		};
	}
}

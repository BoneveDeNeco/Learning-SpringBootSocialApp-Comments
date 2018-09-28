package com.lucas.learningspringboot.SpringBootSocialApp.comments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;

@SpringCloudApplication
public class SpringBootSocialAppCommentsMicroservice {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSocialAppCommentsMicroservice.class, args);
	}
}

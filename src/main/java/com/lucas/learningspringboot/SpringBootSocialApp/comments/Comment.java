package com.lucas.learningspringboot.SpringBootSocialApp.comments;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Comment {
	@Id private String id;
	private String imageId;
	private String comment;
}

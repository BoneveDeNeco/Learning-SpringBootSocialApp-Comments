package com.lucas.learningspringboot.SpringBootSocialApp.comments;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
@EnableBinding(CustomProcessor.class)
public class CommentMessageStreamEndpoint {
	
	private CommentService commentService;
	
	public CommentMessageStreamEndpoint(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@StreamListener
	@Output(CustomProcessor.OUTPUT)
	public Flux<Comment> save(@Input(CustomProcessor.INPUT) Flux<Comment> newComments) {
		return commentService.save(newComments);
	}
}

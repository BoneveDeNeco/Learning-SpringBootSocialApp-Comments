package com.lucas.learningspringboot.SpringBootSocialApp.comments;

import org.springframework.stereotype.Service;

import com.esotericsoftware.minlog.Log;

import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Flux;

@Service
public class CommentService {
	
	CommentRepository repository;
	MeterRegistry meterRegistry;
	
	public CommentService(CommentRepository repository, MeterRegistry meterRegistry) {
		this.repository = repository;
		this.meterRegistry = meterRegistry;
	}
	
	public Flux<Comment> save(Flux<Comment> comments) {
		return repository.saveAll(comments)
		.log("commentService-save")
		.map(savedComment -> {
			Log.info("Saving comment " + savedComment);
			meterRegistry.counter("comments.consumed", "imageId", savedComment.getImageId())
				.increment();
			return savedComment;
		});
	}
}

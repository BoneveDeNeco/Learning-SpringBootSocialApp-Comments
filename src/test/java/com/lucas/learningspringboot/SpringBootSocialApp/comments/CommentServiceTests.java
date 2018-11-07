package com.lucas.learningspringboot.SpringBootSocialApp.comments;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CommentServiceTests {
	private static final Comment A_COMMENT = new Comment("1", "Image1", "A comment");
	private static final Flux<Comment> A_COMMENT_FLUX = Flux.just(A_COMMENT);
	
	Flux<Comment> fluxComment;
	CommentRepository repository;
	CommentService service;
	MeterRegistry meterRegistry;
	@Before
	public void setup() {
		fluxComment = Flux.just(A_COMMENT);
		repository = mock(CommentRepository.class);
		when(repository.saveAll(any())).thenReturn(fluxComment);
		
		meterRegistry = new SimpleMeterRegistry();
		service = new CommentService(repository, meterRegistry);
	}
	
	@Test
	public void savesCommentsToCommentRepository() {
		service.save(A_COMMENT_FLUX);
		
		verify(repository).saveAll(A_COMMENT_FLUX);
	}
	
	@Test
	public void keepsTrackOfNumberOfCommentsConsumed() {
		service.save(A_COMMENT_FLUX).blockFirst();
		
		assertThat(meterRegistry.counter("comments.consumed", "imageId", A_COMMENT.getImageId()).count())
			.isEqualTo(1.0);
		//meterRegistry.close();
	}
	
	@Test
	public void returnsFluxOfComments() {
		Flux<Comment> commentFlux = service.save(A_COMMENT_FLUX);
		
		assertThat(commentFlux.blockFirst()).isEqualTo(A_COMMENT);
	}
}

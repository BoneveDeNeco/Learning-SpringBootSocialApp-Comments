package com.lucas.learningspringboot.SpringBootSocialApp.comments;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = {CommentController.class})
public class CommentControllerTests {
	
	private static final String IMAGE_ID = "1";
	private static final Comment A_COMMENT = new Comment(IMAGE_ID, "2", "A comment");
	
	@Autowired
	WebTestClient webTestClient;
	
	@MockBean
	CommentRepository repository;

	@Test
	public void fetchesCommentsForSingleImageFromRepository() {
		Mockito.when(repository.findByImageId(IMAGE_ID)).thenReturn(Flux.just(A_COMMENT));

		webTestClient.get().uri("/comments/" + IMAGE_ID).exchange()
			.expectBody(Comment[].class).isEqualTo(new Comment[] {A_COMMENT});
	}
}

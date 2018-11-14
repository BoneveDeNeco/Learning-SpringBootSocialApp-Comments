package com.lucas.learningspringboot.SpringBootSocialApp.comments;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.BlockingQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CommentMessageStreamEndpointTests {
	
	@Autowired
	CustomProcessor channels;
	
	@Autowired
	MessageCollector collector;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	private CompositeMessageConverter converter;
	
	Comment comment = new Comment("1", "1", "A comment"); 
	
	@Test
	public void receivesCommentMessagesAndSaveInDatabase() {
		sendComment();
		
		Mockito.verify(commentService).save(Mockito.any(Flux.class));
	}
	
	//@Test //Not sure if this can work
	public void broadcastsSavedComment() {
		sendComment();
		
		BlockingQueue<Message<?>> messages = collector.forChannel(channels.output());
		Message<?> message = messages.poll();
		Comment sentComment = (Comment) converter.fromMessage(message, Comment.class);
		assertThat(sentComment).isEqualTo(comment);
	}

	private void sendComment() {
		SubscribableChannel input = channels.input();
		
		input.send(new GenericMessage<>(comment));
	}
}

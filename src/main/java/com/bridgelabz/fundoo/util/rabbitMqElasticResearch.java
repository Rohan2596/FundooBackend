package com.bridgelabz.fundoo.util;

import java.io.IOException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.elasticSearch.IElasticSearch;
import com.bridgelabz.fundoo.notes.model.Notes;
import com.bridgelabz.fundoo.notes.respository.NotesRespository;


@Component
public class rabbitMqElasticResearch {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Value("${fundoo.rabbitmq.exchange}")
	private String exchange;

	@Value("${elastic.rabbitmq.routingkey}")
	private String routingkey1;
	
	@Autowired
	NotesRespository noteRespository;
	
	  @Autowired
	  IElasticSearch elastic;
 
	
		public void rabitsendelastic(Notes notes) {
		System.out.println(notes);
			rabbitTemplate.convertAndSend(exchange, routingkey1, notes);
			System.out.println("send the messgae ");
			
		}
		
		@RabbitListener(queues = "fundooelastic.queue1")
		public void send(Notes notes) throws IOException {
			System.out.println("elastic");
			System.out.println(notes);
//              Notes  note=null;   			
//			if (notes.equals(note)) {
//			elastic.create(notes);
//			System.out.println("elastic serach ");
//		}else if(noteRespository.findById(notes.getId()).isPresent() && notes.isTrash()==true) {
//			elastic.deleteNote(notes.getId());
//			System.out.println("elastic serach ");}
//		else {
				elastic.updateNote(notes);
			System.out.println("elastic serach ");
//		}

		}
		
		
		
}

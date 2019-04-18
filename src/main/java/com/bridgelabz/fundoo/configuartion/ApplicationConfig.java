package com.bridgelabz.fundoo.configuartion;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.service.MailService;

@Configuration
public class ApplicationConfig {
@Bean
	ModelMapper getModelMapper() {
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

@Bean
public Response getResponse() {
	return new Response();
}
}



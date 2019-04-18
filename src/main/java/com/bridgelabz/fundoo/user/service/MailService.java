package com.bridgelabz.fundoo.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Component;


import com.bridgelabz.fundoo.user.model.Emailid;
import com.bridgelabz.fundoo.util.TokenGenerators;

@Component
public class MailService {
	
private JavaMailSender javaMailSender;

@Autowired
public MailService(JavaMailSender javaMailSender) {
	this.javaMailSender=javaMailSender;}
@Autowired
private TokenGenerators tokenGenerators;
public void send(Emailid emailid) {
	System.out.println("Sending mail to receiver");
	SimpleMailMessage message=new  SimpleMailMessage();
	message.setTo(emailid.getTo());
	message.setSubject(emailid.getSubject());
	message.setText(emailid.getBody());

	javaMailSender.send(message);
	
	System.out.println("email sent successfully");
}
public String getlink(String link,long id) {
	return link+tokenGenerators.generateToken(id);
}
}

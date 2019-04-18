package com.bridgelabz.fundoo.user.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.hibernate.validator.cfg.defs.EmailDef;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.response.Response;

import com.bridgelabz.fundoo.user.dto.UserDto;
import com.bridgelabz.fundoo.user.model.Emailid;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.respository.UserRespository;
import com.bridgelabz.fundoo.util.ResponseStatus;
import com.bridgelabz.fundoo.util.TokenGenerators;

@PropertySource("classpath:message.properties")
@Service
public class UserService implements IUserService {
	@Autowired
	ModelMapper modelmapper;

	@Autowired
	UserRespository userRespository;

	@Autowired
	private Environment environment;
	@Autowired
	private TokenGenerators tokengenerators;

	@Autowired
	private MailService mailService;

	@Override
	public Response registeruser(UserDto userDto) {
		Emailid emailid = new Emailid();
		Response response = null;
		Optional<User> availability = userRespository.findByEmailId(userDto.getEmailId());
		if (availability.isPresent()) {
			System.out.println("user is present");
		} else {

			User user = modelmapper.map(userDto, User.class);
			user.setName(userDto.getName());
			user.setEmailId(userDto.getEmailId());
			user.setPassword(userDto.getPassword());
			user.setPhNumber(userDto.getPhNumber());
			user.setRegisteredDate(LocalDateTime.now());
			user.setVerified(false);
			User status = userRespository.save(user);

			System.out.println(user.getId());
			emailid.setFrom("rohankadam572@gmail.com");
			emailid.setTo(userDto.getEmailId());
			emailid.setSubject("Email verification");
			try {
				emailid.setBody(mailService.getlink("http://192.168.0.189:8080/user/emailvalidation/", status.getId()));
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			}
			mailService.send(emailid);

			if (status == null) {
				System.out.println("Data not Found");
			}
		}
		response = ResponseStatus.statusinfo(environment.getProperty("status.register.success"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;

	}

	@Override
	public Response loginuser(UserDto userDto) {

		Response response = null;
		Optional<User> availability = userRespository.findByEmailId(userDto.getEmailId());
		System.out.println(userDto.getEmailId());
		if (availability.isPresent()) {
			response = ResponseStatus.statusinfo(environment.getProperty("status.login.success"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;

		}
		response = ResponseStatus.statusinfo(environment.getProperty("status.login.nosuccess"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;

	}

	@Override
	public Response validateEmail(String token) throws IllegalArgumentException, UnsupportedEncodingException {

		Response response = null;
		long id = tokengenerators.decodeToken(token);
		System.out.println(id);

//	
		Optional<User> user = userRespository.findById((int) id).map(this::verify);

		if (user.isPresent()) {

			response = ResponseStatus.statusinfo(environment.getProperty("status.register.success"),
					Integer.parseInt(environment.getProperty("status.success.code")));

		} else {
			System.out.println("Validate user");
		}
		return response;

	}

	private User verify(User user) {
		user.setVerified(true);
		user.setModifiedDate(LocalDateTime.now());

		return userRespository.save(user);
	}

	@Override
	public Response forgotpassword(UserDto userDto) {
		Emailid email = new Emailid();
		Response response = null;
		Optional<User> availabilityUser = userRespository.findByEmailId(userDto.getEmailId());
		if (availabilityUser.isPresent()) {
			User user = modelmapper.map(userDto, User.class);
			user.setPassword(userDto.getPassword());
			user.setModifiedDate(LocalDateTime.now());
			User status = userRespository.save(user);
			System.out.println("Password changed");
			email.setFrom("rohankadam572@gmail.com");
			email.setTo(userDto.getEmailId());
			email.setSubject("Forgot Password");
			try {
				email.setBody(mailService.getlink("http://192.168.0.189:8080/user/forgotpassword/", status.getId()));
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			}
			mailService.send(email);

			response = ResponseStatus.statusinfo(environment.getProperty("status.success.fpassword"),
					Integer.parseInt(environment.getProperty("status.success.fpassword.code")));

		} else {
			System.out.println("User is not present");
			response = ResponseStatus.statusinfo(environment.getProperty("status.failure.fpassword"),
					Integer.parseInt(environment.getProperty("status.failure.fpassword.code")));
		}

		return response;
	}

}

package com.bridgelabz.fundoo.user.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDto;
import com.bridgelabz.fundoo.user.dto.UserDto;
import com.bridgelabz.fundoo.user.model.Emailid;
import com.bridgelabz.fundoo.user.model.ForgotPassword;
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
	private UserRespository userRespository;

	@Autowired
	private Environment environment;
	@Autowired
	private TokenGenerators tokengenerators;

	@Autowired
	private MailService mailService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Response registeruser(UserDto userDto, StringBuffer requestUrl) {
		System.out.println(requestUrl);
		Emailid emailid = new Emailid();
		Response response = null;
		Optional<User> availability = userRespository.findByEmailId(userDto.getEmailId());
		if (availability.isPresent()) {
			throw new UserException("User is present");
//			System.out.println("user is present");
		} else {

			User user = modelmapper.map(userDto, User.class);

//			user.setName(userDto.getName());
//			user.setEmailId(userDto.getEmailId());
			String password = passwordEncoder.encode(userDto.getPassword());

			user.setPassword(password);
//			user.setPhNumber(userDto.getPhNumber());
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
	public ResponseToken loginuser(LoginDto loginDto) {

		ResponseToken response = null;
		User user = modelmapper.map(loginDto, User.class);
		Optional<User> availability = userRespository.findByEmailId(loginDto.getEmailId());
		System.out.println(loginDto.getEmailId());
		if (availability.isPresent()) {
			boolean status = passwordEncoder.matches(loginDto.getPassword(), availability.get().getPassword());
			if (status == true) {
				String tokengenerate = tokengenerators.generateToken(availability.get().getId());
				System.out.println(tokengenerate);
				response = ResponseStatus.tokenStatusInfo(environment.getProperty("status.login.success"),
						Integer.parseInt(environment.getProperty("status.success.code")), tokengenerate);
				return response;
			}

		}
		response = ResponseStatus.tokenStatusInfo(environment.getProperty("status.login.nosuccess"),
				Integer.parseInt(environment.getProperty("status.failure.code")), null);
		return response;

	}

	@Override
	public Response validateEmail(String token) throws UserException, UnsupportedEncodingException {

		Response response = null;
		long id = tokengenerators.decodeToken(token);
		System.out.println(id);

//	
		Optional<User> user = userRespository.findById((long) id).map(this::verify);

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
	public Response forgotpassword(LoginDto loginDto) {
		Emailid email = new Emailid();

		System.out.println(loginDto.getEmailId());
		
		Optional<User> user = userRespository.findByEmailId(loginDto.getEmailId());
		System.out.println(user.toString());

		if (user.isPresent()) {

			System.out.println("Password changed");
			email.setFrom("rohankadam572@gmail.com");
			email.setTo(loginDto.getEmailId());
			email.setSubject("Forgot Password");
			try {
				email.setBody(mailService.getlink("http://localhost:4200/user/resetPassword/", user.get().getId()));
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			}
			mailService.send(email);

			Response response = ResponseStatus.statusinfo(environment.getProperty("status.success.fpassword"),
					Integer.parseInt(environment.getProperty("status.success.fpassword.code")));
			return response;

		} else {
			System.out.println("User is not present");
			Response response = ResponseStatus.statusinfo(environment.getProperty("status.failure.fpassword"),
					Integer.parseInt(environment.getProperty("status.failure.fpassword.code")));
			return response;
		}

	}

	@Override
	public Response resetpassword(String token,ForgotPassword forgotPassword) throws UserException, UnsupportedEncodingException {
		Response response = null;
		long id = tokengenerators.decodeToken(token);
		System.out.println(id);

		Optional<User> user = userRespository.findById((long) id);

		if (user.isPresent()) {
		    String password=passwordEncoder.encode(forgotPassword.getConfirmPassword());
			user.get().setPassword(password);
			System.out.println("forgotPassword.getConfirmPassword()");
			
			System.out.println(user.get().getPassword());
		    user.get().setModifiedDate(LocalDateTime.now());
		    System.out.println("UserService.resetpassword()::Date set");
		    userRespository.save(user.get());
//			if(forgotPassword.getConfirmPassword()==forgotPassword.getNewPassword()) {
//			user1.setPassword(forgotPassword.getNewPassword());
//			userRespository.save(user1);
//			System.out.println("UserService.resetpassword()::Object saved");
//			System.out.println(user1.getPassword()+"  "+forgotPassword.getConfirmPassword());
//			response = ResponseStatus.statusinfo(environment.getProperty("status.success.resetpassword"),
//					Integer.parseInt(environment.getProperty("status.success.resetpassword.code")));}
//			else {
				response = ResponseStatus.statusinfo(environment.getProperty("status.success.resetpassword"),
						Integer.parseInt(environment.getProperty("status.success.resetpassword.code")));
				
			
		} else {
			System.out.println("User not present");
			response = ResponseStatus.statusinfo(environment.getProperty("status.failure.resetpassword"),
					Integer.parseInt(environment.getProperty("status.failure.resetpassword.code")));
		}
		return response;
	}

	public User changePassword(Optional<User> user, String Password) {
		user.get().setModifiedDate(LocalDateTime.now());
		user.get().setPassword(Password);
		return userRespository.save(user.get());

	}

	@Override
	public Response changePassword(LoginDto loginDto) {
		Emailid email = new Emailid();
		Response response = null;
		Optional<User> user = userRespository.findByEmailId(loginDto.getEmailId());
		if (user.isPresent()) {
			email.setFrom("rohankadam572@gmail.com");
			email.setTo(loginDto.getEmailId());
			email.setSubject("Changeing Password");
			try {
				email.setBody(mailService.getlink("http://192.168.0.189:8080/user/resetPassword/", user.get().getId()));

			} catch (UserException userException) {
				throw new UserException("invalid mail");
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

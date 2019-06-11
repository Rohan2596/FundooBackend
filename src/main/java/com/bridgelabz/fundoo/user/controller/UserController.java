package com.bridgelabz.fundoo.user.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDto;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.ForgotPassword;
import com.bridgelabz.fundoo.user.service.UserServiceImpl;

/**
 * @author admin1
 *
 */
@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/users")
public class UserController {

	@Autowired
	 UserServiceImpl userService;
	
//	private final static Logger logger = LoggerFactory.getLogger(UserController.class.getName());

	@PostMapping("/registration")
	public ResponseEntity<Response> register(@RequestBody @Valid UserDTO userdto,
			HttpServletRequest httpServletRequest, BindingResult result) {
		
//		logger.info("Request payload : {} ", userdto);
		StringBuffer requestUrl = httpServletRequest.getRequestURL();
		Response response = userService.registeruser(userdto, requestUrl);
//		logger.info("Response payload : {} ", response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseToken> loginuser(@RequestBody @Valid LoginDto loginDto) {
	   ResponseToken response = userService.loginUser(loginDto);
		System.out.println(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/emailvalidation/{token}")
	public ResponseEntity<Response> validateUser(@PathVariable String token)
			throws UserException, UnsupportedEncodingException {
		Response response = userService.validateEmail(token);
		System.out.println(response);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@PostMapping("/forgotPassword")
	public ResponseEntity<Response> forgot(@RequestBody @Valid LoginDto loginDto) {
		System.out.println(loginDto.getEmailId());
		System.out.println("inside forgot");
		Response response = userService.forgotpassword(loginDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/resetPassword/{token}")
	public ResponseEntity<Response> resetpassword(@PathVariable String token,
			@RequestBody ForgotPassword forgotPassword) throws UserException, UnsupportedEncodingException {
		Response response = userService.resetpassword(token, forgotPassword);
		System.out.println("Inside reset");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/changePassword")
	public ResponseEntity<Response> changepassword(@RequestBody @Valid LoginDto loginDto) {
		Response response = userService.changePassword(loginDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/uploadImage")
	public ResponseEntity<Response> uploadImage(@RequestHeader String token, @RequestParam MultipartFile file)
			throws IllegalArgumentException, UnsupportedEncodingException {
		Response response = userService.uploadImage(token, file);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getProfilePic/{token}")
	public ResponseEntity<Resource> getImage(@PathVariable String token)
			throws IllegalArgumentException, UnsupportedEncodingException {
		Resource response = userService.getImage(token);
		return new ResponseEntity<Resource>(response, HttpStatus.OK);
	}
}

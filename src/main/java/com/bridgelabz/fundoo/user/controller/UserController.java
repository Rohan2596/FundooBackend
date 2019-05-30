 package com.bridgelabz.fundoo.user.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.bridgelabz.fundoo.user.dto.UserDto;
import com.bridgelabz.fundoo.user.model.ForgotPassword;
import com.bridgelabz.fundoo.user.service.UserService;

@RestController
@CrossOrigin(allowedHeaders="*",origins="*")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userservice;
	
	LoginDto loginDto = new LoginDto();
	
	@PostMapping("/registration")
	public ResponseEntity<Response> register (@RequestBody @Valid UserDto userdto, HttpServletRequest httpServletRequest) {
		StringBuffer requestUrl = httpServletRequest.getRequestURL();
		Response response=userservice.registeruser(userdto, requestUrl);
		System.out.println(response);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseToken> loginuser(@RequestBody @Valid LoginDto loginDto){
		//System.out.println("login");
		//System.out.println("login dto "+loginDto.toString());
		ResponseToken response=userservice.loginuser(loginDto);
		System.out.println(response);
		return new ResponseEntity<>( response,HttpStatus.OK);
	}
	
	@GetMapping("/emailvalidation/{token}")
	public ResponseEntity<Response>validateUser(@PathVariable String token) throws UserException, UnsupportedEncodingException{
		Response response=userservice.validateEmail(token);
		System.out.println(response);
		return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
	}

	
	@PostMapping("/forgotPassword")
	public ResponseEntity<Response> forgot(@RequestBody @Valid LoginDto loginDto){
		System.out.println(loginDto.getEmailId());
		System.out.println("inside forgot");
		Response response=userservice.forgotpassword(loginDto);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@PutMapping("/resetPassword/{token}")
	public ResponseEntity<Response> resetpassword(@PathVariable String token, @RequestBody ForgotPassword forgotPassword) throws UserException, UnsupportedEncodingException{
		Response response=userservice.resetpassword(token,forgotPassword);
		System.out.println("Inside reset");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@PostMapping("/changePassword")
	public ResponseEntity<Response> changepassword(@RequestBody  @Valid LoginDto loginDto){
		Response response=userservice.changePassword(loginDto);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@PostMapping("/uploadImage")
	public ResponseEntity<Response> uploadImage(@RequestHeader  String token,@RequestParam MultipartFile file) throws IllegalArgumentException, UnsupportedEncodingException{
		Response response=userservice.uploadImage(token, file);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@GetMapping("/getProfilePic/{token}")
	public ResponseEntity<Resource> getImage(@PathVariable String token) throws IllegalArgumentException, UnsupportedEncodingException{
		Resource response=userservice.getImage(token);
		return new ResponseEntity<Resource>(response,HttpStatus.OK);
	}
	}


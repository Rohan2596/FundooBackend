 package com.bridgelabz.fundoo.user.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDto;
import com.bridgelabz.fundoo.user.dto.UserDto;
import com.bridgelabz.fundoo.user.service.UserService;

@RestController
@CrossOrigin(allowedHeaders="*",origins="*")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userservice;
	
	LoginDto loginDto = new LoginDto();
	
	@PostMapping("/registration")
	public ResponseEntity<Response> register (@RequestBody UserDto userdto, HttpServletRequest httpServletRequest) {
		StringBuffer requestUrl = httpServletRequest.getRequestURL();
		Response response=userservice.registeruser(userdto, requestUrl);
		System.out.println(response);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseToken> loginuser(@RequestBody LoginDto loginDto){
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
	public ResponseEntity<Response> forgot(@RequestParam String emailid){
		System.out.println(emailid);
		System.out.println("inside forgot");
		Response response=userservice.forgotpassword(emailid);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@PutMapping("/resetPassword/{token}")
	public ResponseEntity<Response> resetpassword(@PathVariable String token, @RequestParam String password) throws UserException, UnsupportedEncodingException{
		Response response=userservice.resetpassword(token,password);
		System.out.println("Inside reset");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@GetMapping("/changePassword")
	public ResponseEntity<Response> changepassword(@RequestParam String emailid){
		Response response=userservice.changePassword(emailid);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	}


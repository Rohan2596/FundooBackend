 package com.bridgelabz.fundoo.user.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.dto.UserDto;
import com.bridgelabz.fundoo.user.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userservice;
	@PostMapping("/user/response")
	public ResponseEntity<Response> register (@RequestBody UserDto userdto) {
		Response response=userservice.registeruser(userdto);
		System.out.println(response);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@GetMapping("/user/response")
	public ResponseEntity<Response> loginuser(@RequestBody UserDto userDto){
		Response response=userservice.loginuser(userDto);
		System.out.println(response);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	@GetMapping("/user/emailvalidation/{token}")
	public ResponseEntity<Response>validateUser(@PathVariable String token) throws IllegalArgumentException, UnsupportedEncodingException{
		Response response=userservice.validateEmail(token);
		System.out.println(response);
		return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
	}

	@GetMapping("/user/forgot")
	public ResponseEntity<Response> forgot(@RequestParam String emailid){
		System.out.println("inside forgot");
		Response response=userservice.forgotpassword(emailid);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@PutMapping("/user/resetPassword/{token}")
	public ResponseEntity<Response> resetpassword(@PathVariable String token, @RequestParam String password) throws IllegalArgumentException, UnsupportedEncodingException{
		Response response=userservice.resetpassword(token,password);
		System.out.println("Inside reset");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@GetMapping("/user/changePassword")
	public ResponseEntity<Response> changepassword(@RequestParam String emailid){
		Response response=userservice.changePassword(emailid);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}

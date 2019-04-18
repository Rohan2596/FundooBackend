package com.bridgelabz.fundoo.user.service;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.dto.UserDto;
@Service
public interface IUserService {
Response registeruser(UserDto userDto);
Response loginuser(UserDto userDto);
Response validateEmail(String token) throws IllegalArgumentException, UnsupportedEncodingException;
Response forgotpassword(UserDto userDto);
}

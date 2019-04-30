package com.bridgelabz.fundoo.user.service;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDto;
import com.bridgelabz.fundoo.user.dto.UserDto;
@Service
public interface IUserService {
Response registeruser(UserDto userDto, StringBuffer requestUrl);
ResponseToken loginuser(LoginDto loginDto);
Response validateEmail(String token) throws UserException, UnsupportedEncodingException;
Response forgotpassword(String emailid);
Response resetpassword(String token,String password) throws UserException, UnsupportedEncodingException;
Response changePassword(String emailid);
}

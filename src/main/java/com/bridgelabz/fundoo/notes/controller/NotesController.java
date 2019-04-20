package com.bridgelabz.fundoo.notes.controller;



import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.notes.dto.NotesDto;

import com.bridgelabz.fundoo.notes.service.NotesService;
import com.bridgelabz.fundoo.response.Response;

@RestController
public class NotesController {
@Autowired
NotesService  notesService;

@PostMapping("/user/createNotes")
	public ResponseEntity<Response> createNote(@RequestBody NotesDto notesDto,@RequestHeader String token) throws IllegalArgumentException, UnsupportedEncodingException{
		Response response=notesService.create(notesDto,token);
		System.out.println("response");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
@GetMapping("/user/getnotes")
public ResponseEntity<Response> readsingleNote(@RequestParam String title){
	Response response=notesService.read(title);
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
} 
@PutMapping("/user/updatenotes")
public ResponseEntity<Response> updateNote(@RequestBody NotesDto notesDto,@RequestBody String token,@RequestParam long id) throws IllegalArgumentException, UnsupportedEncodingException{
	System.out.println("Inside in updateNote");
	Response response=notesService.update(notesDto,token,id);
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
@PostMapping("/user/deletenotes")
public ResponseEntity<Response> deleteNote(@RequestBody String token, @RequestParam int id) throws IllegalArgumentException, UnsupportedEncodingException{
	System.out.println("Inside delete note");
	Response response=notesService.delete(token,id);
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
@PostMapping("/user/trash")
public ResponseEntity<Response>trash(@RequestBody String token,@RequestParam int id) throws IllegalArgumentException, UnsupportedEncodingException{
	System.out.println("Inside trash note");
	Response response=notesService.trash(token,id);
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
@PostMapping("/user/pin")
public ResponseEntity<Response>pin(@RequestBody String token, @RequestParam int id) throws IllegalArgumentException, UnsupportedEncodingException{
	System.out.println("Inside pin ");
	Response response=notesService.pin(token,id);
	return new ResponseEntity<>(response,HttpStatus.OK);
}

@PostMapping("/user/archieve")
public ResponseEntity<Response>archieve(@RequestBody String token,@ RequestParam int id) throws IllegalArgumentException, UnsupportedEncodingException{
	System.out.println("Inside pin ");
	Response response=notesService.archieve(token,id);
	return new ResponseEntity<>(response,HttpStatus.OK);
}


}

package com.bridgelabz.fundoo.notes.controller;



import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.notes.dto.NotesDto;

import com.bridgelabz.fundoo.notes.service.NotesService;
import com.bridgelabz.fundoo.response.Response;


@RestController
@CrossOrigin(allowedHeaders="*",origins="*")
@RequestMapping("/user")
public class NotesController {
@Autowired
NotesService  notesService;

@PostMapping("/createNotes")
	public ResponseEntity<Response> createNote(@RequestBody NotesDto notesDto,@RequestHeader String token) throws UserException, UnsupportedEncodingException{
		Response response=notesService.create(notesDto, token);
		System.out.println("response");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
@GetMapping("/getnotes")
public List<NotesDto> readsingleNote(@RequestParam String token) throws UserException, UnsupportedEncodingException{
	List<NotesDto> listnotes=notesService.read(token);
	
	return listnotes;
} 

@PutMapping("/updatenotes")
public ResponseEntity<Response> updateNote(@RequestBody NotesDto notesDto,@RequestBody String token,@RequestParam long id) throws UserException, UnsupportedEncodingException{
	System.out.println("Inside in updateNote");
	Response response=notesService.update(notesDto,token,id);
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
@PostMapping("/deletenotes")
public ResponseEntity<Response> deleteNote(@RequestBody String token, @RequestParam int id) throws UserException, UnsupportedEncodingException{
	System.out.println("Inside delete note");
	Response response=notesService.delete(token,id);
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
@PostMapping("notes/trash")
public ResponseEntity<Response>trash(@RequestHeader String token,@RequestParam int id) throws UserException, UnsupportedEncodingException{
	System.out.println("Inside trash note");
	Response response=notesService.trash(token,id);
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
@PostMapping("notes/pin")
public ResponseEntity<Response>pin(@RequestHeader String token, @RequestParam int id) throws UserException, UnsupportedEncodingException{
	System.out.println("Inside pin ");
	Response response=notesService.pin(token,id);
	return new ResponseEntity<>(response,HttpStatus.OK);
}

@PostMapping("notes/archieve")
public ResponseEntity<Response>archieve(@RequestHeader String token,@ RequestParam int id) throws UserException, UnsupportedEncodingException{
	System.out.println("Inside pin ");
	Response response=notesService.archieve(token,id);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
@GetMapping("notes/addNotetolabel")
public ResponseEntity<Response> addNotetolabel(long labelid, String token, long noteid) throws UserException, UnsupportedEncodingException{
	System.out.println("inside addnotelabel");
	Response response=notesService.addNotetolabel(labelid, token, noteid);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
@GetMapping("notes/removeNotetolabel")
public ResponseEntity<Response> removeNotetolabel(long labelid, String token, long noteid) throws UserException, UnsupportedEncodingException{
	System.out.println("inside addnotelabel");
	Response response=notesService.removeNotetolabel(labelid, token, noteid);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
}

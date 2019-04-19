package com.bridgelabz.fundoo.notes.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<Response> createNote(@RequestBody NotesDto notesDto){
		Response response=notesService.create(notesDto);
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
public ResponseEntity<Response> updateNote(@RequestParam String title,@RequestParam  String description){
	System.out.println("Inside in updateNote");
	Response response=notesService.update(title,description);
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
@PostMapping("/user/deletenotes")
public ResponseEntity<Response> deleteNote(@RequestParam String title){
	System.out.println("Inside delete note");
	Response response=notesService.delete(title);
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
}

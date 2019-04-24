package com.bridgelabz.fundoo.labels.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.labels.dto.LabelsDto;
import com.bridgelabz.fundoo.labels.service.LabelService;
import com.bridgelabz.fundoo.response.Response;

@RestController
public class LabelController {
@Autowired
LabelService labelService;
	@PostMapping("/labels/create")
public ResponseEntity<Response> createlabels(@RequestBody LabelsDto labelsDto,@RequestHeader String token) throws IllegalArgumentException, UnsupportedEncodingException{
	
	Response response=labelService.createlabel(labelsDto,token);
	System.out.println(response);
	return  new ResponseEntity<>(response,HttpStatus.OK) ;
}
	@PostMapping("/labels/update")
public ResponseEntity<Response>updatelabels(@RequestBody LabelsDto labelsDto,@RequestParam String token,@RequestParam long id){
	Response response;
	try {
		response = labelService.updatelabel(labelsDto,token,id);
		System.out.println(response);
		return new ResponseEntity<>(response,HttpStatus.OK);
	
	} catch (IllegalArgumentException e) {

		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {

		e.printStackTrace();
	}
	return null;

}
@PostMapping("/labels/delete")
public ResponseEntity<Response> deletelabels(LabelsDto labelsDto,String token,long id) throws IllegalArgumentException, UnsupportedEncodingException{
	Response response=labelService.deletelabel(labelsDto,token,id);
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
@GetMapping("/labels/readlabels")	
public ResponseEntity<Response> readlabels(LabelsDto labelsDto){
		Response response=labelService.readlabel(labelsDto);
		System.out.println(response);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

@GetMapping("/labels/addnote")
public ResponseEntity<Response> addLabeltoNote(long labelid, String token, long noteid){
	Response response=labelService.addLabelNote(labelid, token, noteid) ;
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
}

@GetMapping("/labels/removenote")
public ResponseEntity<Response> removeLabeltoNote(long labelid, String token, long noteid){
	Response response=labelService.removeLabelNote(labelid, token, noteid) ;
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
}

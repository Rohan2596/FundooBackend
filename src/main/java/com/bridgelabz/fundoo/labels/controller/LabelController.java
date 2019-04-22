package com.bridgelabz.fundoo.labels.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.labels.dto.LabelsDto;
import com.bridgelabz.fundoo.labels.service.LabelService;
import com.bridgelabz.fundoo.response.Response;

@RestController
public class LabelController {
@Autowired
LabelService labelService;
	@PostMapping("/labels/create")
public ResponseEntity<Response> createlabels(LabelsDto labelsDto){
	
	Response response=labelService.createlabel(labelsDto);
	System.out.println(response);
	return  new ResponseEntity<>(response,HttpStatus.OK) ;
}
	@PostMapping("/labels/update")
public ResponseEntity<Response>updatelabels(LabelsDto labelsDto){
	Response response=labelService.updatelabel(labelsDto);
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
	@PostMapping("/labels/delete")
public ResponseEntity<Response> deletelabels(LabelsDto labelsDto){
	Response response=labelService.deletelabel(labelsDto);
	System.out.println(response);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
@GetMapping("/labels/readlabels")	
public ResponseEntity<Response> readlabels(LabelsDto labelsDto){
		Response response=labelService.readlabel(labelsDto);
		System.out.println(response);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}

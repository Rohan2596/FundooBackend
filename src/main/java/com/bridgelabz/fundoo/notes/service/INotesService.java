package com.bridgelabz.fundoo.notes.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.response.Response;

public interface INotesService {
Response create(NotesDto notesDto,String token ) throws IllegalArgumentException, UnsupportedEncodingException;
List<NotesDto> read(String token) throws IllegalArgumentException, UnsupportedEncodingException;
Response update(NotesDto notesDto,String token,long id) throws IllegalArgumentException, UnsupportedEncodingException;
Response delete(String token,int id) throws IllegalArgumentException, UnsupportedEncodingException;
Response trash(String token,int id) throws IllegalArgumentException, UnsupportedEncodingException;
Response pin(String token,int id) throws IllegalArgumentException, UnsupportedEncodingException;
Response archieve(String token,int id) throws IllegalArgumentException, UnsupportedEncodingException;

}

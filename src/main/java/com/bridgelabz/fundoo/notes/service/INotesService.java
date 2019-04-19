package com.bridgelabz.fundoo.notes.service;

import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.response.Response;

public interface INotesService {
Response create(NotesDto notesDto);
Response read(NotesDto notesDto);
Response update(NotesDto notesDto);
Response delete(NotesDto notesDto);

}

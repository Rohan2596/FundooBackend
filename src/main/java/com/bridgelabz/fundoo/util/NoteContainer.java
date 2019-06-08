package com.bridgelabz.fundoo.util;

import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.notes.model.Notes;

@Component
public class NoteContainer {
  private Notes notes;
  private NoteOperation noteOperation;
public Notes getNotes() {
	return notes;
}
public void setNotes(Notes notes) {
	this.notes = notes;
}
public NoteOperation getNoteOperation() {
	return noteOperation;
}
public void setNoteOperation(NoteOperation noteOperation) {
	this.noteOperation = noteOperation;
}
public NoteContainer(Notes notes, NoteOperation noteOperation) {
	super();
	this.notes = notes;
	this.noteOperation = noteOperation;
}
public NoteContainer() {

}
  
	
}

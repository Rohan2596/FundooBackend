package com.bridgelabz.fundoo.elasticSearch;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.notes.model.Notes;


public interface IElasticSearch {
	public Notes create(Notes notes) throws IOException;

	public Notes updateNote(Notes notes) throws IOException;

	public void deleteNote(Long NoteId);

	public List<Notes> searchData(String query, long userId);

}

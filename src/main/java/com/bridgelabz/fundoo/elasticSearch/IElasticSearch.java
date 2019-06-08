package com.bridgelabz.fundoo.elasticSearch;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.notes.model.Notes;
@Service
public interface IElasticSearch {
    public Notes create(Notes notes);
    public Notes updateNote(Notes notes);
    public void deleteNote(Long NoteId);
    public List<Notes> searchData(String query, long userId);
	
}

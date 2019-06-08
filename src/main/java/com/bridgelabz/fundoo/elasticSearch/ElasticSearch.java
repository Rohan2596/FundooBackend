package com.bridgelabz.fundoo.elasticSearch;

import java.io.IOException;
import java.util.List;
import java.util.Map; 
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;


import com.bridgelabz.fundoo.notes.model.Notes;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ElasticSearch implements IElasticSearch {
	   private final String INDEX = "noteindex";
	    private final String TYPE = "notetype";  

	    @Autowired
	    private RestHighLevelClient client;

	    @Autowired
	    private ObjectMapper objectMapper;
	    
	@Override
	public Notes create(Notes notes) {
	       @SuppressWarnings("unchecked")
	        Map<String, Object> dataMap = objectMapper.convertValue(notes, Map.class);
	        IndexRequest indexRequest = new IndexRequest(INDEX,TYPE,String.valueOf(notes.getId())).source(dataMap);
	        try {
	            client.index(indexRequest, RequestOptions.DEFAULT);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return notes;
		
	}

	@Override
	public Notes updateNote(Notes notes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteNote(Long NoteId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Notes> searchData(String query, long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}

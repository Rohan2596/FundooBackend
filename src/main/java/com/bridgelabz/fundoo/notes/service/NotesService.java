package com.bridgelabz.fundoo.notes.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.model.Notes;
import com.bridgelabz.fundoo.notes.respository.NotesRespository;
import com.bridgelabz.fundoo.response.Response;

import com.bridgelabz.fundoo.util.ResponseStatus;

@PropertySource("classpath:message.properties")
@Service
public class NotesService implements INotesService {

	@Autowired
	NotesRespository notesRespository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	Environment environment;

	@Override
	public Response create(NotesDto notesDto) {
		Response response = null;
		Optional<Notes> isNotesPresent = notesRespository.findBytitle(notesDto.getTitle());
		if (isNotesPresent.isPresent()) {
			System.out.println("Notes is already present create an another one");
		} else {
			Notes notes = modelMapper.map(notesDto, Notes.class);
			notes.setTitle(notesDto.getTitle());
			notes.setDescription(notesDto.getDescription());
			notes.setCreatedDate(LocalDateTime.now());
			notes.setArchieve(false);
			notes.setPin(false);
			notes.setTrash(false);
			Notes status = notesRespository.save(notes);
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		}
		return response;
	}

	@Override
	public Response read(String title) {
		Response response = null;
		Optional<Notes> availnotes = notesRespository.findBytitle(title);
		if (availnotes.isPresent()) {
			System.out.println("notes is present");
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		} else {
			System.out.println("Notes is not present");
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes"),
					Integer.parseInt(environment.getProperty("status.success.code")));
		}
		return response;
	}

	@Override
	public Response update(String title,String description) {
		Response response = null;
		Optional<Notes> availnotes = notesRespository.findBytitle(title);
		if (availnotes.isPresent()) {
			System.out.println("notes is present");
			change(availnotes, description);

			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));

		} else {
			System.out.println("notes is not present");
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes"),
					Integer.parseInt(environment.getProperty("status.success.code")));
		}
		return response;
	}
	public Notes change(Optional<Notes> notes,String description) {
		notes.get().setDescription(description);
		return notesRespository.save(notes.get());
	}

	@Override
	public Response delete(String title) {
		Response response=null;
		Optional<Notes>availNotes=notesRespository.findBytitle(title);
		if(availNotes.isPresent()) {
			System.out.println("notes is present");
			Optional<Notes> notes=notesRespository.deleteBytitle(title);
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		}else {
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
	}
		return response;
	}

}

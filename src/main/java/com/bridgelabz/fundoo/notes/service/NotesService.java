package com.bridgelabz.fundoo.notes.service;

import java.io.UnsupportedEncodingException;
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
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.respository.UserRespository;
import com.bridgelabz.fundoo.util.ResponseStatus;
import com.bridgelabz.fundoo.util.TokenGenerators;

@PropertySource("classpath:message.properties")
@Service("notesService")
public class NotesService implements INotesService {

	@Autowired
	NotesRespository notesRespository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	Environment environment;
	@Autowired
	TokenGenerators tokengenerators;
@Autowired
UserRespository userRespository;
	@Override
	public Response create(NotesDto notesDto, String token)
			throws IllegalArgumentException, UnsupportedEncodingException {
		long token1 = tokengenerators.decodeToken(token);
		Response response = null;
		Optional<Notes> isNotesPresent = notesRespository.findBytitle(notesDto.getTitle());
		if (isNotesPresent.isPresent()) {
			System.out.println("Notes is already present create an another one");
		} else {

			Notes notes = modelMapper.map(notesDto, Notes.class);
			Optional<User> user=userRespository.findById(token1);
			notes.setUserid(token1);
			notes.setTitle(notesDto.getTitle());
			notes.setDescription(notesDto.getDescription());
			notes.setCreatedDate(LocalDateTime.now());
			notes.setArchieve(false);
			notes.setPin(false);
			notes.setTrash(false);
			user.get().getNotesUser().add(notes);
			
			notesRespository.save(notes);
			userRespository.save(user.get());
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
	public Response update(NotesDto notesDto, String token, long id)
			throws IllegalArgumentException, UnsupportedEncodingException {
		Response response = null;
		if (notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {
			System.out.println("Notes is empty");
		}
		long Userid = tokengenerators.decodeToken(token);

		Notes notes = notesRespository.findByIdAndUserId(id, Userid);
		notes.setTitle(notesDto.getDescription());
		notes.setDescription(notesDto.getDescription());
		notes.setModifiedDate(LocalDateTime.now());
       notesRespository.save(notes);
		response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public Response delete(String token, int id) throws IllegalArgumentException, UnsupportedEncodingException {
		Response response = null;
		long userid = tokengenerators.decodeToken(token);
		Notes notes = notesRespository.findByIdAndUserId(id, userid);
		if (notes.isTrash() == false) {
			System.out.println("delete notes and putting into trash");
			notes.setTrash(true);
			notes.setModifiedDate(LocalDateTime.now());
			notesRespository.save(notes);

			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		} else {
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		}
		return response;

	}

	@Override
	public Response trash(String token, int id) throws IllegalArgumentException, UnsupportedEncodingException {
		Response response = null;
		long userid = tokengenerators.decodeToken(token);
		Notes notes = notesRespository.findByIdAndUserId(id, userid);

		if (notes.isTrash()) {
			System.out.println("notes trash");
			notes.setTrash(true);
			notes.setModifiedDate(LocalDateTime.now());
			notesRespository.save(notes);
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		} else {
			System.out.println("trash not set");
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		}
		return response;
	}

	@Override
	public Response pin(String token, int id) throws IllegalArgumentException, UnsupportedEncodingException {
		Response response = null;
		long userid = tokengenerators.decodeToken(token);
		Notes notes = notesRespository.findByIdAndUserId(id, userid);

		if (notes.isPin()) {
			System.out.println("notes trash");
			notes.setPin(true);
			notes.setModifiedDate(LocalDateTime.now());
			notesRespository.save(notes);
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		} else {
			System.out.println("trash not set");
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		}
		return response;
	}

	@Override
	public Response archieve(String token, int id) throws IllegalArgumentException, UnsupportedEncodingException {
		Response response = null;
		long userid = tokengenerators.decodeToken(token);
		Notes notes = notesRespository.findByIdAndUserId(id, userid);

		if (notes.isPin()) {
			System.out.println("notes trash");
			notes.setPin(true);
			notes.setModifiedDate(LocalDateTime.now());
			notesRespository.save(notes);
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		} else {
			System.out.println("trash not set");
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		}
		return response;
	}

}

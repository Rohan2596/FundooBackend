package com.bridgelabz.fundoo.notes.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.elasticSearch.IElasticSearch;
import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.labels.model.Labels;
import com.bridgelabz.fundoo.labels.respository.LabelRespository;
import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.model.Notes;
import com.bridgelabz.fundoo.notes.respository.NotesRespository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.respository.UserRespository;

import com.bridgelabz.fundoo.util.ResponseStatus;
import com.bridgelabz.fundoo.util.TokenGenerators;
import com.bridgelabz.fundoo.util.rabbitMqElasticResearch;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	@Autowired
	IElasticSearch elastic;

	@Autowired
	rabbitMqElasticResearch elasticRabbit;

	@Autowired
	LabelRespository labelRespository;

	@Override
	public Response create(NotesDto notesDto, String token) throws UserException, UnsupportedEncodingException {
		long token1 = tokengenerators.decodeToken(token);
		Notes note1 = null;
		if (notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {
			throw new UserException("Notes are empty", -5);
		} else {
			Notes notes = modelMapper.map(notesDto, Notes.class);
			Optional<User> user = userRespository.findById(token1);
			notes.setUserid(token1);
			notes.setCreatedDate(LocalDateTime.now());
			notes.setArchieve(false);
			notes.setPin(false);
			notes.setTrash(false);
			user.get().getNotes().add(notes);

			
 
		    note1  =notesRespository.save(notes);
			elasticRabbit.rabitsendelastic(note1);
			try {
				elastic.create(note1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userRespository.save(user.get());

			Response response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
			return response;
		}
	}

	@Override
	public List<Notes> read(String token) throws UserException, UnsupportedEncodingException {

		long userid = tokengenerators.decodeToken(token);
		List<Notes> notes1 = (List<Notes>) notesRespository.findByUserId(userid);
		List<Notes> listnotes = new ArrayList<>();
		for (Notes usernotes : notes1) {
			Notes notes = modelMapper.map(usernotes, Notes.class);
			System.out.println("notes all fbsvsvbsvn sub ");
			if (notes.isTrash() == false && notes.isArchieve() == false && notes.isPin() == false) {
				listnotes.add(notes);
				System.out.println(listnotes);
			}
		}
		return listnotes;
	}

	@Override
	public Response update(NotesDto notesDto, String token, long id)
			throws UserException, UnsupportedEncodingException {
		Response response = null;
		Notes note1 = new Notes();
		if (notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {
			throw new UserException("Notes are empty", -5);
		}
		long Userid = tokengenerators.decodeToken(token);

		Notes notes = notesRespository.findByNoteidAndUserId(id, Userid);
		notes.setTitle(notesDto.getTitle());
		notes.setDescription(notesDto.getDescription());


note1=		notesRespository.save(notes);  
try {
	elasticRabbit.send(note1);
} catch (IOException e) {

	e.printStackTrace();
}
		response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
				Integer.parseInt(environment.getProperty("status.success.notes.code")));
		return response;
	}

	@Override
	public Response delete(String token, int id) throws UserException, UnsupportedEncodingException {
		Response response = null;
	
		long userid = tokengenerators.decodeToken(token);
		Notes notes = notesRespository.findByNoteidAndUserId(id, userid);

		if (notes.isTrash() == true) {
			System.out.println("delete notes and putting into trash");
//			notes.setTrash(true);
//			notes.setModifiedDate(LocalDateTime.now());
	notesRespository.delete(notes);
			elasticRabbit.rabitsendelastic(notes);
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		} else {
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		}
		return response;

	}

	@Override
	public Response trash(String token, int id) throws UserException, UnsupportedEncodingException {
		Response response = null;
		long userid = tokengenerators.decodeToken(token);
		Notes notes = notesRespository.findByNoteidAndUserId(id, userid);
		Notes note1 = new Notes();

		if (notes.isTrash() == false) {
			System.out.println("notes trash");
			notes.setTrash(true);
//			note1 = modelMapper.map(notes, Notes.class);
//			LocalDateTime cr = note1.getCreatedDate();
//			note1.setCreatedDate(null);
//			note1.setModifiedDate(null);
//			System.out.println(note1);
//			elasticRabbit.rabitsendelastic(note1);
//			note1.setCreatedDate(cr);
//			note1.setModifiedDate(LocalDateTime.now());

note1=			notesRespository.save(notes);
			elasticRabbit.rabitsendelastic(note1);
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		} else if (notes.isTrash() == true) {
			System.out.println("notes trash");
			notes.setTrash(false);
//			note1 = modelMapper.map(notes, Notes.class);
//			LocalDateTime cr = note1.getCreatedDate();
//			note1.setCreatedDate(null);
//			note1.setModifiedDate(null);
//			System.out.println(note1);
//			elasticRabbit.rabitsendelastic(note1);
//			note1.setCreatedDate(cr);
//			note1.setModifiedDate(LocalDateTime.now());

	note1=		notesRespository.save(notes);
			elasticRabbit.rabitsendelastic(note1);
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
	public Response pin(String token, int id) throws UserException, UnsupportedEncodingException {
		Response response = null;
		Notes note1 = new Notes();
		long userid = tokengenerators.decodeToken(token);
		Notes notes = notesRespository.findByNoteidAndUserId(id, userid);

		if (notes.isPin() == false) {
			System.out.println("notes pinned");
			notes.setPin(true);
			notes.setModifiedDate(LocalDateTime.now());
			notesRespository.save(notes);
//			elasticRabbit.rabitsendelastic(notes);
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		} else if (notes.isPin() == true) {
			System.out.println("notes unpinned");
			notes.setPin(false);
			note1 = modelMapper.map(notes, Notes.class);
			LocalDateTime cr = note1.getCreatedDate();
			note1.setCreatedDate(null);
			note1.setModifiedDate(null);
			System.out.println(note1);
			elasticRabbit.rabitsendelastic(note1);
			note1.setCreatedDate(cr);
			note1.setModifiedDate(LocalDateTime.now());

			notesRespository.save(note1);

//			elasticRabbit.rabitsendelastic(notes);
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
	public Response archieve(String token, int id) throws UserException, UnsupportedEncodingException {
		Response response = null;
		Notes note1 = new Notes();
		long userid = tokengenerators.decodeToken(token);
		Notes notes = notesRespository.findByNoteidAndUserId(id, userid);

		if (notes.isArchieve() == false) {
			System.out.println("notes unarchive");
			notes.setArchieve(true);
			notes.setPin(false);
			note1 = modelMapper.map(notes, Notes.class);
			LocalDateTime cr = note1.getCreatedDate();
			note1.setCreatedDate(null);
			note1.setModifiedDate(null);
			System.out.println(note1);
			elasticRabbit.rabitsendelastic(note1);
			note1.setCreatedDate(cr);
			note1.setModifiedDate(LocalDateTime.now());

			notesRespository.save(note1);
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		} else if (notes.isArchieve() == true) {
			System.out.println("notes unarchive");
			notes.setArchieve(false);
			note1 = modelMapper.map(notes, Notes.class);
			LocalDateTime cr = note1.getCreatedDate();
			note1.setCreatedDate(null);
			note1.setModifiedDate(null);
			System.out.println(note1);
			elasticRabbit.rabitsendelastic(note1);
			note1.setCreatedDate(cr);
			note1.setModifiedDate(LocalDateTime.now());

			notesRespository.save(note1);

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
	public Response addCollabtoNote(long noteid, String token, String collabemailid)
			throws IllegalArgumentException, UnsupportedEncodingException {
		Response response = null;
		long userid = tokengenerators.decodeToken(token);
		Optional<User> user = userRespository.findById(userid);
		if (user.isPresent()) {
			Notes notes = notesRespository.findByNoteidAndUserId(noteid, userid);
			if (!notes.equals(null)) {
				User collabid = userRespository.findByEmailId(collabemailid).get();

				if (!collabid.equals(null) && notes.getCollabId().contains(collabid) == false) {
					notes.setModifiedDate(LocalDateTime.now());
					notes.getCollabId().add(collabid);
					collabid.getCollabnotes().add(notes);
					notesRespository.save(notes);
					userRespository.save(collabid);
					response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
							Integer.parseInt(environment.getProperty("status.success.notes.code")));
				} else {
					throw new UserException("collabid is not presen");
				}
			} else {
				throw new UserException("notes not present", -10);
			}
		} else {
			throw new UserException("user exception");
		}
		response = ResponseStatus.statusinfo(environment.getProperty("status.failure.notes.created"),
				Integer.parseInt(environment.getProperty("status.failure.notes.code")));
		return response;
	}

	@Override
	public Response removeCollabtoNote(long noteid, String token, String collabemailid)
			throws IllegalArgumentException, UnsupportedEncodingException {
		Response response = null;
		long userid = tokengenerators.decodeToken(token);
		Optional<User> user = userRespository.findById(userid);
		if (user.isPresent()) {
			Notes notes = notesRespository.findByNoteidAndUserId(noteid, userid);
			if (!notes.equals(null)) {
				User collabid = userRespository.findByEmailId(collabemailid).get();

				if (!collabid.equals(null) && notes.getCollabId().contains(collabid) == true) {
					notes.setModifiedDate(LocalDateTime.now());
					notes.getCollabId().remove(collabid);
					collabid.getCollabnotes().remove(notes);
					notesRespository.save(notes);
					userRespository.save(collabid);
					response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
							Integer.parseInt(environment.getProperty("status.success.notes.code")));
				} else {
					throw new UserException("collabid is not presen");
				}
			} else {
				throw new UserException("notes not present", -10);
			}
		} else {
			throw new UserException("user exception");
		}
		response = ResponseStatus.statusinfo(environment.getProperty("status.failure.notes.created"),
				Integer.parseInt(environment.getProperty("status.failure.notes.code")));
		return response;
	}

	@Override
	public Response addNotetolabel(long labelid, String token, long noteid)
			throws UserException, UnsupportedEncodingException {
		Response response = null;
		long userid = tokengenerators.decodeToken(token);
		Optional<User> user = userRespository.findById(userid);
		Notes notes = notesRespository.findByNoteidAndUserId(noteid, userid);
		Labels labels = labelRespository.findByLabelIdAndUserId(labelid, userid);
		if (user.isPresent()) {
			notes.setModifiedDate(LocalDateTime.now());
			notes.getNLabels().add(labels);
			labels.getLNotes().add(notes);
			labelRespository.save(labels);
			notesRespository.save(notes);
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));

		}
		response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
				Integer.parseInt(environment.getProperty("status.success.notes.code")));
		return response;
	}

	@Override
	public Response removeNotetolabel(long labelid, String token, long noteid)
			throws UserException, UnsupportedEncodingException {
		Response response = null;

		long userid = tokengenerators.decodeToken(token);
		Optional<User> user = userRespository.findById(userid);
		Notes notes = notesRespository.findByNoteidAndUserId(noteid, userid);
		Labels labels = labelRespository.findByLabelIdAndUserId(labelid, userid);
		if (user.isPresent()) {
			notes.setModifiedDate(LocalDateTime.now());
			notes.getNLabels().remove(labels);
			labels.getLNotes().remove(notes);
			labelRespository.save(labels);
			notesRespository.save(notes);

			response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
					Integer.parseInt(environment.getProperty("status.success.notes.code")));
		}
		response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
				Integer.parseInt(environment.getProperty("status.success.notes.code")));
		return response;
	}

	@Override
	public List<Notes> trashnotes(String token) throws IllegalArgumentException, UnsupportedEncodingException {
		long userid = tokengenerators.decodeToken(token);
		List<Notes> notes1 = (List<Notes>) notesRespository.findByUserId(userid);
		List<Notes> listnotes = new ArrayList<>();
		for (Notes usernotes : notes1) {
			Notes notes = modelMapper.map(usernotes, Notes.class);
			System.out.println("notes all fbsvsvbsvn sub ");
			if (notes.isTrash() == true) {
				listnotes.add(notes);
				System.out.println(listnotes);
			}
		}
		return listnotes;
	}

	@Override
	public List<Notes> archivenotes(String token) throws UserException, UnsupportedEncodingException {
		long userid = tokengenerators.decodeToken(token);
		List<Notes> notes1 = (List<Notes>) notesRespository.findByUserId(userid);
		List<Notes> listnotes = new ArrayList<>();
		for (Notes usernotes : notes1) {
			Notes notes = modelMapper.map(usernotes, Notes.class);
			System.out.println("notes all fbsvsvbsvn sub ");
			if (notes.isArchieve() == true && notes.isTrash() == false) {
				listnotes.add(notes);
				System.out.println(listnotes);
			}
			System.out.println(listnotes.size());
		}
		return listnotes;
	}

	@Override
	public List<Notes> pinnotes(String token) throws UserException, UnsupportedEncodingException {
		long userid = tokengenerators.decodeToken(token);
		System.out.println("^^^^^^^" + userid);
		List<Notes> notes1 = notesRespository.findByUserId(userid);
		System.out.println("****************************" + notes1.toString());

		List<Notes> listnotes = new ArrayList<>();
		System.out.println(listnotes.toString());

		for (Notes usernotes : notes1) {
			Notes notes = modelMapper.map(usernotes, Notes.class);
			System.out.println("notes all fbsvsvbsvn sub ");

			if (notes.isTrash() == false && notes.isArchieve() == false && notes.isPin() == true) {
				listnotes.add(notes);
				System.out.println(listnotes);
			}
		}
		return listnotes;
	}

	@Override
	public List<Labels> getAlllabels(String token, long noteid)
			throws IllegalArgumentException, UnsupportedEncodingException {
		long userid = tokengenerators.decodeToken(token);
		Optional<User> user = userRespository.findById(userid);
		if (user.isPresent()) {
			Optional<Notes> note = notesRespository.findById(noteid);
			if (note.isPresent()) {
				List<Labels> listLabel = note.get().getNLabels();

				return listLabel;
			} else {
				throw new UserException("note is not present");
			}

		} else {
			throw new UserException("user is not present");
		}

	}

	@Override
	public List<User> getcollablist(String token, long noteid)
			throws IllegalArgumentException, UnsupportedEncodingException {
		long userid = tokengenerators.decodeToken(token);
		Optional<User> user = userRespository.findById(userid);
		if (user.isPresent()) {
			Optional<Notes> note = notesRespository.findById(noteid);
			if (note.isPresent()) {
				List<User> collablist = note.get().getCollabId();

				return collablist;
			} else {
				throw new UserException("note is not present");
			}

		} else {
			throw new UserException("user is not present");
		}

	}

	@Override
	public Response color(String token, long noteid, String color)
			throws IllegalArgumentException, UnsupportedEncodingException {
		Response response = null;
		Notes note1 = new Notes();
		long userid = tokengenerators.decodeToken(token);
		Optional<User> user = userRespository.findById(userid);
		Notes notes = notesRespository.findByNoteidAndUserId(noteid, userid);
		if (user.isPresent()) {
			Optional<Notes> note = notesRespository.findById(noteid);
			if (note.isPresent()) {

				notes.setColor(color);
				// System.out.println(note1);
				note1 = modelMapper.map(notes, Notes.class);
				LocalDateTime cr = note1.getCreatedDate();
				note1.setCreatedDate(null);
				note1.setModifiedDate(null);
				System.out.println(note1);
				elasticRabbit.rabitsendelastic(note1);
				note1.setCreatedDate(cr);
				note1.setModifiedDate(LocalDateTime.now());
//			elastic.updateNote(notes);
				notesRespository.save(note1);

				response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
						Integer.parseInt(environment.getProperty("status.success.notes.code")));
				return response;
			} else {
				throw new UserException("note is not present");
			}
		} else {
			throw new UserException("User is not present");
		}

	}

	@Override
	public Response reminder(String token, long noteid, String date)
			throws IllegalArgumentException, UnsupportedEncodingException {
		Response response = null;
		Notes note1 = new Notes();
		long userid = tokengenerators.decodeToken(token);
		Optional<User> user = userRespository.findById(userid);
		Notes notes = notesRespository.findByNoteidAndUserId(noteid, userid);
		if (user.isPresent()) {
			Optional<Notes> note = notesRespository.findById(noteid);
			if (note.isPresent()) {
				notes.setReminder(date);

				note1 = modelMapper.map(notes, Notes.class);
				LocalDateTime cr = note1.getCreatedDate();
				note1.setCreatedDate(null);
				note1.setModifiedDate(null);
				System.out.println(note1);
				elasticRabbit.rabitsendelastic(note1);
				note1.setCreatedDate(cr);
				note1.setModifiedDate(LocalDateTime.now());

				notesRespository.save(note1);

				response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
						Integer.parseInt(environment.getProperty("status.success.notes.code")));
				return response;
			} else {
				throw new UserException("note is not present");
			}
		} else {
			throw new UserException("User is not present");
		}

	}

	@Override
	public List<Notes> getReminder(String token) throws IllegalArgumentException, UnsupportedEncodingException {
		long userid = tokengenerators.decodeToken(token);
		List<Notes> notes1 = (List<Notes>) notesRespository.findByUserId(userid);
		List<Notes> listnotes = new ArrayList<>();
		for (Notes usernotes : notes1) {
			Notes notes = modelMapper.map(usernotes, Notes.class);
			System.out.println("notes all fbsvsvbsvn sub ");
			if (notes.getReminder() != null) {
				listnotes.add(notes);
				System.out.println(listnotes);
			}
		}
		return listnotes;
	}

	@Override
	public Response deletereminder(String token, long noteid)
			throws IllegalArgumentException, UnsupportedEncodingException {
		Response response = null;
		Notes note1 = new Notes();
		long userid = tokengenerators.decodeToken(token);
		Optional<User> user = userRespository.findById(userid);
		Notes notes = notesRespository.findByNoteidAndUserId(noteid, userid);
		if (user.isPresent()) {
			Optional<Notes> note = notesRespository.findById(noteid);
			if (note.isPresent()) {
				notes.setReminder(null);
				note1 = modelMapper.map(notes, Notes.class);
				LocalDateTime cr = note1.getCreatedDate();
				note1.setCreatedDate(null);
				note1.setModifiedDate(null);
				System.out.println(note1);
				elasticRabbit.rabitsendelastic(note1);
				note1.setCreatedDate(cr);
				note1.setModifiedDate(LocalDateTime.now());

				notesRespository.save(note1);

				response = ResponseStatus.statusinfo(environment.getProperty("status.success.notes.created"),
						Integer.parseInt(environment.getProperty("status.success.notes.code")));
				return response;
			} else {
				throw new UserException("note is not present");
			}
		} else {
			throw new UserException("User is not present");
		}

	}

}

package com.bridgelabz.fundoo.notes.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoo.notes.model.Notes;
import com.bridgelabz.fundoo.user.model.User;

public interface  NotesRespository  extends JpaRepository<Notes,Integer>{
	public Optional<Notes> findBytitle(String title);
	public Optional<Notes> deleteBytitle(String title);
}

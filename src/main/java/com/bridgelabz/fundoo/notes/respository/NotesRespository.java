package com.bridgelabz.fundoo.notes.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoo.notes.model.Notes;

public interface  NotesRespository  extends JpaRepository<Notes,Integer>{

}

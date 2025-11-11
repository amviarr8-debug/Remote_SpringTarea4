package com.amelia.demonotes2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amelia.demonotes2.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

}

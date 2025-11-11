package com.amelia.demonotes2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amelia.demonotes2.model.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByTitleContainingIgnoreCase(String keyword);
}

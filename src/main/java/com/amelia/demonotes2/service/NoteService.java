package com.amelia.demonotes2.service;


import com.amelia.demonotes2.exception.ConcurrencyConflictException;
import com.amelia.demonotes2.exception.NoteNotFoundException;
import com.amelia.demonotes2.model.Note;
import com.amelia.demonotes2.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Marca esta clase como un Bean de Servicio
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }
    public List<Note> findByKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return findAll(); // Sin filtro
        }
        return noteRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // @Override
    public Note findById(Long id) {
        // Mueve la lógica de error 404 del Controller REST/MVC al Service
        return noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
    }

    // @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }

    // @Override
    public void deleteById(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException(id);
        }
        noteRepository.deleteById(id);
    }

    // @Override
    public Note update(Long id, Note noteDetails) {

        Note existingNote = findById(id); // Usa findById() para rehusar la lógica de excepción

        existingNote.setTitle(noteDetails.getTitle());
        existingNote.setContent(noteDetails.getContent());
        existingNote.setDate(existingNote.getDate());
        existingNote.setCategory(noteDetails.getCategory());


        return noteRepository.save(existingNote);
    }


}

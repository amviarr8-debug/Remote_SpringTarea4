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

    // Aquí se inyecta el $NoteRepository$ y se implementa la lógica actual que está
    // en tus $Controllers$.
    // Inyección por constructor (preferida sobre @Autowired en campo)
    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
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
            // Lógica de validación de existencia antes de borrar (opcional, pero útil)
            throw new NoteNotFoundException(id);
        }
        noteRepository.deleteById(id);
    }

    // @Override
    public Note update(Long id, Note noteDetails) {
        // 1. Reutiliza la gestión del 404
        Note existingNote = findById(id); // Usa findById() para rehusar la lógica de excepción

        // 2. ** Lógica de Simulación de Conflicto ** (Movida del Controller)
        /*if ("CONFLICTO".equalsIgnoreCase(noteDetails.getContent())) {
            throw new ConcurrencyConflictException("Nota ID " + id);
        }*/
        // ** Fin de la Simulación **

        // 3. Aplica las actualizaciones
        existingNote.setTitle(noteDetails.getTitle());
        existingNote.setContent(noteDetails.getContent());

        // 4. Guarda y devuelve
        return noteRepository.save(existingNote);
    }

}

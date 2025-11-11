    package com.amelia.demonotes2.controller;
import java.util.List;

import com.amelia.demonotes2.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.amelia.demonotes2.model.Note;
import com.amelia.demonotes2.repository.NoteRepository;

import jakarta.validation.Valid;

    @RestController
    @RequestMapping("/api/notes")
    public class NoteController {
        // CAMBIO: Inyectar NoteService
        @Autowired
        private NoteService noteService;

        // Si tenemos varios servicios que implementen NoteService
        // siendo esta una interfaz, puede asumir la implementación que hayamos
        // configurado
        @GetMapping
        public List<Note> getAllNotes(@RequestParam(required = false) String keyword) {

            return noteService.findByKeyword(keyword);
        }


        @PostMapping
        public Note createNote(@RequestBody Note note) {
            // DELEGAR: El Service se encarga de save
            return noteService.save(note);
        }

        @GetMapping("/{id}")
        public Note getNoteById(@PathVariable Long id) {
            // DELEGAR: El Service se encarga de findById (y de lanzar
            // NoteNotFoundException)
            return noteService.findById(id);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
            // DELEGAR: El Service se encarga de la lógica de borrado y de errores
            noteService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        @PutMapping("/{id}")
        public Note updateNote(@PathVariable Long id, @Valid @RequestBody Note noteDetails) {
            // DELEGAR: El Service se encarga de encontrar, actualizar y de la lógica de
            // conflicto
            return noteService.update(id, noteDetails);
        }
        // ...
    }
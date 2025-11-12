package com.amelia.demonotes2.controller;

import java.util.Comparator;
import java.util.List;

import com.amelia.demonotes2.model.Category;
import com.amelia.demonotes2.repository.CategoriaRepositorio;
import com.amelia.demonotes2.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.amelia.demonotes2.model.Note;



import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {

    // CAMBIO: Inyectar NoteService
    @Autowired
    private NoteService noteService;

    @Autowired
    private CategoriaRepositorio categoriaRepository;


    @GetMapping("/menu")
    public String showMenu(Model model) {

        return "menu";
    }

    @GetMapping("/list-notes")
    public String showAllNotes(@RequestParam(required = false) String keyword, Model model) {
        List<Note> notes = noteService.findByKeyword(keyword);
        notes.sort(Comparator.comparingInt(n -> n.getDate().getDayOfYear())); // orden por fecha
        model.addAttribute("notes", notes);
        model.addAttribute("keyword", keyword);
        return "list_notes";
    }


    @GetMapping("/new-note")
    public String showNewNoteForm(Model model) {
        // DELEGAR: El Service se encarga de crear un nuevo objeto Note
        Note note = new Note();
        model.addAttribute("note", note);
        // === NUEVO: Pasamos la lista de categorías a la vista ===
        model.addAttribute("categorias", categoriaRepository.findAll());

        return "new_note";
    }

    @PostMapping("/new-note")
    public String createNote(@Valid Note note, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("note", note);
            return "new_note";
        }
        // DELEGAR: El Service se encarga de save()
        noteService.save(note);
        return "redirect:/list-notes";
    }

    @GetMapping("/edit-note/{id}")
    public String showEditNoteForm(@PathVariable Long id, Model model) {
        // DELEGAR: El Service se encarga de findById (y del 404)
        Note note = noteService.findById(id);
        model.addAttribute("note", note);
        // === NUEVO: Pasamos la lista de categorías a la vista ===
        model.addAttribute("categorias", categoriaRepository.findAll());

        return "edit_note";
    }

    @PutMapping("/edit-note/{id}")
    public String updateNoteMvc(@PathVariable Long id, @Valid Note note, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("note", note);
            return "edit_note";
        }
        noteService.update(id, note);

        return "redirect:/list-notes";
    }
    @GetMapping("/forced-error")
    public String forceError() {
        throw new RuntimeException("Error 500 de prueba forzado desde la vista.");

    }

    @GetMapping("/delete-note/{id}")
    public String showDeleteNoteForm(@PathVariable Long id, Model model) {
        Note note = noteService.findById(id);
        model.addAttribute("note", note);
        return "delete_note";
    }

    @DeleteMapping("/delete-note/{id}")
    public String deleteNoteMvc(@PathVariable Long id) {
        noteService.deleteById(id);
        return "redirect:/list-notes";
    }
    @GetMapping("/list-category")
    public String showAllCategory(Model model) {
        List<Category> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias); // para poderlo usar en la vista
        return "list_category";
    }


}

package com.Homework.springMvcdemo.controller;

import com.Homework.springMvcdemo.entity.Note;
import com.Homework.springMvcdemo.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(value = "/note")
public class NoteController {

    private NoteService noteService;
    private Long id = 0L;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping(value = "/")
    public ModelAndView getHello() {
        ModelAndView result = new ModelAndView("home");
        return result;
    }

    @GetMapping(value = "/add")
    public String addNewNote(Model model) {
        return "add";
    }

    @GetMapping(value = "/{id}/details")
    public String noteDetails(@PathVariable(value = "id") Long id,Model model) {

        Optional<Note> notes = Optional.ofNullable(noteService.getById(id));
        ArrayList<Note> res = new ArrayList<>();
        notes.ifPresent(res::add);
        model.addAttribute("notes", res);
        return "details";
    }

    @PostMapping(value = "/add")
    public String addNewNote(@RequestParam String title,@RequestParam String content, Model model) {
        Note note = new Note(++id, title, content);
        noteService.add(note);
        return "redirect:/note/list";
    }

    @GetMapping(value = "/list")
    public ModelAndView getAllNotes() {
        ModelAndView result = new ModelAndView("list");
        result.addObject("notes", noteService.listAll());
        return result;
    }

    @GetMapping(value = "/{id}/edit")
    public String editNote(@PathVariable(name = "id") Long id, Model model){
        Optional<Note> notes = Optional.ofNullable(noteService.getById(id));
        ArrayList<Note> res = new ArrayList<>();
        notes.ifPresent(res::add);
        model.addAttribute("notes", res);
        return "/edit";
    }

    @PostMapping(value = "/{id}/edit")
    public String updateNote(@PathVariable(value = "id") Long id, @RequestParam String title,@RequestParam String content) throws Exception {
        Note note = noteService.getById(id);
        note.setTitle(title);
        note.setContent(content);
        noteService.update(note);

        return "redirect:/note/list";
    }

    @PostMapping(value = "/{id}/delete")
    public String deleteNote(@PathVariable(value = "id") Long id) {
        Note note = noteService.getById(id);
        noteService.removeNote(note);
        return "redirect:/note/list";
    }

}

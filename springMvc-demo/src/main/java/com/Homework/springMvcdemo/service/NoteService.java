package com.Homework.springMvcdemo.service;

import com.Homework.springMvcdemo.entity.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private List<Note> notes = new ArrayList<>();


    public List<Note> listAll() {
        return notes;
    }

    public Note add(Note note) {
        notes.add(note);
        return notes.get(notes.size() - 1);
    }

    public void deleteById(Long id) {
           for (Note note : notes) {
            if (note.getId().equals(id)) {
                notes.remove(note);
            }
            throw new IllegalArgumentException("No notes with id = " + id);
        }
    }

    public void removeNote(Note note) {
        notes.remove(note);
    }

    public void update(Note note) throws Exception {
        for (Note existingNote : notes) {
            if (existingNote.getId().equals(note.getId())) {
                existingNote.setTitle(note.getTitle());
                existingNote.setContent(note.getContent());
                return;
            }
        }
        throw new IllegalArgumentException("List does not contain this note");
    }

    public Note getById(Long id) {
        for (Note note : notes) {
            if (note.getId().equals(id))
                return note;
        }
        throw new IllegalArgumentException("There is no notes with id = " + id);
    }
}
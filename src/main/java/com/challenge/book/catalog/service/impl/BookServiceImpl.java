package com.challenge.book.catalog.service.impl;

import com.challenge.book.catalog.dto.BookRequestDTO;
import com.challenge.book.catalog.exception.RecordNotFoundException;
import com.challenge.book.catalog.model.Book;
import com.challenge.book.catalog.repository.BookRepository;
import com.challenge.book.catalog.service.AuthorService;
import com.challenge.book.catalog.service.BookService;
import com.challenge.book.catalog.service.SubjectService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final SubjectService subjectService;

    public BookServiceImpl(BookRepository bookRepository,
            AuthorService authorService,
            SubjectService subjectService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.subjectService = subjectService;
    }

    @Override
    public Book create(BookRequestDTO request) {
        Book record = BookRequestDTO.toBook(request);
        record.getAuthors().stream()
                .forEach(a -> a.setName(
                        authorService.get(a.getCodAu()).getName()));
        record.getSubjects().stream()
                .forEach(a -> a.setDescription(
                        subjectService.get(a.getCodAs()).getDescription()));
        return bookRepository.save(record);
    }

    @Override
    public Book update(String codL, BookRequestDTO request) {
        Book record = this.get(codL);
        record = BookRequestDTO.toBook(request);
        record.setCodL(codL);
        record.getAuthors().stream()
                .forEach(a -> a.setName(
                        authorService.get(a.getCodAu()).getName()));
        record.getSubjects().stream()
                .forEach(a -> a.setDescription(
                        subjectService.get(a.getCodAs()).getDescription()));
        return bookRepository.save(record);
    }

    @Override
    public Book get(String codL) {
        return bookRepository.findById(codL).orElseThrow(() -> new RecordNotFoundException(codL));
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public void delete(String codL) {
        if (this.get(codL) != null)
            bookRepository.deleteById(codL);
    }

    private List<Book> getByAuthor(String codAu) {
        return this.getAll().stream()
                .filter(e -> e.getAuthors()
                        .stream()
                        .anyMatch(a -> a.getCodAu().equals(codAu)))
                .collect(Collectors.toList());
    }

    public boolean existAuthor(String codAu) {
        return !this.getByAuthor(codAu).isEmpty();
    }

    private List<Book> getBySubject(String codAs) {
        return this.getAll().stream()
                .filter(e -> e.getSubjects()
                        .stream()
                        .anyMatch(a -> a.getCodAs().equals(codAs)))
                .collect(Collectors.toList());
    }

    public boolean existSubject(String codAs) {
        return !this.getBySubject(codAs).isEmpty();
    }

    public void refreshAuthor(String codAu, String name) {
        this.getByAuthor(codAu).stream()
                .forEach(b -> {
                    b.getAuthors().stream().forEach(a -> a.setName(name));
                    bookRepository.save(b);
                });
    }

    public void refreshSubject(String codAs, String description) {
        this.getBySubject(codAs).stream()
                .forEach(b -> {
                    b.getSubjects().stream().forEach(a -> a.setDescription(description));
                    bookRepository.save(b);
                });
    }

}

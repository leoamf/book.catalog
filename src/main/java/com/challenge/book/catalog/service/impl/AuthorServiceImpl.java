package com.challenge.book.catalog.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.challenge.book.catalog.dto.AuthorRequestDTO;
import com.challenge.book.catalog.exception.RecordNotFoundException;
import com.challenge.book.catalog.model.Author;
import com.challenge.book.catalog.repository.AuthorRepository;
import com.challenge.book.catalog.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author create(AuthorRequestDTO request) {
        return authorRepository.save(AuthorRequestDTO.toAuthor(request));
    }

    @Override
    public Author update(String codAu, AuthorRequestDTO request) {
        Author record = this.get(codAu);
        record = AuthorRequestDTO.toAuthor(request);
        record.setCodAu(codAu);
        return authorRepository.save(record);
    }

    @Override
    public Author get(String codAu) {
        return authorRepository.findById(codAu).orElseThrow(() -> new RecordNotFoundException(codAu));
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public void delete(String codAu) {
        if (this.get(codAu) != null)
            authorRepository.deleteById(codAu);
    }

}

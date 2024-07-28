package com.challenge.book.catalog.service;

import java.util.List;
import com.challenge.book.catalog.dto.AuthorRequestDTO;
import com.challenge.book.catalog.model.Author;

public interface AuthorService {

    Author create(AuthorRequestDTO author);

    Author update(String codAu, AuthorRequestDTO author);

    Author get(String codAu);

    List<Author> getAll();

    void delete(String codAu);

}

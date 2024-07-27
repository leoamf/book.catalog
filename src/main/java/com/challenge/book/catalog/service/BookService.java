package com.challenge.book.catalog.service;

import java.util.List;
import com.challenge.book.catalog.dto.BookRequestDTO;
import com.challenge.book.catalog.model.Book;

public interface BookService {

    Book create(BookRequestDTO book);

    Book update(String codL, BookRequestDTO book);

    Book get(String codL);

    List<Book> gettAll();

    void delete(String codL);

    boolean existAuthor(String codAu);

    boolean existSubject(String codAs);

    void refreshAuthor(String codAu, String name);

    void refreshSubject(String codAs, String description);
}

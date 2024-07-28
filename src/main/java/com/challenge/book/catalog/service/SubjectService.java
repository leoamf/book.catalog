package com.challenge.book.catalog.service;

import java.util.List;
import com.challenge.book.catalog.dto.SubjectRequestDTO;
import com.challenge.book.catalog.model.Subject;

public interface SubjectService {

    Subject create(SubjectRequestDTO subject);

    Subject update(String codAs, SubjectRequestDTO subject);

    Subject get(String codAs);

    List<Subject> getAll();

    void delete(String codAs);

}

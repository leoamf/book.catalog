package com.challenge.book.catalog.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.challenge.book.catalog.dto.SubjectRequestDTO;
import com.challenge.book.catalog.exception.RecordNotFoundException;
import com.challenge.book.catalog.model.Subject;
import com.challenge.book.catalog.repository.SubjectRepository;
import com.challenge.book.catalog.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Subject create(SubjectRequestDTO request) {
        return subjectRepository.save(SubjectRequestDTO.toSubject(request));
    }

    @Override
    public Subject update(String codAs, SubjectRequestDTO request) {
        Subject record = this.get(codAs);
        record = SubjectRequestDTO.toSubject(request);
        record.setCodAs(codAs);
        return subjectRepository.save(record);
    }

    @Override
    public Subject get(String codAs) {
        return subjectRepository.findById(codAs).orElseThrow(() -> new RecordNotFoundException(codAs));
    }

    @Override
    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    @Override
    public void delete(String codAs) {
        if (this.get(codAs) != null)
            subjectRepository.deleteById(codAs);
    }

}

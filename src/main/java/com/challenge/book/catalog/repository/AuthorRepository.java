package com.challenge.book.catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.challenge.book.catalog.model.Author;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {

}

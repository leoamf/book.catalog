package com.challenge.book.catalog.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document(value = "Book")
public class Book {
    public static final String ENTITY_NAME = "Livro";

    @Id
    private String codL;

    @Field("titulo")
    private String title;

    @Field("editora")
    private String publishingCompany;

    @Field("edicao")
    private Integer edition;

    @Field("anoPublicacao")
    private String releaseYear;

    @Field("Precos")
    private List<PriceChannel> priceByChannels;

    @Field("Autores")
    private List<Author> authors;

    @Field("Assuntos")
    private List<Subject> subjects;
}

package com.challenge.book.catalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(value = "ViewLivro")
public class ViewBook {
    @Id
    private String id;

    @Field("titulo")
    private String title;

    @Field("edicao")
    private String edition;

    @Field("anoPublicacao")
    private String releaseYear;

    @Field("AutoresLivros")
    private String author;

    @Field("AssuntoLivros")
    private String subject;

}

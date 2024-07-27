package com.challenge.book.catalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(value = "Autor")
public class Author {
    @Id
    private String codAu;

    @Field("Nome")
    private String name;
}

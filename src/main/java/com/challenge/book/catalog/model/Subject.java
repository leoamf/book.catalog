package com.challenge.book.catalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(value = "Assunto")
public class Subject {
    @Id
    private String codAs;

    @Field("Descricao")
    private String description;
}

package com.challenge.book.catalog.model;

import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceChannel {

    @Field("Canal")
    private String channel;

    @Field("PrecoLivro")
    private double price;
}
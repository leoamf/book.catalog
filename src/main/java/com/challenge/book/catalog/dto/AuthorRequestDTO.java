package com.challenge.book.catalog.dto;

import com.challenge.book.catalog.model.Author;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorRequestDTO {

    @NotEmpty(message = "The name is required.")
    @Size(min = 1, max = 40, message = "The length of name must be between 1 and 40 characters.")
    private String name;

    public static Author toAuthor(AuthorRequestDTO request) {
        return Author.builder()
                .name(request.getName())
                .build();
    }
}

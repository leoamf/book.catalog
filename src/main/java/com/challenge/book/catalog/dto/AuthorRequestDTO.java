package com.challenge.book.catalog.dto;

import com.challenge.book.catalog.model.Author;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequestDTO {

    @NotEmpty(message = "O nome é obrigatorio.")
    @Size(min = 1, max = 40, message = "O tamanho do campo nome deve ser de 1 a 40 caracteres.")
    private String name;

    public static Author toAuthor(AuthorRequestDTO request) {
        return Author.builder()
                .name(request.getName())
                .build();
    }
}

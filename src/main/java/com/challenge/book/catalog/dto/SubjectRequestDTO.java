package com.challenge.book.catalog.dto;

import com.challenge.book.catalog.model.Subject;
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
public class SubjectRequestDTO {

    @NotEmpty(message = "A descrição é obrigatoria.")
    @Size(min = 1, max = 20, message = "O tamanho do campo descrição deve ser de 1 a 20 caracteres.")
    private String description;

    public static Subject toSubject(SubjectRequestDTO request) {
        return Subject.builder()
                .description(request.getDescription())
                .build();
    }
}

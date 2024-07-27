package com.challenge.book.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorObjectValueRequestDTO {
    @NotNull(message = "O codigo é obrigatorio.")
    @NotEmpty(message = "O codigo é obrigatorio.")
    private String codAu;
}

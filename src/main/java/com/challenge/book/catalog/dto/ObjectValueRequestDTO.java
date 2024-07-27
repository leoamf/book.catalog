package com.challenge.book.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObjectValueRequestDTO {
    @NotEmpty(message = "O id é obrigatorio.")
    private String id;
}

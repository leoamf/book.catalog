package com.challenge.book.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ObjectValueRequestDTO {
    @NotEmpty(message = "The id is required.")
    private String id;
}

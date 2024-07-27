package com.challenge.book.catalog.dto;

import com.challenge.book.catalog.model.Subject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectRequestDTO {

    @NotEmpty(message = "The description is required.")
    @Size(min = 1, max = 20, message = "The length of description must be between 1 and 20 characters.")
    private String description;

    public static Subject toSubject(SubjectRequestDTO request) {
        return Subject.builder()
                .description(request.getDescription())
                .build();
    }
}

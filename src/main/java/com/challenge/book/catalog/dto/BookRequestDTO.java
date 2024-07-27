package com.challenge.book.catalog.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.validator.constraints.Range;
import com.challenge.book.catalog.model.Author;
import com.challenge.book.catalog.model.Book;
import com.challenge.book.catalog.model.PriceChannel;
import com.challenge.book.catalog.model.Subject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRequestDTO {

    @NotBlank(message = "Title cannot be null or blank")
    @NotEmpty(message = "The Title is required.")
    @Size(min = 1, max = 40, message = "The length of Title must be between 1 and 40 characters.")
    private String title;

    @NotBlank(message = "Publishing Company cannot be null or blank")
    @NotEmpty(message = "The Publishing Company is required.")
    @Size(min = 1, max = 40, message = "The length of Publishing Company must be between 1 and 40 characters.")
    private String publishingCompany;

    @NotNull(message = "The edition is required.")
    @Range(min = 1, message = "The edition is required.")
    private Integer edition;

    @NotEmpty(message = "The Release Year is required.")
    @Size(min = 4, max = 4, message = "The length of Release Year must be 4 characters.")
    @Pattern(regexp = "[\\d]+", message = "Release Year must contain only numbers")
    private String releaseYear;

    private List<PriceChannelRequestDTO> priceByChannels;

    private List<ObjectValueRequestDTO> authors;

    private List<ObjectValueRequestDTO> subjects;

    public static Book toBook(BookRequestDTO request) {
        return Book.builder()
                .title(request.getTitle())
                .edition(request.getEdition())
                .publishingCompany(request.getPublishingCompany())
                .releaseYear(request.getReleaseYear())
                .priceByChannels(request.toPriceByChannels(request.getPriceByChannels()))
                .authors(request.toAuthors(request.getAuthors()))
                .subjects(request.toSubjects(request.getSubjects()))
                .build();
    }

    private List<PriceChannel> toPriceByChannels(List<PriceChannelRequestDTO> request) {
        if (request == null || request.isEmpty()) {
            return Collections.emptyList();
        }
        return request
                .stream()
                .map(PriceChannelRequestDTO::toPriceChannel)
                .collect(Collectors.toList());
    }

    private List<Author> toAuthors(List<ObjectValueRequestDTO> request) {
        if (request == null || request.isEmpty()) {
            return Collections.emptyList();
        }
        return request
                .stream()
                .map(r -> Author.builder().codAu(r.getId()).build())
                .collect(Collectors.toList());
    }

    private List<Subject> toSubjects(List<ObjectValueRequestDTO> request) {
        if (request == null || request.isEmpty()) {
            return Collections.emptyList();
        }
        return request
                .stream()
                .map(r -> Subject.builder().codAs(r.getId()).build())
                .collect(Collectors.toList());
    }
}
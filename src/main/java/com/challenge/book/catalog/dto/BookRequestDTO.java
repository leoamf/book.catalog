package com.challenge.book.catalog.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.challenge.book.catalog.model.Author;
import com.challenge.book.catalog.model.Book;
import com.challenge.book.catalog.model.PriceChannel;
import com.challenge.book.catalog.model.Subject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {

    @NotEmpty(message = "O titulo é obrigatorio.")
    @Size(min = 1, max = 40, message = "O tamanho do campo titulo deve ser de 1 a 40 caracteres.")
    private String title;

    @NotEmpty(message = "A editora é obrigatorio.")
    @Size(min = 1, max = 40, message = "O tamanho do campo editora deve ser de 1 a 40 caracteres.")
    private String publishingCompany;

    @NotNull(message = "A edição é obrigatorio.")
    private Integer edition;

    @NotEmpty(message = "O ano de publicação é obrigatorio.")
    @Size(min = 4, max = 4, message = "O tamanho do campo ano de publicação deve ser de 4 caracteres.")
    @Pattern(regexp = "[\\d{4}]+", message = "O ano de publicação só deve conter numeros")
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
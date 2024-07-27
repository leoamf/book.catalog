package com.challenge.book.catalog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.challenge.book.catalog.dto.AuthorRequestDTO;
import com.challenge.book.catalog.exception.RecordReferenceFoundException;
import com.challenge.book.catalog.model.Author;
import com.challenge.book.catalog.model.Book;
import com.challenge.book.catalog.service.AuthorService;
import com.challenge.book.catalog.service.BookService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/author")
@Tag(name = "APIs of author", description = "Collections APIs of author")
@Validated
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService,
            BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a Author", description = "Save the new author in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The author was created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
            @ApiResponse(responseCode = "400", description = "The input data has problems", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "Author not found", content = { @Content() })
    })
    public ResponseEntity<Author> create(@RequestBody @Valid AuthorRequestDTO request) {
        return new ResponseEntity<>(authorService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{codAu}")
    @Operation(summary = "Get the author", description = "Get the author from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
            @ApiResponse(responseCode = "400", description = "The input data has problems", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "Author not found", content = { @Content() })
    })

    public ResponseEntity<Author> get(@PathVariable String codAu) {
        return ResponseEntity.ok(authorService.get(codAu));
    }

    @GetMapping("/all")
    @Operation(summary = "Get list of all authors", description = "Get list of all authors from database")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(authorService.gettAll());
    }

    @PutMapping(value = "/{codAu}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update the author", description = "Update the author from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "The author was accepted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
            @ApiResponse(responseCode = "400", description = "The input data has problems", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "Author not found", content = { @Content() })
    })
    public ResponseEntity<Author> update(@PathVariable String codAu, @Valid @RequestBody AuthorRequestDTO request) {
        Author record = authorService.update(codAu, request);
        bookService.refreshAuthor(record.getCodAu(), record.getName());
        return new ResponseEntity<>(record, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{codAu}")
    @Operation(summary = "Delete the author", description = "Delete the author from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "The author was deleted", content = { @Content() }),
            @ApiResponse(responseCode = "400", description = "The input data has problems", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "Author not found", content = { @Content() })
    })
    public ResponseEntity<Void> delete(@PathVariable String codAu) {
        if (bookService.existAuthor(codAu))
            throw new RecordReferenceFoundException(Book.ENTITY_NAME, codAu);
        authorService.delete(codAu);
        return ResponseEntity.accepted().build();
    }
}

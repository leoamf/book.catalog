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

import com.challenge.book.catalog.dto.SubjectRequestDTO;
import com.challenge.book.catalog.exception.RecordReferenceFoundException;
import com.challenge.book.catalog.model.Author;
import com.challenge.book.catalog.model.Book;
import com.challenge.book.catalog.model.Subject;
import com.challenge.book.catalog.service.BookService;
import com.challenge.book.catalog.service.SubjectService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/subject")
@Tag(name = "APIs of subject", description = "Collections APIs of subject")
@Validated
public class SubjectController {

    private final SubjectService subjectService;
    private final BookService bookService;

    public SubjectController(SubjectService subjectService,
            BookService bookService) {
        this.subjectService = subjectService;
        this.bookService = bookService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a subject", description = "Save the new subject in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The subject was created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
            @ApiResponse(responseCode = "400", description = "The input data has problems", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "subject not found", content = { @Content() })
    })
    public ResponseEntity<Subject> create(@RequestBody @Valid SubjectRequestDTO request) {
        return new ResponseEntity<>(subjectService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{codAs}")
    @Operation(summary = "Get the subject", description = "Get the subject from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
            @ApiResponse(responseCode = "400", description = "The input data has problems", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "Subject not found", content = { @Content() })
    })

    public ResponseEntity<Subject> get(@PathVariable String codAs) {
        return ResponseEntity.ok(subjectService.get(codAs));
    }

    @GetMapping("/all")
    @Operation(summary = "Get list of all subjects", description = "Get list of all subjects from database")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<Subject>> getAll() {
        return ResponseEntity.ok(subjectService.gettAll());
    }

    @PutMapping(value = "/{codAs}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update the subject", description = "Update the subject from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "The update subject was accepted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)) }),
            @ApiResponse(responseCode = "400", description = "The input data has problems", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "Subject not found", content = { @Content() })
    })
    public ResponseEntity<Subject> update(@PathVariable String codAs, @Valid @RequestBody SubjectRequestDTO request) {
        Subject record = subjectService.update(codAs, request);
        bookService.refreshSubject(record.getCodAs(), record.getDescription());
        return new ResponseEntity<>(record, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{codAs}")
    @Operation(summary = "Delete the subject", description = "Delete the subject from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "The subject was deleted", content = { @Content() }),
            @ApiResponse(responseCode = "400", description = "The input data has problems", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "Subject not found", content = { @Content() })
    })
    public ResponseEntity<Void> delete(@PathVariable String codAs) {

        if (bookService.existSubject(codAs))
            throw new RecordReferenceFoundException(Book.ENTITY_NAME, codAs);

        subjectService.delete(codAs);
        return ResponseEntity.accepted().build();
    }
}

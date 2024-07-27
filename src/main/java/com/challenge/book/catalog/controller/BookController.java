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
import com.challenge.book.catalog.dto.BookRequestDTO;
import com.challenge.book.catalog.model.Book;
import com.challenge.book.catalog.service.BookService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/book")
@Tag(name = "APIs of book", description = "Collections APIs of book")
@Validated
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a book", description = "Save the new book in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The book was created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "The input data has problems", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "Book not found", content = { @Content() })
    })
    public ResponseEntity<?> create(@RequestBody @Valid BookRequestDTO request) {
        return new ResponseEntity<>(bookService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{codL}")
    @Operation(summary = "Get the book", description = "Get the book from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "The input data has problems", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "Book not found", content = { @Content() })
    })

    public ResponseEntity<Book> get(@PathVariable String codL) {
        return ResponseEntity.ok(bookService.get(codL));
    }

    @GetMapping("/all")
    @Operation(summary = "Get list of all books", description = "Get list of all books from database")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookService.gettAll());
    }

    @PutMapping(value = "/{codL}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update the book", description = "Update the book from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "The update book was accepted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "The input data has problems", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "Book not found", content = { @Content() })
    })
    public ResponseEntity<Book> update(@PathVariable String codL, @Valid @RequestBody BookRequestDTO request) {
        return new ResponseEntity<>(bookService.update(codL, request), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{codL}")
    @Operation(summary = "Delete the book", description = "Delete the book from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "The book was deleted", content = { @Content() }),
            @ApiResponse(responseCode = "400", description = "The input data has problems", content = { @Content() }),
            @ApiResponse(responseCode = "404", description = "Book not found", content = { @Content() })
    })
    public ResponseEntity<Void> delete(@PathVariable String codL) {
        bookService.delete(codL);
        return ResponseEntity.accepted().build();

    }
}

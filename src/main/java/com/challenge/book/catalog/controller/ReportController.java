package com.challenge.book.catalog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/report")
@Tag(name = "APIs of report", description = "Export report")
public class ReportController {

    @GetMapping()
    @Operation(summary = "Get the report", description = "Get the report from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK") })
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(true);
    }
}

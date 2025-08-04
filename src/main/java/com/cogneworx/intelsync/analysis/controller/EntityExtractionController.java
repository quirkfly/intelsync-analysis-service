package com.cogneworx.intelsync.analysis.controller;

import com.cogneworx.intelsync.analysis.service.EntityExtractionService;
import com.cogneworx.intelsync.analysis.service.GraphStorageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
public class EntityExtractionController {

    private final EntityExtractionService entityExtractionService;
    private final GraphStorageService graphStorageService;

    public EntityExtractionController(EntityExtractionService entityExtractionService,
                                      GraphStorageService graphStorageService) {
        this.entityExtractionService = entityExtractionService;
        this.graphStorageService = graphStorageService;
    }

    /**
     * POST /api/analysis/entities
     * 
     * Accepts raw text input and returns extracted named entities.
     */
    @PostMapping("/entities")
    public ResponseEntity<Map<String, List<String>>> extractEntities(@RequestBody String text) {
        List<String> persons = entityExtractionService.extractPersons(text);

        // In future: add orgs, locations, etc.

        return ResponseEntity.ok(Map.of(
                "persons", persons
        ));
    }

    @PostMapping("/save-entities")
    public ResponseEntity<Map<String, String>> saveEntities(
            @RequestParam String caseId,
            @RequestBody List<String> persons) {

        if (caseId == null || caseId.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "caseId is required"));
        }
        if (persons == null || persons.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "person list cannot be empty"));
        }

        graphStorageService.storeEntities(persons, caseId);

        return ResponseEntity.ok(Map.of("message", "Entities saved successfully"));
    }

    @GetMapping("/graph")
    public ResponseEntity<Map<String, List<?>>> getGraph(@RequestParam String caseId) {
        if (caseId == null || caseId.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", List.of("caseId is required")));
        }

        Map<String, List<?>> graph = graphStorageService.fetchGraphForCase(caseId);
        return ResponseEntity.ok(graph);
    }
}

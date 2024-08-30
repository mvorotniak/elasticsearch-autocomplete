package com.demo.elasticsearchautocomplete.controller;

import com.demo.elasticsearchautocomplete.service.EsAutocompleteWordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class EsAutocompleteController {
    
    private final EsAutocompleteWordsService service;
    
    @PostMapping("/populate")
    public ResponseEntity<String> populateElasticsearch() {
        Integer numberOfWords = service.populateEsWithWords();
        return ResponseEntity.ok("Successfully populated Elasticsearch with %s words".formatted(numberOfWords));
    }
    
    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> findMatches(@RequestParam String word) {
        List<String> wordsMatches = service.findMatches(word);
        return ResponseEntity.ok(wordsMatches);
    }
}

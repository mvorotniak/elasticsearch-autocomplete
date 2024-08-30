package com.demo.elasticsearchautocomplete.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EnglishWordsService {
    
    @Value("${english.words.url}")
    private String englishWordsUrl;
    
    private final RestTemplate restTemplate;
    
    public List<String> getEnglishWords() {
        String txtResponse = restTemplate.getForObject(englishWordsUrl, String.class);
        
        return Optional.ofNullable(txtResponse)
                .map(r -> r.split("\\r?\\n"))
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }
}

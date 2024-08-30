package com.demo.elasticsearchautocomplete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
public class ElasticsearchAutocompleteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchAutocompleteApplication.class, args);
    }

}

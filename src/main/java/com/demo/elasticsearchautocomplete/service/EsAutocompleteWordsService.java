package com.demo.elasticsearchautocomplete.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.demo.elasticsearchautocomplete.entity.WordEntity;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EsAutocompleteWordsService {

  private final EnglishWordsService englishWordsService;

  private final ElasticsearchRestTemplate elasticsearchRestTemplate;

  public Integer populateEsWithWords() {
    List<WordEntity> wordEntities = englishWordsService.getEnglishWords()
      .stream()
      .map(this::toWord)
      .toList();

    log.info("Going to save [{}] english words to Elasticsearch...", wordEntities.size());

    List<IndexQuery> indexQueries = wordEntities.stream()
      .map(wordEntity -> new IndexQueryBuilder()
        .withId(wordEntity.getId())
        .withObject(wordEntity)
        .build())
      .toList();

    Lists.partition(indexQueries, 10_000)
      .forEach(q -> {
        log.info("Storing words in elasticsearch in a batch...");
        try {
          elasticsearchRestTemplate.bulkIndex(q, WordEntity.class);
        } catch (Exception e) {
          log.warn("Issue occurred: [{}]", e.getMessage());
        }
      });

    log.info("Successfully saved all words to Elasticsearch.");

    return wordEntities.size();
  }

  public List<String> findMatches(String word) {
    if (Objects.nonNull(word)) {
      Query searchQuery = new NativeSearchQueryBuilder()
        .withQuery(QueryBuilders.matchQuery("word", word)
          .fuzziness("AUTO")
          .prefixLength(3))
        .build();

      SearchHits<WordEntity> searchHits = elasticsearchRestTemplate.search(searchQuery, WordEntity.class);
      List<String> words = searchHits.stream()
        .map(SearchHit::getContent)
        .map(WordEntity::getWord)
        .toList();

      log.info("Found {} matches for word [{}]", words, word);

      return words;
    }

    return Collections.emptyList();
  }

  private WordEntity toWord(String word) {
    return WordEntity.builder()
      .id(UUID.randomUUID().toString())
      .word(word)
      .build();
  }
}

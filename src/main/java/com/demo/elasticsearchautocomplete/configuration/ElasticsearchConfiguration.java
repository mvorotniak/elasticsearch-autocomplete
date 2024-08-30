package com.demo.elasticsearchautocomplete.configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Configuration
public class ElasticsearchConfiguration {

  @Bean
  public RestHighLevelClient restHighLevelClient() {
    ClientConfiguration clientConfiguration = ClientConfiguration.builder()
      .connectedTo("localhost:9200")
      .withSocketTimeout(Duration.of(2, ChronoUnit.MINUTES))
      .build();

    return RestClients.create(clientConfiguration).rest();
  }

  @Bean
  public ElasticsearchRestTemplate elasticsearchRestTemplate(RestHighLevelClient restHighLevelClient) {
    return new ElasticsearchRestTemplate(restHighLevelClient);
  }
}

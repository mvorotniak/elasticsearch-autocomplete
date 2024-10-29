package com.demo.elasticsearchautocomplete.controller;

import com.demo.elasticsearchautocomplete.service.EsAutocompleteWordsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EsAutocompleteControllerTest {

	@Mock
	private EsAutocompleteWordsService service;

	@InjectMocks
	private EsAutocompleteController controller;

	@Test
	void findMatches() {
		when(this.service.findMatches(any())).thenReturn(List.of("word"));

		final ResponseEntity<List<String>> response = this.controller.findMatches("wo");

		assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.OK.value()));
		assertThat(response.getBody()).hasSize(1);
		assertThat(response.getBody().get(0)).isEqualTo("word");
	}
}
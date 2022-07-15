package com.fiserv.wiki.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fiserv.wiki.exception.WikiException;
import com.fiserv.wiki.resource.WikiResource;

@ExtendWith(MockitoExtension.class)
class WikiServiceTest {

    @InjectMocks
    private WikiService wikiService;

    @Mock
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new JsonMapper();

    @Test
    void shouldReturnCorrectNumberOfOccurrences() throws JsonProcessingException {
        var topic = "test";
        var responseJson = "{\"parse\":{\"title\":\"Test\",\"pageid\":24768,\"text\":{\"*\":\"test_test\"}}}";
        var responseBody = objectMapper.readValue(responseJson, WikiResource.class);
        var expectedOccurrences = 2;

        when(restTemplate.getForEntity(any(), any(), any(Map.class))).thenReturn(ResponseEntity.ok(responseBody));
        var result = wikiService.countOccurrences(topic);
        assertEquals(expectedOccurrences, result);
    }

    @Test
    void shouldThrowWikiException() throws JsonProcessingException {
        var topic = "test";
        var responseJson = "{\"error\":{\"code\":\"missingtitle\",\"info\":\"The page you specified doesn't exist.\"}}";
        var responseBody = objectMapper.readValue(responseJson, WikiResource.class);

        when(restTemplate.getForEntity(any(), any(), any(Map.class))).thenReturn(ResponseEntity.ok(responseBody));
        assertThrows(WikiException.class, () -> wikiService.countOccurrences(topic));
    }
}

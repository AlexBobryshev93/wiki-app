package com.fiserv.wiki.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fiserv.wiki.exception.MissingTitleException;
import com.fiserv.wiki.exception.WikiException;
import com.fiserv.wiki.resource.WikiResource;

import lombok.RequiredArgsConstructor;

/**
 * Service that allows to call Wikipedia API.
 *
 * @author Oleksandr Bobryshev
 */
@Component
@RequiredArgsConstructor
public class WikiService {

    @Value("${application.wiki.url}")
    private String wikiUrl;

    private static final String MISSING_TITLE_ERROR_CODE = "missingtitle";

    private final RestTemplate restTemplate;

    /**
     * Method counts how many times a topic of the article in Wikipedia occurs in the article.
     *
     * @param topic a Wikipedia topic in which the calculation is performed
     * @return the number of occurrences
     * @throws WikiException in case or an error response from Wikipedia API
     */
    public int countOccurrences(String topic) {
        var params = Map.of("page", topic);
        var responseBody = restTemplate.getForEntity(wikiUrl, WikiResource.class, params).getBody();
        if (responseBody.getError() != null) {
            if (responseBody.getError().getCode().equals(MISSING_TITLE_ERROR_CODE)) {
                throw new MissingTitleException();
            }
            throw new WikiException(responseBody.getError().getInfo());
        }
        var text = responseBody.getParse().getText().getHtml();
        return StringUtils.countOccurrencesOf(text, topic);
    }
}

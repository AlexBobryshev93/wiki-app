package com.fiserv.wiki.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class TextResource {
    @JsonProperty("*")
    private String html;
}

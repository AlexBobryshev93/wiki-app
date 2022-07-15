package com.fiserv.wiki.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ParseResource {

    private String title;

    @JsonProperty("pageid")
    private String pageId;

    private TextResource text;
}

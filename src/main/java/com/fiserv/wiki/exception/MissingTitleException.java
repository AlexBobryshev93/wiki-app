package com.fiserv.wiki.exception;

public class MissingTitleException extends WikiException {

    public MissingTitleException() {
        super("The page you specified doesn't exist.");
    }
}

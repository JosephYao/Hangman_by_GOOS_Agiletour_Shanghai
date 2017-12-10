package com.odde.hangman.domain;

import org.springframework.stereotype.Component;

@Component
public class WordProvider {

    public String getWord() {
        return "tuesday";
    }
}

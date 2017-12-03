package com.odde.hangman.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RandomWord {

    private int id;
    private String word;

    public String getWord() {
        return word.toLowerCase();
    }
}

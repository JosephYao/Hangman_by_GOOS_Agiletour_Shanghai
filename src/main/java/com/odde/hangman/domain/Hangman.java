package com.odde.hangman.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.joining;

@Component
@RequestScope
public class Hangman {

    private final String word;
    private int tries;
    private String usedChars;

    @Autowired
    public Hangman(HttpServletRequest request, WordWarehouse wordWarehouse) {
        initUsedChars(request);
        initTries(request);
        word = wordWarehouse.getWord();
    }

    private void initTries(HttpServletRequest request) {
        if (triesOf(request) == null)
            tries = 12;
        else
            tries = parseInt(triesOf(request));
    }

    private void initUsedChars(HttpServletRequest request) {
        if (usedCharsOf(request) == null)
            usedChars = "aeiou";
        else
            usedChars = usedCharsOf(request);
    }

    private String usedCharsOf(HttpServletRequest request) {
        return request.getParameter("usedChars");
    }

    private String triesOf(HttpServletRequest request) {
        return request.getParameter("tries");
    }

    public int tries() {
        return tries;
    }

    public void input(String character) {
        if (isCharNotInWord(character) || isCharUsed(character))
            tries--;

        if (!isCharUsed(character))
            usedChars += character;
    }

    private boolean isCharUsed(String character) {
        return usedChars.contains(character);
    }

    private boolean isCharNotInWord(String character) {
        return !word.contains(character);
    }

    public int length() {
        return word.length();
    }

    public String usedChars() {
        return usedChars;
    }

    public String discovered() {
        return word.chars().mapToObj(this::discoveredChar).collect(joining());
    }

    private String discoveredChar(int intChar) {
        String character = String.valueOf((char) intChar);
        if ("aeiou".contains(character))
            return character;
        else
            return "_";
    }
}

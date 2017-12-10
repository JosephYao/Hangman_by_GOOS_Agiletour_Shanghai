package com.odde.hangman.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Component
@RequestScope
public class Hangman {

    private int tries;
    private String usedChars;
    private String word;

    @Autowired
    public Hangman(HttpServletRequest request, WordProvider wordProvider) {
        initUsedChars(request);
        initTries(request);
        this.word = wordProvider.getWord();
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
        return this.word.chars().mapToObj(i -> convertOneChar((char) i)).collect(Collectors.joining());
    }

    private String convertOneChar(char i) {
        if ("aeiou".indexOf(i) >= 0) {
            return String.valueOf(i);
        } else {
            return "_";
        }
    }
}

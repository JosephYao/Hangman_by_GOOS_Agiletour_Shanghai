package com.odde.hangman.controller;

import com.nitorcreations.junit.runners.NestedRunner;
import com.odde.hangman.domain.Hangman;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;

@RunWith(NestedRunner.class)
public class HangmanControllerTest {

    private static final int ANY_LENGTH = 10;
    private static final int ANY_TRIES = 100;
    private static final String ANY_USED_CHARS = "usedchar";
    private static final String ANY_DISCOVERED = "discovered";
    private static final String ANYWORD = "anyword";
    Hangman mockHangman = mock(Hangman.class);
    HangmanController controller = new HangmanController(mockHangman);
    Model mockModel = mock(Model.class);

    private void givenGameStateIs(int tries, int length, String usedChars, String discovered, String word) {
        when(mockHangman.tries()).thenReturn(tries);
        when(mockHangman.length()).thenReturn(length);
        when(mockHangman.usedChars()).thenReturn(usedChars);
        when(mockHangman.discovered()).thenReturn(discovered);
        when(mockHangman.word()).thenReturn(word);
    }

    private void verifyAddAttributeForView(int tries, int length, String usedChars, String discovered, String word) {
        verify(mockModel).addAttribute("tries", tries);
        verify(mockModel).addAttribute("length", length);
        verify(mockModel).addAttribute("usedChars", usedChars);
        verify(mockModel).addAttribute("discovered", discovered);
        verify(mockModel).addAttribute("word", word);
    }

    public class Input {

        @Test
        public void should_set_game_state_when_input_a_char() {
            givenGameStateIs(ANY_TRIES, ANY_LENGTH, ANY_USED_CHARS, ANY_DISCOVERED, ANYWORD);

            input("a");

            verifyAddAttributeForView(ANY_TRIES, ANY_LENGTH, ANY_USED_CHARS, ANY_DISCOVERED, ANYWORD);
        }

        @Test
        public void should_invoke_hangman_input_when_a_char() {
            givenGameStateIs(ANY_TRIES, ANY_LENGTH, ANY_USED_CHARS, ANY_DISCOVERED, ANYWORD);

            input("a");

            verify(mockHangman).input("a");
        }

        private void input(String character) {
            controller.input(mockModel, character);
        }

    }

    public class Home {

        @Test
        public void should_set_game_state_when_start_game() {
            givenGameStateIs(ANY_TRIES, ANY_LENGTH, ANY_USED_CHARS, ANY_DISCOVERED, ANYWORD);

            home();

            verifyAddAttributeForView(ANY_TRIES, ANY_LENGTH, ANY_USED_CHARS, ANY_DISCOVERED, ANYWORD);
        }

        private String home() {
            return controller.home(mockModel);
        }

    }

}
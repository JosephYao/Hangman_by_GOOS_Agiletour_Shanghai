package com.odde.hangman.domain;

import com.nitorcreations.junit.runners.NestedRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(NestedRunner.class)
public class HangmanTest {

    HttpServletRequest stubHttpServletRequest = mock(HttpServletRequest.class);
    WordWarehouse stubWordWarehouse = mock(WordWarehouse.class);

    private Hangman createHangman() {
        return new Hangman(stubHttpServletRequest, stubWordWarehouse);
    }

    private void givenRequestWithGameState(String tries, String usedChars) {
        when(stubHttpServletRequest.getParameter("tries")).thenReturn(tries);
        when(stubHttpServletRequest.getParameter("usedChars")).thenReturn(usedChars);
    }

    private Hangman hangmanWhenGameJustStarted() {
        givenRequestWithGameState(null, null);
        givenWord("tuesday");
        return createHangman();
    }

    private void givenWord(String tuesday) {
        when(stubWordWarehouse.getWord()).thenReturn(tuesday);
    }

    public class GameStateSetByRequest {

        @Test
        public void should_be_from_request_if_set() {
            givenRequestWithGameState("10", "aeiouc");

            assertThat(createHangman().tries()).isEqualTo(10);
            assertThat(createHangman().usedChars()).isEqualTo("aeiouc");
        }

        @Test
        public void should_be_initial_state_when_start_game() {
            givenRequestWithGameState(null, null);
            givenWord("tuesday");

            assertThat(createHangman().tries()).isEqualTo(12);
            assertThat(createHangman().length()).isEqualTo(7);
            assertThat(createHangman().usedChars()).isEqualTo("aeiou");
        }
    }

    public class UsedCharsChangeByInput {

        Hangman hangman = hangmanWhenGameJustStarted();

        @Test
        public void should_append_character_when_input_is_not_used_yet() {
            hangman.input("d");

            assertThat(hangman.usedChars()).isEqualTo("aeioud");
        }

        @Test
        public void should_not_append_character_when_input_is_already_used() {
            hangman.input("a");

            assertThat(hangman.usedChars()).isEqualTo("aeiou");
        }
    }

    public class TriesChangeByInput {

        Hangman hangman = hangmanWhenGameJustStarted();

        @Test
        public void should_decrease_when_input_a_vowel() {
            hangman.input("i");

            assertThat(hangman.tries()).isEqualTo(11);
        }

        @Test
        public void should_decrease_when_input_a_wrong_consonant() {
            hangman.input("f");

            assertThat(hangman.tries()).isEqualTo(11);
        }

        @Test
        public void should_not_change_when_input_a_correct_consonant() {
            hangman.input("t");

            assertThat(hangman.tries()).isEqualTo(12);
        }

        @Test
        public void should_decrease_when_input_a_character_already_used() {
            givenRequestWithGameState("12", "aeiout");
            hangman = createHangman();

            hangman.input("t");

            assertThat(hangman.tries()).isEqualTo(11);
        }

    }

    public class DiscoveredWhenGameStarted {

        @Test
        public void should_be_itself_when_word_is_a_vowel() {
            Hangman hangman = givenGameStartedWithWord("a");

            assertThat(hangman.discovered()).isEqualTo("a");
        }

        @Test
        public void should_be_underscore_when_word_is_a_consonant() {
            Hangman hangman = givenGameStartedWithWord("c");

            assertThat(hangman.discovered()).isEqualTo("_");
        }

        @Test
        public void should_be_itself_when_word_is_two_vowels() {
            Hangman hangman = givenGameStartedWithWord("ai");

            assertThat(hangman.discovered()).isEqualTo("ai");
        }

        private Hangman givenGameStartedWithWord(String word) {
            givenRequestWithGameState(null, null);
            givenWord(word);
            return createHangman();
        }

    }

}
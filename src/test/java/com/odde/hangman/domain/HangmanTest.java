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

    private Hangman createHangman() {
        return new Hangman(stubHttpServletRequest, new WordProvider());
    }

    private void givenRequestWithGameState(String tries, String usedChars) {
        when(stubHttpServletRequest.getParameter("tries")).thenReturn(tries);
        when(stubHttpServletRequest.getParameter("usedChars")).thenReturn(usedChars);
    }

    private Hangman hangmanWhenGameJustStarted() {
        givenRequestWithGameState(null, null);
        return new Hangman(stubHttpServletRequest, new WordProvider());
    }

    private Hangman createHangmanByWord(String word) {
        givenRequestWithGameState(null, null);
        WordProvider wordProvider = mock(WordProvider.class);
        when(wordProvider.getWord()).thenReturn(word);
        return new Hangman(stubHttpServletRequest, wordProvider);
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

    public class DiscoveredInitialState {

        @Test
        public void should_display_vowels_at_initial() {
            Hangman hangman = createHangmanByWord("o");
            assertThat(hangman.discovered()).isEqualTo("o");
        }

        @Test
        public void should_display_underscore_at_initial_when_consonant() {
            Hangman hangman = createHangmanByWord("y");
            assertThat(hangman.discovered()).isEqualTo("_");
        }

        @Test
        public void should_display_two_vowels_at_initial() {
            Hangman hangman = createHangmanByWord("oo");
            assertThat(hangman.discovered()).isEqualTo("oo");
        }

        @Test
        public void should_display_a_word_at_initial() {
            Hangman hangman = createHangmanByWord("tuesday");
            assertThat(hangman.discovered()).isEqualTo("_ue__a_");
        }
    }

    public class DiscoveredChangedByInput {
        @Test
        public void should_changed_if_input_is_ok() {
            Hangman hangman = createHangmanByWord("ok");
            hangman.input("k");
            assertThat(hangman.discovered()).isEqualTo("ok");
        }
    }
}
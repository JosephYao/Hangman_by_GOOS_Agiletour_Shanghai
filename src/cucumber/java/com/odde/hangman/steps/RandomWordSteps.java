package com.odde.hangman.steps;

import com.odde.hangman.pages.HomePage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomWordSteps {

    @Autowired
    HomePage homePage;

    @Autowired
    HomePage anotherHomePage;

    private String firstPlayerWord;
    private String secondPlayerWord;

    @When("^two players both start game$")
    public void two_players_both_start_game() throws Throwable {
        homePage.open();
        firstPlayerWord = homePage.getWord();
        anotherHomePage.open();
        secondPlayerWord = anotherHomePage.getWord();
    }

    @Then("^they will get different words$")
    public void they_will_get_different_words() throws Throwable {
        assertThat(firstPlayerWord).isNotEqualTo(secondPlayerWord);
    }
}

Feature: Random word for the game

  Scenario: Two players will get different word when start game
    Given two words "first" and "second" in the word warehouse
    When two players both start game
    Then they will get different words

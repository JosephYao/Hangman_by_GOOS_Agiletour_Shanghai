package com.odde.hangman.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dreamhead.moco.HttpServer;
import com.github.dreamhead.moco.Runner;
import com.odde.hangman.domain.RandomWord;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.github.dreamhead.moco.Moco.httpServer;

@Component
@Scope("cucumber-glue")
public class RandomWordServer {

    HttpServer server = httpServer(12306);
    Runner runner = Runner.runner(server);

    public void start() {
        runner.start();
    }

    public void stop() {
        runner.stop();
    }

    public void response(String word) {
        try {
            server.response(jsonOf(word));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String jsonOf(String word) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(randomWordOf(word));
    }

    private RandomWord randomWordOf(String word) {
        RandomWord randomWord = new RandomWord();
        randomWord.setId(1);
        randomWord.setWord(word);
        return randomWord;
    }
}

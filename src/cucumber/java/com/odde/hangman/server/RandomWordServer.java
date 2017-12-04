package com.odde.hangman.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dreamhead.moco.*;
import com.github.dreamhead.moco.internal.SessionContext;
import com.github.dreamhead.moco.model.MessageContent;
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

    public void response(String... words) {
        final int[] wordIndex = {0};
        server.response(new ResponseHandler() {
            @Override
            public void writeToResponse(SessionContext context) {
                Request request = context.getRequest();
                Response response = context.getResponse();

                if (HttpRequest.class.isInstance(request) && MutableHttpResponse.class.isInstance(response)) {
                    MutableHttpResponse httpResponse = MutableHttpResponse.class.cast(response);
                    httpResponse.setContent(MessageContent.content(jsonOf(words[wordIndex[0]++])));
                }
            }

            @Override
            public ResponseHandler apply(MocoConfig config) {
                return this;
            }
        });
    }

    private String jsonOf(String word) {
        try {
            return new ObjectMapper().writeValueAsString(randomWordOf(word));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private RandomWord randomWordOf(String word) {
        RandomWord randomWord = new RandomWord();
        randomWord.setId(1);
        randomWord.setWord(word);
        return randomWord;
    }
}

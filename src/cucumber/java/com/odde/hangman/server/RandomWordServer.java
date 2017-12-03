package com.odde.hangman.server;

import com.github.dreamhead.moco.HttpServer;
import com.github.dreamhead.moco.Runner;
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
        server.response(word);
    }
}

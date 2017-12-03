package com.odde.hangman.steps;

import com.odde.hangman.server.RandomWordServer;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class Hooks {

    @Autowired
    private RandomWordServer server;

    @Before
    public void startServer() {
        server.start();
    }

    @After
    public void stopServer() {
        server.stop();
    }
}

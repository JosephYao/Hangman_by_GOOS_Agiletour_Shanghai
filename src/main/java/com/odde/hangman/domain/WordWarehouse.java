package com.odde.hangman.domain;

import org.springframework.stereotype.Component;
import retrofit2.Retrofit;

import java.io.IOException;

@Component
public class WordWarehouse {

    private static final String BASE_URL = "http://localhost:12306";
    private final RawRandomWordApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
            .create(RawRandomWordApi.class);

    public String getWord() {
        try {
            return api.randomWord().execute().body().string();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

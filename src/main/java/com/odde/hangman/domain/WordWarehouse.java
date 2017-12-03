package com.odde.hangman.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Component
public class WordWarehouse {

    @Value("${external.randomWordServer.url}")
    private String baseUrl;

    private RawRandomWordApi api() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(RawRandomWordApi.class);
    }

    public String getWord() {
        try {
            return api().randomWord().execute().body().getWord();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

package com.odde.hangman.domain;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RawRandomWordApi {

    @GET("words.json/randomWord?hasDictionaryDef=false&minCorpusCount=1000000&minDictionaryCount=20&minLength=7&maxLength=15&api_key=a2a73e7b926c924fad7001ca3111acd55af2ffabf50eb4ae5")
    Call<RandomWord> randomWord();
}

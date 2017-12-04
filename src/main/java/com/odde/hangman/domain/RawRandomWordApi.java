package com.odde.hangman.domain;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RawRandomWordApi {

    @GET("words.json/randomWord?hasDictionaryDef=false&minCorpusCount=1000000&minDictionaryCount=20&minLength=7&maxLength=15&api_key=467579d5f7d42543f20760aaf9e05d2e5187b8a9ed52e6078")
    Call<RandomWord> randomWord();
}

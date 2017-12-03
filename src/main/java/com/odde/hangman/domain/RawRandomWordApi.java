package com.odde.hangman.domain;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RawRandomWordApi {

    @GET("/")
    Call<ResponseBody> randomWord();
}

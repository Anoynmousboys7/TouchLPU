package com.example.myapplicationlpu;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {


    @POST("/api/v1/user/login")
    Call<SessionCoockie> getSessionCoockie(@Body PayloadFormat payloadFormat);


    @POST("/api/v1/user/me")
    Call<UserDetails> getUserDetails(@Body PayloadFormatwithCookie payloadFormatwithCookie);


    @POST("/api/v1/user/exams")
    Call<Exams> getExams(@Body PayloadFormatwithCookie payloadFormatwithCookie);

}

package com.unesc.mastersysapp.Services;

import com.unesc.mastersysapp.Models.Graduation;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GraduationService {
    @POST("graduations")
    Call<Graduation> create(@Body RequestBody object);
}

package com.unesc.mastersysapp.Services;

import com.unesc.mastersysapp.ModelList.RegistrationList;
import com.unesc.mastersysapp.Models.Matriculation;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MatriculationService {
    @POST("registrations")
    Call<Matriculation> create(@Body RequestBody object);

    @GET("registrations")
    Call<RegistrationList> listRegistrations();
}

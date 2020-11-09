package com.unesc.mastersysapp.Services;

import com.unesc.mastersysapp.Models.Plan;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PlanService {
    @POST("plans")
    Call<Plan> create(@Body RequestBody object);
}

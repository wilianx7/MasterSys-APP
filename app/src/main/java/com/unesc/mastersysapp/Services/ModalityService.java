package com.unesc.mastersysapp.Services;

import com.unesc.mastersysapp.ModelList.ModalityList;
import com.unesc.mastersysapp.Models.Modality;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ModalityService {
    @GET("modalities")
    Call<ModalityList> listModalities();

    @POST("modalities")
    Call<Modality> create(@Body RequestBody object);
}

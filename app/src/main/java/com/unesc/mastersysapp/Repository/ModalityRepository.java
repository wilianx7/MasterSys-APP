package com.unesc.mastersysapp.Repository;

import android.util.Log;

import com.unesc.mastersysapp.ModelList.ModalityList;
import com.unesc.mastersysapp.Services.ModalityService;
import com.unesc.mastersysapp.Services.Service;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModalityRepository {
    private Retrofit retrofit;
    ModalityList modalityList;

    public ModalityRepository() {
    }

    public ModalityList findAll() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ModalityService modalityService = retrofit.create(ModalityService.class);
        Call<ModalityList> request = modalityService.listModalities();


        request.enqueue(new Callback<ModalityList>() {
            @Override
            public void onResponse(Call<ModalityList> call, Response<ModalityList> response) {
                if(response.isSuccessful()) {
                    modalityList = response.body();
                }
            }

            @Override
            public void onFailure(Call<ModalityList> call, Throwable t) {
                Log.i("TAG", "ERRO " + t.getMessage());
            }

        });

        return modalityList;
    }

}

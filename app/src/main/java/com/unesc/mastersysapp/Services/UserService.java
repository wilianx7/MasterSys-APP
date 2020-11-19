package com.unesc.mastersysapp.Services;

import com.unesc.mastersysapp.ModelList.UserList;
import com.unesc.mastersysapp.Models.User;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    @GET("users")
    Call<UserList> listUsers();

    @POST("users")
    Call<User> create(@Body RequestBody object);
}

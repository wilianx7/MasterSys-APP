package com.unesc.mastersysapp.Services;

import com.unesc.mastersysapp.Models.Student;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface StudentService {
    @POST("students")
    Call<Student> create(@Body RequestBody object);
}

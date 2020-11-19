package com.unesc.mastersysapp.ModelList;

import com.google.gson.annotations.SerializedName;
import com.unesc.mastersysapp.Models.Matriculation;

import java.util.List;

public class RegistrationList {
    @SerializedName("data")
    public List<Matriculation> registrations;
}

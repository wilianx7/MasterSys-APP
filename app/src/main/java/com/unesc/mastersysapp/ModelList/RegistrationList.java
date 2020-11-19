package com.unesc.mastersysapp.ModelList;

import com.google.gson.annotations.SerializedName;
import com.unesc.mastersysapp.Models.Matriculation;

import java.io.Serializable;
import java.util.List;

public class RegistrationList implements Serializable {
    @SerializedName("data")
    public List<Matriculation> registrations;
}

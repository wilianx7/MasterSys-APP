package com.unesc.mastersysapp.ModelList;

import com.google.gson.annotations.SerializedName;
import com.unesc.mastersysapp.Models.Plan;

import java.io.Serializable;
import java.util.List;

public class PlanList implements Serializable {
    @SerializedName("data")
    public List<Plan> plans;
}

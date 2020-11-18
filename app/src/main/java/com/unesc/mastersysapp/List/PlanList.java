package com.unesc.mastersysapp.List;

import com.google.gson.annotations.SerializedName;
import com.unesc.mastersysapp.Models.Plan;

import java.util.List;

public class PlanList {
    @SerializedName("data")
    public List<Plan> plans;
}

package com.unesc.mastersysapp.ModelList;

import com.google.gson.annotations.SerializedName;
import com.unesc.mastersysapp.Models.Modality;

import java.io.Serializable;
import java.util.List;

public class ModalityList implements Serializable {
    @SerializedName("data")
    public List<Modality> modalities;
}

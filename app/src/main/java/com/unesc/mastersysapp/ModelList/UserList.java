package com.unesc.mastersysapp.ModelList;

import com.google.gson.annotations.SerializedName;
import com.unesc.mastersysapp.Models.User;

import java.io.Serializable;
import java.util.List;

public class UserList implements Serializable {
    @SerializedName("data")
    public List<User> users;
}

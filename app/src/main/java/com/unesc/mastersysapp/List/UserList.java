package com.unesc.mastersysapp.List;

import com.google.gson.annotations.SerializedName;
import com.unesc.mastersysapp.Models.User;

import java.util.List;

public class UserList {
    @SerializedName("data")
    public List<User> users;
}

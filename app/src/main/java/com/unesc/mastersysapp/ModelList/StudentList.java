package com.unesc.mastersysapp.ModelList;

import com.google.gson.annotations.SerializedName;
import com.unesc.mastersysapp.Models.Student;

import java.io.Serializable;
import java.util.List;

public class StudentList implements Serializable {
    @SerializedName("data")
    public List<Student> students;
}

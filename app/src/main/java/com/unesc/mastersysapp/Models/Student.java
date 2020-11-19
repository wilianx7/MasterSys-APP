package com.unesc.mastersysapp.Models;

import java.io.Serializable;

public class Student implements Serializable {
    public int id;
    public String name;
    public String birth_date;
    public String gender;
    public String email;
    public String observation;
    public String phone;
    public Address address;
}

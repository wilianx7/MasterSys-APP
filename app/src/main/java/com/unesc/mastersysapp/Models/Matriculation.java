package com.unesc.mastersysapp.Models;

import java.io.Serializable;
import java.util.Date;

public class Matriculation implements Serializable {
    public int id;
    public Student student;
    public Plan plan;
    public int due_date;
    public Date start_date;
    public Date end_date;
}

package com.unesc.mastersysapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unesc.mastersysapp.Adpters.StudentAdapter;
import com.unesc.mastersysapp.ModelList.StudentList;

public class StudentListActivity extends AppCompatActivity {
    private RecyclerView rc;
    private StudentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        rc =  findViewById(R.id.rc);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rc.setLayoutManager(manager);

        StudentList students = (StudentList)
                getIntent().getSerializableExtra("students");

        if(students != null){
            adapter = new StudentAdapter(students);
            rc.setAdapter(adapter);
        }
    }

}

package com.unesc.mastersysapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unesc.mastersysapp.Adpters.RegistrationAdapter;
import com.unesc.mastersysapp.Adpters.StudentAdapter;
import com.unesc.mastersysapp.ModelList.RegistrationList;
import com.unesc.mastersysapp.ModelList.StudentList;

public class RegistrationListActivity extends AppCompatActivity {
    private RecyclerView rc;
    private RegistrationAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matriculation_list);
        rc =  findViewById(R.id.rc);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rc.setLayoutManager(manager);

        RegistrationList registrations = (RegistrationList)
                getIntent().getSerializableExtra("registrations");

        if(registrations != null){
            adapter = new RegistrationAdapter(registrations);
            rc.setAdapter(adapter);
        }
    }
}

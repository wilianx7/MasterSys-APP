package com.unesc.mastersysapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unesc.mastersysapp.Adpters.PlanAdapter;
import com.unesc.mastersysapp.ModelList.PlanList;

public class PlanListActivity extends AppCompatActivity {
    private RecyclerView rc;
    private PlanAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
        rc =  findViewById(R.id.rc);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rc.setLayoutManager(manager);

        PlanList plans = (PlanList)
                getIntent().getSerializableExtra("plans");

        if(plans != null){
            adapter = new PlanAdapter(plans);
            rc.setAdapter(adapter);
        }
    }
}

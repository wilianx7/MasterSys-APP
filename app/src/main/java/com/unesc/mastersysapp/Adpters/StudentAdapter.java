package com.unesc.mastersysapp.Adpters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unesc.mastersysapp.Holders.PlanHolder;
import com.unesc.mastersysapp.Holders.StudentHolder;
import com.unesc.mastersysapp.ModelList.PlanList;
import com.unesc.mastersysapp.ModelList.StudentList;
import com.unesc.mastersysapp.Models.Plan;
import com.unesc.mastersysapp.Models.Student;
import com.unesc.mastersysapp.R;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentHolder> {
    private List<Student> students;

    public StudentAdapter(StudentList students){
        this.students = students.students;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_student, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        holder.name.setText(students.get(position).id + " - " + students.get(position).name);
        holder.city.setText("Cidade: " + students.get(position).address.city + " - " + students.get(position).address.state);
        holder.email.setText("E-mail: " + students.get(position).email);
        holder.phone.setText("Telefone: " + students.get(position).phone);
    }

    @Override
    public int getItemCount() {
        return students != null ? students.size() : 0;
    }
}

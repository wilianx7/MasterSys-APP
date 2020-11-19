package com.unesc.mastersysapp.Adpters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unesc.mastersysapp.Holders.RegistrationHolder;
import com.unesc.mastersysapp.Holders.StudentHolder;
import com.unesc.mastersysapp.ModelList.RegistrationList;
import com.unesc.mastersysapp.ModelList.StudentList;
import com.unesc.mastersysapp.Models.Matriculation;
import com.unesc.mastersysapp.Models.Student;
import com.unesc.mastersysapp.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class RegistrationAdapter extends RecyclerView.Adapter<RegistrationHolder> {
    private List<Matriculation> registrations;

    public RegistrationAdapter(RegistrationList registrations){
        this.registrations = registrations.registrations;
    }

    @NonNull
    @Override
    public RegistrationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RegistrationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_registration, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RegistrationHolder holder, int position) {

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String start_date = formato.format(registrations.get(position).start_date);
        String end_date = formato.format(registrations.get(position).end_date);

        holder.plan.setText(registrations.get(position).plan.name);
        holder.student.setText("Estudante: " + registrations.get(position).student.name);
        holder.end_date.setText("Data final: " + end_date);
        holder.start_date.setText("Data de inicio: " + start_date);
        holder.price.setText("Total a pagar: R$ " + registrations.get(position).plan.price);
    }

    @Override
    public int getItemCount() {
        return registrations != null ? registrations.size() : 0;
    }
}

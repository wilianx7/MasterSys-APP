package com.unesc.mastersysapp.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unesc.mastersysapp.R;

public class RegistrationHolder extends RecyclerView.ViewHolder {
    public TextView plan, student, start_date, end_date, price;

    public RegistrationHolder(@NonNull View itemView) {
        super(itemView);

        plan = itemView.findViewById(R.id.plan);
        student = itemView.findViewById(R.id.student);
        start_date = itemView.findViewById(R.id.start_date);
        end_date = itemView.findViewById(R.id.end_date);
        price = itemView.findViewById(R.id.price);
    }
}

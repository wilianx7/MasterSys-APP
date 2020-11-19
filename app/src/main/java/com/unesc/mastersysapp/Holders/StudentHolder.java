package com.unesc.mastersysapp.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unesc.mastersysapp.R;

public class StudentHolder extends RecyclerView.ViewHolder {

    public TextView name, email, city, phone;
    public ImageView delete;

    public StudentHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        email = itemView.findViewById(R.id.email);
        phone = itemView.findViewById(R.id.phone);
        city = itemView.findViewById(R.id.city);
    }
}

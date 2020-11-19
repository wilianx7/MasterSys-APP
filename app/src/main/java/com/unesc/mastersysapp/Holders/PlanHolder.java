package com.unesc.mastersysapp.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unesc.mastersysapp.R;

public class PlanHolder extends RecyclerView.ViewHolder {

    public TextView name, price, modality;
    public ImageView delete;

    public PlanHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        price = itemView.findViewById(R.id.price);
        modality = itemView.findViewById(R.id.modality);
    }
}

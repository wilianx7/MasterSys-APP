package com.unesc.mastersysapp.Adpters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unesc.mastersysapp.Holders.PlanHolder;
import com.unesc.mastersysapp.ModelList.PlanList;
import com.unesc.mastersysapp.Models.Plan;
import com.unesc.mastersysapp.R;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanHolder> {
    private List<Plan> plans;

    public PlanAdapter(PlanList plans){
        this.plans = plans.plans;
    }

    @NonNull
    @Override
    public PlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_plan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlanHolder holder, int position) {
        holder.name.setText(plans.get(position).name);
        holder.price.setText("R$ " + plans.get(position).price);
        holder.modality.setText("Modalidade: " + plans.get(position).modality.name);
    }

    @Override
    public int getItemCount() {
        return plans != null ? plans.size() : 0;
    }

}

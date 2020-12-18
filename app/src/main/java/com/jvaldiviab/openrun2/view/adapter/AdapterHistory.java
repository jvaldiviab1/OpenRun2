package com.jvaldiviab.openrun2.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.data.model.RunPojo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterHistory<M extends com.jvaldiviab.openrun2.data.model.RunPojo, V extends AdapterHistory.ViewHolder> extends FirebaseRecyclerAdapter<RunPojo, V> {

    FirebaseRecyclerOptions<RunPojo> options;

    public AdapterHistory(FirebaseRecyclerOptions<RunPojo> options) {
        super(options);

        this.options = options;
    }


    @Override
    protected void onBindViewHolder(V holder, @SuppressLint("RecyclerView") final int position, RunPojo runPojo) {

        holder.setTxtTime("Tiempo: "+runPojo.getTime());
        holder.setTxtPace("Prom. de pasos: "+runPojo.getAvgPace());
        holder.setTxtDist("Distancia: "+runPojo.getDistance());
        holder.setTxtDate("Fecha: "+runPojo.getDate());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.getSnapshots().size();
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);

        return (V) new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public TextView txtTime;
        public TextView txtPace;
        public TextView txtDist;
        public TextView txtDate;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_root);
            txtTime = itemView.findViewById(R.id.list_time);
            txtPace = itemView.findViewById(R.id.list_pace);
            txtDist = itemView.findViewById(R.id.list_dist);
            txtDate = itemView.findViewById(R.id.list_date);
        }

        public void setTxtTime(String string) {
            txtTime.setText(string);
        }

        public void setTxtPace(String string) {
            txtPace.setText(string);
        }

        public void setTxtDist(String string) {
            txtDist.setText(string);
        }

        public void setTxtDate(String string) {
            txtDate.setText(string);
        }
    }
}
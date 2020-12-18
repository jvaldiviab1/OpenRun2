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

public class AdapterHistory<M extends  com.jvaldiviab.openrun2.data.model.RunPojo, V extends AdapterHistory.ViewHolder> extends FirebaseRecyclerAdapter<RunPojo, V> {

    FirebaseRecyclerOptions<RunPojo> options;

    public AdapterHistory(FirebaseRecyclerOptions<RunPojo> options) {
        super(options);

        this.options = options;
    }


    @Override
    protected void onBindViewHolder(V holder, @SuppressLint("RecyclerView") final int position, RunPojo runPojo) {

        holder.setTxtTitle(runPojo.getDistance());
        holder.setTxtDesc(runPojo.getDate());

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
        public TextView txtTitle;
        public TextView txtDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_root);
            txtTitle = itemView.findViewById(R.id.list_title);
            txtDesc = itemView.findViewById(R.id.list_desc);
        }

        public void setTxtTitle(String string) {
            txtTitle.setText(string);
        }


        public void setTxtDesc(String string) {
            txtDesc.setText(string);
        }
    }
}
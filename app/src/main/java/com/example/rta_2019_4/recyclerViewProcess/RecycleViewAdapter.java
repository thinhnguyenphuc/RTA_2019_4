package com.example.rta_2019_4.recyclerViewProcess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rta_2019_4.R;

import java.util.ArrayList;
import java.util.HashMap;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HashMap<String,String>> hashMaps;

    public RecycleViewAdapter (Context context, ArrayList<HashMap<String,String>> hashMaps){
        this.context = context;
        this.hashMaps = hashMaps;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }

    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View heroView = inflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
        HashMap<String,String> item = hashMaps.get(position);
        String tmp = "";
        tmp += "country_id: "+item.get("country_id")+"\n";
        tmp += "region: "+item.get("region")+"\n";
        tmp +="country: "+item.get("country")+"\n";
        tmp += "dyad_name: "+item.get("dyad_name")+"\n";
        tmp += "conflict_name: "+item.get("conflict_name")+"\n";
        holder.textView.setText(tmp);
    }

    @Override
    public int getItemCount() {
        return hashMaps.size();
    }
}

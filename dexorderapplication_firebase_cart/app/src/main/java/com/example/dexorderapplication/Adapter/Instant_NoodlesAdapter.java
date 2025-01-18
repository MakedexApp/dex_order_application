package com.example.dexorderapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dexorderapplication.Activity.ShowDetailActivity;
import com.example.dexorderapplication.Domain.ItemDomain;
import com.example.dexorderapplication.R;

import java.util.ArrayList;


public class Instant_NoodlesAdapter extends RecyclerView.Adapter<Instant_NoodlesAdapter.ViewHolder> {
    ArrayList<ItemDomain> itemDomains;

    public Instant_NoodlesAdapter(ArrayList<ItemDomain> itemDomains) {
        this.itemDomains = itemDomains;
    }

    @Override
    public Instant_NoodlesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.instantnoodles_viewholder,parent,false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Instant_NoodlesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.instantnoodlename.setText(itemDomains.get(position).getTitle());
        holder.instantnoodleFee.setText(String.valueOf(itemDomains.get(position).getFee()));
        String picUrl = "";
        switch (position){
            case 0:{
                picUrl = "img_samyang";
                break;
            }
            case 1:{
                picUrl = "img_sin";
                break;
            }
            case 2:{
                picUrl = "img_ansung";
                break;
            }
            case 3:{
                picUrl = "img_bibim";
                break;
            }
        }
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.instantnoodleImage);

        holder.btn_showdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object",itemDomains.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView instantnoodlename;
        ImageView instantnoodleImage;
        TextView instantnoodleFee;
        ConstraintLayout layout;
        Button btn_showdetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            instantnoodlename = itemView.findViewById(R.id.instantnoodlesTitle);
            instantnoodleFee = itemView.findViewById(R.id.instantnoodlesFee);
            instantnoodleImage = itemView.findViewById(R.id.instantnoodlesImg);
            layout = itemView.findViewById(R.id.instantnoodlelayout);
            btn_showdetail = itemView.findViewById(R.id.btn_add_instantnoodledetail);
        }
    }
}

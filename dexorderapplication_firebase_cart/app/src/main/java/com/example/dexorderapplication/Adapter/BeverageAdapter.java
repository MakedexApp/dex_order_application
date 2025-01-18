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



public class BeverageAdapter extends RecyclerView.Adapter<BeverageAdapter.ViewHolder> {
    ArrayList<ItemDomain> itemDomains;

    public BeverageAdapter(ArrayList<ItemDomain> itemDomains) {
        this.itemDomains = itemDomains;
    }

    @Override
    public BeverageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.beverage_viewholder,parent,false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull BeverageAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.beverageName.setText(itemDomains.get(position).getTitle());
        holder.beverageFee.setText(String.valueOf(itemDomains.get(position).getFee()));
        String picUrl = "";
        switch (position){
            case 0:{
                picUrl = "img_pa";
                break;
            }
            case 1:{
                picUrl = "img_cola";
                break;
            }
            case 2:{
                picUrl = "img_sprite";
                break;
            }
            case 3:{
                picUrl = "img_2pro";
                break;
            }
        }
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.beverageImage);

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
        TextView beverageName;
        ImageView beverageImage;
        TextView beverageFee;
        ConstraintLayout layout;
        Button btn_showdetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            beverageName = itemView.findViewById(R.id.beverageTitle);
            beverageFee = itemView.findViewById(R.id.beverageFee);
            beverageImage = itemView.findViewById(R.id.beverageImg);
            layout = itemView.findViewById(R.id.beveragelayout);
            btn_showdetail = itemView.findViewById(R.id.btn_add_beveragedetail);
        }
    }
}

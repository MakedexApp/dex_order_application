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


public class SnackAdapter extends RecyclerView.Adapter<SnackAdapter.ViewHolder> {
    ArrayList<ItemDomain> itemDomains;

    public SnackAdapter(ArrayList<ItemDomain> itemDomains) {
        this.itemDomains = itemDomains;
    }

    @Override
    public SnackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.snack_viewholder,parent,false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SnackAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.snackName.setText(itemDomains.get(position).getTitle());
        holder.snackFee.setText(String.valueOf(itemDomains.get(position).getFee()));
        String picUrl = "";
        switch (position){
            case 0:{
                picUrl = "img_jb";
                break;
            }
            case 1:{
                picUrl = "img_pokachip";
                break;
            }
            case 2:{
                picUrl = "img_honey";
                break;
            }
            case 3:{
                picUrl = "img_ace";
                break;
            }
        }
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.snackImage);

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
        TextView snackName;
        ImageView snackImage;
        TextView snackFee;
        ConstraintLayout layout;
        Button btn_showdetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            snackName = itemView.findViewById(R.id.snackTitle);
            snackFee = itemView.findViewById(R.id.snackFee);
            snackImage = itemView.findViewById(R.id.snackImg);
            layout = itemView.findViewById(R.id.snacklayout);
            btn_showdetail = itemView.findViewById(R.id.btn_add_snackdetail);
        }
    }
}

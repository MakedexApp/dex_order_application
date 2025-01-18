package com.example.dexorderapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dexorderapplication.Adapter.BeverageAdapter;
import com.example.dexorderapplication.Adapter.Instant_NoodlesAdapter;
import com.example.dexorderapplication.Domain.ItemDomain;

import java.util.ArrayList;

public class Instant_noodlesFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.instant_noodles_recycler, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView = view.findViewById(R.id.recyclerview_instantNoodles);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<ItemDomain> itemDomains = new ArrayList<>();
        itemDomains.add(new ItemDomain("삼양라면","img_samyang",2000));
        itemDomains.add(new ItemDomain("신라면","img_sin",2000));
        itemDomains.add(new ItemDomain("안성탕면","img_ansung",2000));
        itemDomains.add(new ItemDomain("비빔면","img_bibim",2000));

        adapter = new Instant_NoodlesAdapter(itemDomains);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
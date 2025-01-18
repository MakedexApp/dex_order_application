package com.example.dexorderapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dexorderapplication.Adapter.BeverageAdapter;
import com.example.dexorderapplication.Domain.ItemDomain;

import java.util.ArrayList;

public class BeverageFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.beverage_recycler, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView = view.findViewById(R.id.recyclerview_beverage);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<ItemDomain> itemDomains = new ArrayList<>();
        itemDomains.add(new ItemDomain("파워에이드","img_pa",2000));
        itemDomains.add(new ItemDomain("콜라","img_cola",2000));
        itemDomains.add(new ItemDomain("스프라이트","img_sprite",2000));
        itemDomains.add(new ItemDomain("이프로","img_2pro",2000));

        adapter = new BeverageAdapter(itemDomains);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
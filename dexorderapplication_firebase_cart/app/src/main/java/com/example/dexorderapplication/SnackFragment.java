package com.example.dexorderapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dexorderapplication.Adapter.SnackAdapter;
import com.example.dexorderapplication.Domain.ItemDomain;

import java.util.ArrayList;

public class SnackFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.snack_recycler, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView = view.findViewById(R.id.recyclerview_snack);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<ItemDomain> itemDomains = new ArrayList<>();
        itemDomains.add(new ItemDomain("쫄병","img_jb",1000));
        itemDomains.add(new ItemDomain("포카칩","img_pokachip",2000));
        itemDomains.add(new ItemDomain("허니버터칩","img_honey",2000));
        itemDomains.add(new ItemDomain("에이스","img_ace",2000));

        adapter = new SnackAdapter(itemDomains);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
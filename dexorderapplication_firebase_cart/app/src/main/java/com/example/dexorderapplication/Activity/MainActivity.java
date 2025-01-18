package com.example.dexorderapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dexorderapplication.Adapter.CartListAdapter;
import com.example.dexorderapplication.Adapter.ViewPagerFragmentAdapter;
import com.example.dexorderapplication.Adapter.ViewPagerFragmentAdapter;
import com.example.dexorderapplication.Domain.ItemDomain;
import com.example.dexorderapplication.Helper.ManagementCart;
import com.example.dexorderapplication.Helper.TinyDB;
import com.example.dexorderapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPagerFragmentAdapter viewPagerFragmentAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    Button btn_cart;
    Context context;
    ArrayList<ItemDomain> listEmpty = new ArrayList<>();
    private String[] titles = new String[]{"음료","과자","라면"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(this);

        viewPager2.setAdapter(viewPagerFragmentAdapter);

        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText(titles[position]))).attach();

        btn_cart = findViewById(R.id.btn_cart);
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartListActivity.class);
                startActivity(intent);
            }
        });
    }
}
package com.example.dexorderapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dexorderapplication.Domain.ItemDomain;
import com.example.dexorderapplication.Helper.ManagementCart;
import com.example.dexorderapplication.Adapter.CartListAdapter;
import com.example.dexorderapplication.Interface.ChangeNumberItemsListener;
import com.example.dexorderapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    TextView totalTxt,emptyTxt;
    private ScrollView scrollView;
    ImageView img_back;
    Button orderBtn ;
    Integer a = 0, b = 0, orderState1 = 1, orderState2 = 1, orderState3 = 1, orderState4 = 1,  q = 1, w = 1, e = 1, r = 1;
    Integer place,total;

    private FirebaseAuth mFirebaseAuth;            // 데이터 베이스
    private DatabaseReference mDatabaseRef;       // 실시간 데이터베이스("dex")
    private DatabaseReference orderDataRef;       // 실시간 데이터베이스("order")

    ArrayList <ItemDomain> orderList = new ArrayList<>();
    ArrayList <String> list_title = new ArrayList<>();
    ArrayList <String> list_user = new ArrayList<>();
    ArrayList <String> list_order1 = new ArrayList<>();
    ArrayList <String> list_order2 = new ArrayList<>();
    ArrayList <String> list_order3 = new ArrayList<>();
    ArrayList <String> list_order4 = new ArrayList<>();
    ArrayList <Integer> list_numberInCart = new ArrayList<>();
    ArrayList <String> list_item_ko = new ArrayList<>();
    ArrayList <String> list_item = new ArrayList<>();

    String[] list_menu = new String[]{"zpwa", "zcola", "zsprite", "zpro", "zjb", "zpoka", "zhoney", "zace", "zsamyang", "zsin", "zansung", "zbibim"};

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managementCart = new ManagementCart(this);
        firebaseSetting();

        getCurrentUserPlace();


        img_back = findViewById(R.id.img_back_main);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                list_title.add("aorder state");
                list_numberInCart.add(1);
                list_title.add("bdelivery state");
                list_numberInCart.add(0);
                list_title.add("cplace");
                list_numberInCart.add(place);
                list_title.add("dlendhow");
                list_numberInCart.add(0);
                list_title.add("eback");
                list_numberInCart.add(0);
                list_title.add("fhome");
                list_numberInCart.add(0);
            }
        }, 800);// 0.6초 정도 딜레이를 준 후 시작

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                getOrderState();
            }
        }, 500);// 0.5초 정도 딜레이를 준 후 시작


        initView();
        initList();
        CalculateCart();

        orderBtn = findViewById(R.id.btn_order);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderList = new ArrayList<>();
                list_item = new ArrayList<>();
                getOrderState();
                orderList = managementCart.getListCart();

                if(orderList.isEmpty()){
                    Toast.makeText(CartListActivity.this, "장바구니에 상품이 없습니다", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < orderList.size(); i++) {
                    list_item_ko.add(i,orderList.get(i).getTitle());
                }
                Log.d("Cart", "list_item_ko.size: " + list_item_ko.size());
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (b == 5){
                            Toast.makeText(CartListActivity.this, "주문이 밀려있습니다. 잠시 후에 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                            b = 0;

                        }
                        else if(b == 0) {
                            shiftlanguage();
                            new Handler().postDelayed(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    if (a > 0 && a < 5) {
                                        for (int j = 0; j < list_item.size(); j++) {
                                            list_title.add(6+j, list_item.get(j));
                                        }
                                        for (int i = 0; i < orderList.size(); i++) {
                                            list_numberInCart.add(6+i, (orderList.get(i).getNumberInCart()));
                                            Log.d("Cart", "list: " + orderList.get(i).getNumberInCart());
                                        }
                                        Log.d("Cart", "list_title: " + list_title);
                                        Log.d("Cart", "list_item: " + list_item);
                                        Log.d("Cart", "list_numberInCart: " + list_numberInCart);
                                        Log.d("Cart", "orderlist size: " + orderList);

                                        Intent intent = new Intent(CartListActivity.this,OrderInformation.class);
                                        intent.putExtra("place",place);
                                        intent.putExtra("a",a);
                                        intent.putExtra("totalFee",total);
                                        intent.putExtra("list_title",list_title);
                                        intent.putExtra("list_numberInCart",list_numberInCart);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }, 1000);// 0.6초 정도 딜레이를 준 후 시작
                        }
                        list_item_ko.clear();
                    }
                }, 1200);// 1초 정도 딜레이를 준 후 시작
            }
        });
    }

    public void shiftlanguage(){
        for (int i = 0; i < list_item_ko.size(); i++) {
            switch(list_item_ko.get(i)){
                case "파워에이드":
                    list_item.add(i,list_menu[0]);
                    break;
                case "콜라":
                    list_item.add(i,list_menu[1]);
                    break;
                case "스프라이트":
                    list_item.add(i,list_menu[2]);
                    break;
                case "이프로":
                    list_item.add(i,list_menu[3]);
                    break;
                case "쫄병":
                    list_item.add(i,list_menu[4]);
                    break;
                case "포카칩":
                    list_item.add(i,list_menu[5]);
                    break;
                case "허니버터칩":
                    list_item.add(i,list_menu[6]);
                    break;
                case "에이스":
                    list_item.add(i,list_menu[7]);
                    break;
                case "삼양라면":
                    list_item.add(i,list_menu[8]);
                    break;
                case "신라면":
                    list_item.add(i,list_menu[9]);
                    break;
                case "안성탕면":
                    list_item.add(i,list_menu[10]);
                    break;
                case "비빔면":
                    list_item.add(i,list_menu[11]);
                    break;
            }
        }
    }

    public void getOrderState(){
        orderDataRef.child("order1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.d("MainActivity", "UserAccount : " + snapshot1.getValue());
                    list_order1.add(snapshot1.getValue().toString());
                }
                if(list_order1.isEmpty()){
                    q = 1;
                }
                else {
                    orderState1 = Integer.valueOf(list_order1.get(0));
                    if(orderState1 == 0){
                        q = 1;
                        orderDataRef.child("order1").removeValue();
                    } else if (orderState1 == 1) {
                        q = 2;
                    }
                }
                list_order1 = new ArrayList<>();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        orderDataRef.child("order2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.d("MainActivity", "UserAccount : " + snapshot1.getValue());
                    list_order2.add(snapshot1.getValue().toString());
                }
                if(list_order2.isEmpty()){
                    w = 1;
                }
                else {
                    orderState2 = Integer.valueOf(list_order2.get(0));
                    if(orderState2 == 0){
                        w = 1;
                        orderDataRef.child("order2").removeValue();
                    }
                    else if (orderState2 == 1) {
                        w = 3;
                    }
                }
                list_order2 = new ArrayList<>();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        orderDataRef.child("order3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.d("MainActivity", "UserAccount : " + snapshot1.getValue());
                    list_order3.add(snapshot1.getValue().toString());
                }
                if(list_order3.isEmpty()){
                    e = 1;
                }
                else {
                    orderState3 = Integer.valueOf(list_order3.get(0));
                    if(orderState3 == 0){
                        e = 1;
                        orderDataRef.child("order3").removeValue();
                    }
                    else if (orderState3 == 1) {
                        e = 5;
                    }
                }
                list_order3 = new ArrayList<>();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        orderDataRef.child("order4").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.d("MainActivity", "UserAccount : " + snapshot1.getValue());
                    list_order4.add(snapshot1.getValue().toString());
                }
                if(list_order4.isEmpty()){
                    r = 1;
                }
                else {
                    orderState4 = Integer.valueOf(list_order4.get(0));
                    if(orderState4 == 0){
                        r = 1;
                        orderDataRef.child("order4").removeValue();
                    }
                    else if (orderState4 == 1) {
                        r = 7;
                    }
                }
                list_order4 = new ArrayList<>();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.e("Swich","q,w,e,r:" + q + "," + w + "," + e + "," + r);


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                switch (q * w * e * r){
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 15:
                    case 21:
                    case 35:
                    case 105:
                        a = 1;
                        break;
                    case 2:
                    case 10:
                    case 14:
                    case 70:
                        a = 2;
                        break;
                    case 6:
                    case 42:
                        a = 3;
                        break;
                    case 30:
                        a = 4;
                        break;
                    case 210:
                        b = 5;
                        break;
                }
            }
        }, 1000);// 1초 정도 딜레이를 준 후 시작

    }

    public void getCurrentUserPlace(){
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.d("CartListActivity", "user : " + snapshot1.getValue());
                    list_user.add(snapshot1.getValue().toString());
                }
                place = Integer.valueOf(list_user.get(5));
                Log.d("CartListActivity", "userPlace : " + place);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void firebaseSetting() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("dex");
        orderDataRef = FirebaseDatabase.getInstance().getReference("order");
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerview);
        totalTxt = findViewById(R.id.total_charge);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView2);

    }
    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if(managementCart.getListCart().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }
    private void CalculateCart() {
        total = Math.round(managementCart.getTotalFee() * 100) / 100;
        totalTxt.setText(total + "원");
    }
}

package com.example.dexorderapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dexorderapplication.BeverageFragment;
import com.example.dexorderapplication.Cart;
import com.example.dexorderapplication.Domain.ItemDomain;
import com.example.dexorderapplication.Helper.ManagementCart;
import com.example.dexorderapplication.R;
import com.example.dexorderapplication.order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowDetailActivity extends AppCompatActivity {

    private Button addToCartBtn, plusBtn, minusBtn;
    private TextView titleText, feeTxt, numberOrderTxt;
    private ImageView picItem, img_back;
    private ItemDomain object;
    int numberOrder = 1, count_check = 0, i = 0;

    private String title_check = "";
    private ManagementCart managementCart;
    private FirebaseAuth mFirebaseAuth;            // 데이터 베이스
    private DatabaseReference mDatabaseRef, orderDataRef, cartDataRef;       // 실시간 데이터베이스("dex"), 실시간 데이터베이스("order"), 실시간 데이터베이스("cart")

    ArrayList <String> list_check = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        img_back = findViewById(R.id.img_back_main);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        settingFirebase();


        managementCart = new ManagementCart(this);
        initView();
        getBundle();
    }

    private void settingFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("dex");
        orderDataRef = FirebaseDatabase.getInstance().getReference("order");
        cartDataRef = FirebaseDatabase.getInstance().getReference("cart");
    }

    private void getBundle() {
        object = (ItemDomain) getIntent().getSerializableExtra("object");

        int drawableResourceId = this.getResources().getIdentifier(object.getPic(),"drawable",this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(picItem);

        titleText.setText(object.getTitle());
        feeTxt.setText(object.getFee()+"원");
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder =numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOrder > 1){
                    numberOrder =numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                object.setNumberInCart(numberOrder);
                managementCart.inserItem(object);
                i = object.getNumberInCart();
                ItemDomain cart = new ItemDomain(object.getTitle(), i);
                Log.d("a","numberOrder : " + object.getTitle() + object.getNumberInCart());
                existAlreadyInCart();
                Log.d("b","check : " + count_check + title_check);

                if (title_check.equals(object.getTitle()) == true){
                    count_check += i;
                    ItemDomain cart1 = new ItemDomain(object.getTitle(), count_check);
                    cartDataRef.child(firebaseUser.getUid()).child(object.getTitle()).setValue(cart1);
                }
                else {
                    cartDataRef.child(firebaseUser.getUid()).child(object.getTitle()).setValue(cart);
                }
                list_check = new ArrayList<>();
            }
        });
    }

    private void existAlreadyInCart(){
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        cartDataRef.child(firebaseUser.getUid()).child(object.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    Log.d("ShowDetailActivity", " cart : " + snapshot1.getValue());
                    list_check.add(snapshot1.getValue().toString());
                }
                count_check = Integer.valueOf(list_check.get(0));
                title_check = list_check.get(1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initView() {
        addToCartBtn = findViewById(R.id.btn_add_beverage_cart);
        plusBtn = findViewById(R.id.btn_plus);
        minusBtn = findViewById(R.id.btn_minus);
        titleText = findViewById(R.id.titleTxt);
        feeTxt = findViewById((R.id.feeTxt));
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        picItem = findViewById(R.id.picItem);
    }
}

package com.example.dexorderapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity_previous extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;            // 데이터 베이스
    private DatabaseReference mDatabaseRef;       // 실시간 데이터베이스("dex")
    private DatabaseReference orderDataRef;       // 실시간 데이터베이스("order")
    private Button mbtnOrder;                     // 주문 버튼

    private Button poka_plus;
    private Button poka_minus;
    private Button hrb_plus;
    private Button hrb_minus;
    private Button swc_plus;
    private Button swc_minus;
    private TextView poka_count;
    private TextView hrb_count;
    private TextView swc_count;
    private Integer i=0;
    private Integer j = 0;
    private Integer k = 0;

    Integer a = 1;
    ArrayList <String> list_order1 = new ArrayList<>();
    ArrayList <String> list_order2 = new ArrayList<>();
    ArrayList <String> list_order3 = new ArrayList<>();
    ArrayList <String> list_order4 = new ArrayList<>();
    ArrayList <String> list_user = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_previous);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("dex");
        orderDataRef = FirebaseDatabase.getInstance().getReference("order");

        poka_plus = findViewById(R.id.poka_plus);
        poka_minus = findViewById(R.id.poka_minus);
        poka_count = findViewById(R.id.poka_count);

        hrb_plus = findViewById(R.id.hrb_plus);
        hrb_minus = findViewById(R.id.hrb_minus);
        hrb_count = findViewById(R.id.hrb_count);

        swc_plus = findViewById(R.id.swc_plus);
        swc_minus = findViewById(R.id.swc_minus);
        swc_count = findViewById(R.id.swc_count);

        mbtnOrder = findViewById(R.id.btn_order);

        poka_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i>=99){
                    i = 99;
                    Toast.makeText(getApplicationContext(), "이미 가장 높은 수량입니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                i++;
                poka_count.setText(String.valueOf(i));
            }
        });

        poka_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i<=0){
                    i = 0;
                    Toast.makeText(getApplicationContext(), "이미 가장 낮은 수량입니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                i--;
                poka_count.setText(String.valueOf(i));
            }
        });

        hrb_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(j>=99){
                    j = 99;
                    Toast.makeText(getApplicationContext(), "이미 가장 높은 수량입니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                j++;
                hrb_count.setText((String.valueOf(j)));
            }
        });

        hrb_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(j<=0){
                    j = 0;
                    Toast.makeText(getApplicationContext(), "이미 가장 낮은 수량입니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                j--;
                hrb_count.setText((String.valueOf(j)));
            }
        });

        swc_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(k>=99){
                    k = 99;
                    Toast.makeText(getApplicationContext(), "이미 가장 높은 수량입니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                k++;
                swc_count.setText((String.valueOf(k)));
            }
        });

        swc_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(k<=0){
                    k = 0;
                    Toast.makeText(getApplicationContext(), "이미 가장 낮은 수량입니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                k--;
                swc_count.setText((String.valueOf(k)));
            }
        });

        mbtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            Log.d("MainActivity", "UserAccount : " + snapshot1.getValue());
                            list_user.add(snapshot1.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                String place = "dex/UserAccount/" + firebaseUser.getUid() +"/place";

                orderDataRef.child("order").child("order1").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            Log.d("MainActivity", "order1 : " + snapshot1.getValue());
                            list_order1.add(snapshot1.getValue().toString());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                orderDataRef.child("order").child("order2").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            Log.d("MainActivity", "order2 : " + snapshot1.getValue());
                            list_order2.add(snapshot1.getValue().toString());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                orderDataRef.child("order").child("order3").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            Log.d("MainActivity", "order3 : " + snapshot1.getValue());
                            list_order3.add(snapshot1.getValue().toString());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                orderDataRef.child("order").child("order4").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            Log.d("MainActivity", "order4 : " + snapshot1.getValue());
                            list_order4.add(snapshot1.getValue().toString());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                String pkc = poka_count.getText().toString();
                String hrb = hrb_count.getText().toString();
                String swc = swc_count.getText().toString();
                String order = "1";



                addOrder(pkc,hrb,swc,place,order);
            }
        });

    }

    private void addOrder(String pkc, String hrb, String swc, String place, String order) {
        order order1= new order(pkc, hrb, swc, place, order);

        if (a == 5){
            a = 1;
            if(list_order1.get(1) == "1"){
                Toast.makeText(MainActivity_previous.this, "주문이 밀려있습니다. 잠시 후에 시도해주세요.", Toast.LENGTH_LONG).show();
                return;
            }
        }

        orderDataRef.child("order").child("order" + a).setValue(order1);
        a++;
    }
}
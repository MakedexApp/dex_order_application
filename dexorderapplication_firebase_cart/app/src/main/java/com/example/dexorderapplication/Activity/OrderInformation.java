package com.example.dexorderapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dexorderapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderInformation extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;            // 데이터 베이스
    private DatabaseReference mDatabaseRef;       // 실시간 데이터베이스("dex")
    TextView name_order, total_order;
    EditText place_order;
    Button btn_next;

    ArrayList<String> list_user = new ArrayList<>();
    ArrayList <String> list_title = new ArrayList<>();
    ArrayList <String> list_numberInCart = new ArrayList<>();
    String name, place1, place;
    Integer total, a;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderinformation);
        name_order = findViewById(R.id.tv_name_order);
        total_order = findViewById(R.id.tv_total_order);
        place_order = findViewById(R.id.et_place_order);
        btn_next = findViewById(R.id.btn_next);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("dex");
        Intent intent = getIntent();
        total = intent.getIntExtra("totalFee",0);
        getCurrentUserPlace();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                name_order.setText(name);
                place_order.setText(place);
                total_order.setText(total.toString());
            }
        }, 500);// 0.5초 정도 딜레이를 준 후 시작

        a = intent.getIntExtra("a",0);
        list_title = intent.getStringArrayListExtra("list_title");
        list_numberInCart = (ArrayList<String>) intent.getSerializableExtra("list_numberInCart");
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(OrderInformation.this,PaymentActivity.class);
                place1 = place_order.getText().toString();
                if(place.toString() != place1){
                    list_numberInCart.set(2,place1);
                }
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        intent1.putExtra("a",a);
                        intent1.putExtra("totalFee",total);
                        intent1.putExtra("list_title",list_title);
                        intent1.putExtra("list_numberInCart",list_numberInCart);
                        startActivity(intent1);
                        finish();
                    }
                }, 500);// 0.5초 정도 딜레이를 준 후 시작
            }
        });
    }

    public void getCurrentUserPlace(){
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.d("OrderInformation", "user : " + snapshot1.getValue());
                    list_user.add(snapshot1.getValue().toString());
                }
                name = list_user.get(3);
                place = list_user.get(5);
                Log.d("OrderInformation", "userName : " + name);
                Log.d("OrderInformation", "userPlace : " + place);
                list_user.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

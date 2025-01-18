package com.example.dexorderapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dexorderapplication.Activity.PaymentActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticeArrival extends AppCompatActivity {
    Integer a = 0;
    private DatabaseReference orderDataRef;       // 실시간 데이터베이스("order")
    Button btn_recieve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticearrival);
        orderDataRef = FirebaseDatabase.getInstance().getReference("order");

        btn_recieve = findViewById(R.id.button);
        btn_recieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = getApplicationContext();
                Intent intent = new Intent(NoticeArrival.this,MyService.class);
                intent.putExtra("arrival",true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startService(intent);
                    context.startForegroundService(intent);
                }
                a = ((PaymentActivity)PaymentActivity.context_main).a;
                orderDataRef.child("order" + a).child("eback").setValue(1);
                finish();
            }
        });
    }
}

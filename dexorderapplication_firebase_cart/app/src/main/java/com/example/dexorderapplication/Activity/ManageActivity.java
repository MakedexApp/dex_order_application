package com.example.dexorderapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dexorderapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManageActivity extends AppCompatActivity {
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DatabaseReference salesDataRef;       // 매출 데이터베이스("sales")

    TextView mTextView, mSale;
    ArrayList<String> list_sale = new ArrayList<>();
    Integer sales = 0;
    Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        salesDataRef = FirebaseDatabase.getInstance().getReference("sales");

        //bind view
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setText(getTime());
        mSale = (TextView) findViewById(R.id.sales_text);
        getSales(getTime());
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mSale.setText(sales.toString());
            }
        }, 500);// 0.5초 정도 딜레이를 준 후 시작

        btn_exit = findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    private void getSales(String date){
        salesDataRef.child(date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Log.d("ManageActivity", "sales : " + snapshot1.getValue());
                    list_sale.add(snapshot1.getValue().toString());
                }
                if(list_sale.size()>0){
                    sales = Integer.valueOf(list_sale.get(0));
                }
                list_sale = new ArrayList<>();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
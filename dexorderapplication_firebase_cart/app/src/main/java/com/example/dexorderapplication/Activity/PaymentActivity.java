package com.example.dexorderapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dexorderapplication.MyService;
import com.example.dexorderapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
    Button btn_pay;
    ArrayList<String> list_title, list_sale = new ArrayList<>();
    ArrayList <String> list_numberInCart = new ArrayList<>();
    private DatabaseReference orderDataRef;       // 실시간 데이터베이스("order")
    private DatabaseReference salesDataRef;       // 매출 데이터베이스("sales")
    public int a;
    String totalText, place1;
    TextView paytotalFee, cardNumberTxt, ExpMonthTxt, ExpDayTxt, CvcTxt;
    Integer total, sales = 0;
    public static Context context_main;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        context_main = this;
        orderDataRef = FirebaseDatabase.getInstance().getReference("order");
        salesDataRef = FirebaseDatabase.getInstance().getReference("sales");
        btn_pay = findViewById(R.id.btn_pay);
        paytotalFee = findViewById(R.id.paytotalTxt);
        cardNumberTxt = findViewById(R.id.cardNumberTxt);
        ExpMonthTxt = findViewById(R.id.ExpMonthTxt);
        ExpDayTxt = findViewById(R.id.ExpDayText);
        CvcTxt = findViewById(R.id.CvcTxt);
        Intent intent = getIntent();
        place1 = intent.getStringExtra("place");
        a = intent.getIntExtra("a",0);
        total = intent.getIntExtra("totalFee",0);
        totalText = intent.getStringExtra("totalFee");
        list_title = intent.getStringArrayListExtra("list_title");
        list_numberInCart = (ArrayList<String>) intent.getSerializableExtra("list_numberInCart");
        paytotalFee.setText(total.toString());
        Log.d("pay","txt" + cardNumberTxt.getText().toString());

        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        String date = mFormat.format(mDate);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cardNumberTxt.getText().toString().length() > 15 && ExpMonthTxt.getText().toString().length() > 0 && ExpDayTxt.getText().toString().length() > 0 && CvcTxt.getText().toString().length() > 2){
                    Toast.makeText(PaymentActivity.this, "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < list_title.size(); k++) {
                        orderDataRef.child("order" + a).child(list_title.get(k)).setValue(list_numberInCart.get(k));
                    }
                    getsales(date);
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if(sales == 0){
                                salesDataRef.child(date).child("sales").setValue(total.toString());
                            } else if (sales > 0) {
                                total += sales;
                                salesDataRef.child(date).child("sales").setValue(total.toString());
                            }
                        }
                    }, 600);// 0.6초 정도 딜레이를 준 후 시작
                    Context context = getApplicationContext();
                    Intent it = new Intent(PaymentActivity.this, MyService.class);
                    it.putExtra("a",a);
                    //startService(it);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startService(it);
                        context.startForegroundService(it);
                    }
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            finish();
                        }
                    }, 600);// 0.6초 정도 딜레이를 준 후 시작
                }
                else{
                    Toast.makeText(PaymentActivity.this, "카드 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void getsales(String date){
        salesDataRef.child(date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Log.d("MainActivity", "UserAccount : " + snapshot1.getValue());
                    list_sale.add(snapshot1.getValue().toString());
                }
                if (list_sale.size()>0) {
                    sales = Integer.valueOf(list_sale.get(0));
                }
                Log.d("Payment", "sales : " + sales);
                list_sale = new ArrayList<>();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

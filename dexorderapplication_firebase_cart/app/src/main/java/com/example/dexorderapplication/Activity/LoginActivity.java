package com.example.dexorderapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dexorderapplication.Helper.ManagementCart;
import com.example.dexorderapplication.MyService;
import com.example.dexorderapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText mEtEmail, mEtPw;  // 회원가입 입력필드
    EditText et;
    ManagementCart managementCart;
    ArrayList list = new ArrayList<>();

    ImageView img_dollar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        managementCart = new ManagementCart(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("dex");

        mEtEmail = findViewById(R.id.et_email);
        mEtPw = findViewById(R.id.et_pw);
        Intent serviceIntent = new Intent(this, MyService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        }
        foregroundServiceRunning();

        img_dollar = findViewById(R.id.img_dollar);
        img_dollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manageintent = new Intent(LoginActivity.this, ManageActivity.class);
                AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivity.this);
                ad.setIcon(R.mipmap.ic_launcher);
                ad.setTitle("관리자");
                ad.setMessage("관리자 비밀번호를 입력하세요.");

                final EditText et = new EditText(LoginActivity.this);
                ad.setView(et);

                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result = et.getText().toString();
                        Log.d("Manage","result:" + result.length());
                        if(result.equals("1234")){
                            startActivity(manageintent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "관리자 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인 요청
                String strEmail = mEtEmail.getText().toString();
                String strpw = mEtPw.getText().toString();

                // 아이디, 비밀번호 null일 경우 체크하여 return
                if ( strEmail.length() < 1 ) {
                    Toast.makeText(LoginActivity.this, "이메일을 입력하세요.", Toast.LENGTH_LONG).show();
                    return;
                }
                if ( strpw.length() < 1 ){
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                    return;
                }



                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strpw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // 로그인 성공 !!!
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            mEtEmail.setText("");
                            mEtPw.setText("");
                            managementCart.deleteItem(list);
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        TextView tv_register = findViewById(R.id.pwadeTxt);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    public boolean foregroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(MyService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
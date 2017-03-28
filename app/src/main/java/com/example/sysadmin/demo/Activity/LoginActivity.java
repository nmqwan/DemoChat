package com.example.sysadmin.demo.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.sysadmin.demo.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    DatabaseReference myRef;
    String TAG = MainActivity.class.getSimpleName();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @BindView(R.id.txtUser)
    TextInputEditText txtUser;
    @BindView(R.id.txtPass)
    TextInputEditText txtPass;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected void initData() {
        preferences = getSharedPreferences("quan", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    @Override
    protected void setEvent() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        myRef = database.getReference("USER");

    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        final String usertmp = txtUser.getText().toString().trim();

        myRef.child(usertmp).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    if (!usertmp.equals("")) {
                        Map<String, String> mapPass = (Map<String, String>) dataSnapshot.getValue();
                        String password = mapPass.get("PASSWORD");
                        editor.putString("user", usertmp);
                        editor.commit();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "Sai user", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "sai pass", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

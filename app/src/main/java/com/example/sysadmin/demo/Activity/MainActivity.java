package com.example.sysadmin.demo.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sysadmin.demo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    String TAG = MainActivity.class.getSimpleName();
    DatabaseReference myRef;
    List<String> users;
    String user = "";
    ListView recyclerListFr;
    ArrayAdapter<String> adapter;
    FirebaseDatabase database;

    protected void initData() {
        database = FirebaseDatabase.getInstance();
        recyclerListFr = (ListView) findViewById(R.id.recyclerListFr);
        preferences = getSharedPreferences("quan", Context.MODE_PRIVATE);
        myRef = database.getReference("USER");
        users = new ArrayList<>();


        loadUser();
        checkLogin();
    }

    protected void setEvent() {
        recyclerListFr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(MainActivity.this,ChatActivity.class);
                intent.putExtra("userChat",users.get(position));
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        setEvent();

    }

    public void checkLogin() {
        user = preferences.getString("user", "");
        if (user.equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    public void loadUser() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Objects> mapUser = (Map<String, Objects>) dataSnapshot.getValue();
                users = initListUser(mapUser);
                adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, users);
                recyclerListFr.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public List<String> initListUser(Map<String, Objects> map) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Objects> entry : map.entrySet()) {
            if (!entry.getKey().equals(user)) {
                result.add(entry.getKey());
            }

        }
        return result;
    }
}

package com.example.sysadmin.demo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.sysadmin.demo.Adapter.AdapterChat1;
import com.example.sysadmin.demo.Model.Message;
import com.example.sysadmin.demo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    String TAG = ChatActivity.class.getSimpleName();
    SharedPreferences preferences;
    RecyclerView rVChat;
    @BindView(R.id.edtChat)
    EditText edtChat;
    @BindView(R.id.btnSend)
    Button btnSend;

    DatabaseReference myRef;
    FirebaseDatabase database;
    String userChat, user;
    ArrayList<Message> listMes;
    /*
    * @params :statusCode
     * true : userChat->user
     * flase : user->userChat*/
    boolean statusCode = false;
    RecyclerView.Adapter adapterChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initData();
        loadDataChat();


    }

    public void initData() {
        database = FirebaseDatabase.getInstance();
        listMes=new ArrayList<>();
    rVChat= (RecyclerView) findViewById(R.id.rVChat);

        Intent i = getIntent();
        userChat = i.getStringExtra("userChat");
        preferences = getSharedPreferences("quan", MODE_PRIVATE);
        user = preferences.getString("user", "");
        myRef = database.getReference("MESSAGE");
        myRef.child(userChat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                statusCode = dataSnapshot.hasChild(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.btnSend)
    public void onViewClicked() {
        String chat = edtChat.getText().toString();
        long time = Calendar.getInstance().getTime().getTime();
        int fromID = 0;
        Message message;
        myRef = database.getReference("MESSAGE");
        if (statusCode) {
            fromID = 1;
            message = new Message(chat, fromID, time);
            myRef.child(userChat).child(user).push().setValue(message);
        } else {
            message = new Message(chat, fromID, time);
            myRef.child(user).child(userChat).push().setValue(message);
        }
    }
    public void loadDataChat(){
        if (statusCode) {
            getListAllMess(user,userChat);
        }else{
            getListAllMess(userChat,user);
        }
    }

    public void getListAllMess(String user,String user1){
//        myRef.child(userChat).child(user).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Message m = dataSnapshot.getValue(Message.class);
//                listMes.add(m);
//                Log.e(TAG, "onChildAdded: "+listMes.size() );
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        Log.e(TAG, "getListAllMess: "+ user +" "+ userChat +" "+ user1 +" "+user );
        myRef.child(user).child(user1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    JSONObject m = new JSONObject((Map) postSnapshot.getValue());
                    try {
                        Message mess = new Message(m.getString("content"),m.getInt("fromId"),m.getLong("time"));
                        listMes.add(mess);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapterChat = new AdapterChat1(ChatActivity.this,listMes);
                rVChat.setAdapter(adapterChat);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this, LinearLayoutManager.VERTICAL, false);
                rVChat.setLayoutManager(linearLayoutManager);
                adapterChat.notifyDataSetChanged();
                Log.e(TAG, "onDataChange: " );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

package com.example.sysadmin.demo.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sysadmin on 3/28/17.
 */

public abstract class BaseActivity extends Activity {
    FirebaseDatabase database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        initData();
        setEvent();
    }
    protected abstract void initData();

    protected abstract void setEvent();


}

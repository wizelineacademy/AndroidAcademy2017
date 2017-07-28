package com.joseluishdz.app.customviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyAwesomeCustomTextView v = (MyAwesomeCustomTextView) findViewById(R.id.myview);
        v.setValues("1", "1,562,500");

    }
}

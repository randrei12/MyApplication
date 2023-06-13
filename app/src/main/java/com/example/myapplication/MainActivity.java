package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.home_layout);
        super.onCreate(savedInstanceState);
        LinearLayout layout = findViewById(R.id.div);
        Fetch fetch = new Fetch("http://192.168.1.216:8081/api/projects", layout);
        fetch.execute();

    }
}
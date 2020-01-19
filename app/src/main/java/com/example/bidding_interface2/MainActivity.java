package com.example.bidding_interface2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv_front;
    Button btn_front;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_front = (TextView)findViewById(R.id.txt_front);
        btn_front = (Button)findViewById(R.id.btn_front);
        btn_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Home_Drawer.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

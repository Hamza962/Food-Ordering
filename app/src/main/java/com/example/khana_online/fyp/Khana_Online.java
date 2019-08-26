package com.example.khana_online.fyp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Khana_Online extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khana__online);


        Button yourButton = (Button) findViewById(R.id.button_customer_login);
        Button yourButton1 = (Button) findViewById(R.id.button3);


        yourButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Khana_Online.this , Chef_Signin.class));
            }
        });

        yourButton1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Khana_Online.this , Home.class));
            }
        });
    }
}

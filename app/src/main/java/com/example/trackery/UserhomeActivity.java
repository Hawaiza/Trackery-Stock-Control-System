package com.example.trackery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserhomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);

    }
    public void prod(View v1){
        Intent i = new Intent(getApplicationContext(),user_product.class);
        startActivity(i);
    }

    public void sale(View v2){
        Intent i2 = new Intent(getApplicationContext(), USales_Home.class);
        startActivity(i2);
    }

    public void cust(View v3){
        Intent i3 = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i3);
    }

    public void user(View v4){
        Intent i4 = new Intent(getApplicationContext(),Users.class);
        startActivity(i4);
    }
}
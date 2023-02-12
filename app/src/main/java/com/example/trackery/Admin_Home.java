package com.example.trackery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Admin_Home extends AppCompatActivity {
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
    }

    @Override
    public void onBackPressed() {

        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        }
        else
        {
            Toast.makeText(getBaseContext(), "Press back again to LogOut", Toast.LENGTH_SHORT).show();
        }
        backPressedTime=System.currentTimeMillis();
    }

    public void prod(View v1){
        Intent i = new Intent(getApplicationContext(),ProductsActivity.class);
        startActivity(i);
    }

    public void sale(View v2){
        Intent i2 = new Intent(getApplicationContext(),Sales_List.class);
        startActivity(i2);
    }

    public void cust(View v3){
        Intent i3 = new Intent(getApplicationContext(),Customer.class);
        startActivity(i3);
    }

    public void user(View v4){
        Intent i4 = new Intent(getApplicationContext(),Users.class);
        startActivity(i4);
    }
}


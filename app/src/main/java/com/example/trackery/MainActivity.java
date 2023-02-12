package com.example.trackery;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    public static UsersDBHelper db;
    public String userMobile;
    EditText email, password;
    public String pswd;
    Button login;
    String emailAddress;
    ToggleButton admin_but,user_but;
    public String phone;
    TextView forgotpass;

    private Boolean validateEmail(EditText email) {
        emailAddress = email.getText().toString();
        if (Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            return true;
        } else {
            return false;
        }
    }



    public void admin_clicked(View v)
    {
        forgotpass = (TextView) findViewById(R.id.forgotpass);
        forgotpass.setVisibility(View.INVISIBLE);
        if (!admin_but.isChecked())
        {

            admin_but.setChecked(true);
            admin_but.setBackgroundColor(Color.parseColor("#1A434E"));
        }
        else
        {
            admin_but.setBackgroundColor(Color.parseColor("#1A434E"));
            admin_but.setTextColor(Color.parseColor("#EFE9FF"));
            user_but.setChecked(false);
            user_but.setBackgroundColor(Color.parseColor("#FBF3EA"));
            user_but.setTextColor(Color.parseColor("#1A434E"));
        }
    }

    public void user_clicked(View v)
    {
        forgotpass = (TextView) findViewById(R.id.forgotpass);
        forgotpass.setVisibility(View.INVISIBLE);
        if (!admin_but.isChecked())
        {

            user_but.setChecked(true);
            user_but.setBackgroundColor(Color.parseColor("#1A434E"));
        }
        else
        {
            user_but.setBackgroundColor(Color.parseColor("#1A434E"));
            user_but.setTextColor(Color.parseColor("#EFE9FF"));
            admin_but.setChecked(false);
            admin_but.setBackgroundColor(Color.parseColor("#FBF3EA"));
            admin_but.setTextColor(Color.parseColor("#1A434E"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.et1);
        password= (EditText)findViewById(R.id.et2);
        login = (Button) findViewById(R.id.button);
        admin_but = (ToggleButton) findViewById(R.id.admin);
        user_but = (ToggleButton) findViewById(R.id.user);
        forgotpass = (TextView) findViewById(R.id.forgotpass);

        user_but.setChecked(false);
        admin_but.setChecked(true);
        admin_but.setBackgroundColor(Color.parseColor("#1A434E"));
        admin_but.setTextColor(Color.parseColor("#FBF3EA"));
        user_but.setBackgroundColor(Color.parseColor("#FBF3EA"));
        user_but.setTextColor(Color.parseColor("#1A434E"));

        forgotpass.setVisibility(View.INVISIBLE);

        //if (admin_but.isChecked()==false){

        // }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean a = validateEmail(email);
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {

                    Toast.makeText(MainActivity.this, "Enter Required Data!", Toast.LENGTH_SHORT).show();
                }
                else if (email.getText().toString().contains(" ")){
                    Toast.makeText(MainActivity.this, "Email Contains Spaces!", Toast.LENGTH_SHORT).show();
                }
                else if(a!=true){
                    Toast.makeText(MainActivity.this, "Enter Valid Email Address! ", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().length() < 8){
                    Toast.makeText(MainActivity.this, "Password cannot be less than 8 characters", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().contains(" ")){
                    Toast.makeText(MainActivity.this, "Password Contains Spaces!", Toast.LENGTH_SHORT).show();
                }
                else{

                    if(admin_but.isChecked())
                    {
                        forgotpass.setVisibility(View.INVISIBLE);
                        phone="+919137242482";
                        pswd="abcde12345";
                        forgotpass.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sendSMS(phone, pswd);
                            }
                        });
                        if(email.getText().toString().equals("hawaiza1234@gmail.com") && password.getText().toString().equals("abcde12345")) {
                            Toast.makeText(MainActivity.this, "Login Successful! ", Toast.LENGTH_SHORT).show();

                            Intent adduser = new Intent(getApplicationContext(), Admin_Home.class);
                            startActivity(adduser);
                            email.setText("");
                            password.setText("");


                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Login Denied! ", Toast.LENGTH_SHORT).show();
                            forgotpass.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        forgotpass.setVisibility(View.INVISIBLE);

                        db = new UsersDBHelper(MainActivity.this);
                        Cursor cursor = db.GetUsersLogin(email.getText().toString());
                        if (cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            userMobile = (cursor.getString(2));
                            phone = "+91" + userMobile;
                            pswd = (cursor.getString(5));

                            forgotpass.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    sendSMS(phone, pswd);
                                }
                            });

                            if(password.getText().toString().equals(pswd)) {
                                Toast.makeText(MainActivity.this, "Login Successful! ", Toast.LENGTH_SHORT).show();
                                email.setText("");
                                password.setText("");
                                Intent userHome = new Intent(getApplicationContext(), UserhomeActivity.class);
                                startActivity(userHome);
                            }else{
                                Toast.makeText(MainActivity.this, "Login Denied! ", Toast.LENGTH_SHORT).show();
                                forgotpass.setVisibility(View.VISIBLE);
                            }


                        }

                    }
                }
            }

        });
    }

  /*  public void forgotPassword(View v)
    {
        sendSMS(phone,pswd);
    }  */


    public void sendSMS(String phone, String pswd) {
        SmsManager smsManager = SmsManager.getDefault();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                String[] permissions = {Manifest.permission.SEND_SMS};
                requestPermissions(permissions, 1);
            } else {
                smsManager.sendTextMessage(phone, null, "Your Trackery password is \n" +pswd+ "\nDo not share this with anyone.", null, null);
                Toast t = Toast.makeText(getApplicationContext(), "An SMS has been sent to your Registered Mobile No.", Toast.LENGTH_LONG);
                t.show();
            }
        }
    }


}

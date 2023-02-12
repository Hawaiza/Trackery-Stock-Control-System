package com.example.trackery;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Add_Users extends AppCompatActivity {
    private EditText name,mobile,email,designation,password;
    private Button b;
    ImageButton back;
    String emailAddress;
    String userMobile, userEmail;
    public String pswd, phone, emailid;
    UsersDBHelper dbHandler;



    public void sendSMS(String phone, String pswd, String emailid) {
        SmsManager smsManager = SmsManager.getDefault();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.SEND_SMS};
                requestPermissions(permissions, 1);
                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
            } else {
                smsManager.sendTextMessage(phone, null, "Your Trackery Email-Id/Username: \n" +emailid +" Password: \n" +pswd+ "\nDo not share this with anyone.", null, null);
                Toast t = Toast.makeText(getApplicationContext(), "An SMS has been sent to the Users Mobile No.", Toast.LENGTH_LONG);
                t.show();
            }
        }
    }

    private Boolean validateEmail(EditText email) {
        emailAddress = email.getText().toString();
        if (Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_users);

        Intent intent=getIntent();      //calls the intent from previous activity

        name=(EditText) findViewById(R.id.addname);
        mobile=(EditText) findViewById(R.id.addmobile);
        email=(EditText) findViewById(R.id.addemail);
        designation=(EditText) findViewById(R.id.addDesignation);
        password=(EditText) findViewById(R.id.addPassword);
        b=(Button) findViewById(R.id.adduserbutton);
        back = (ImageButton)findViewById(R.id.back);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sv = new Intent(Add_Users.this,Users.class);
                sv.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(sv);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean a = validateEmail(email);
                if (email.getText().toString().isEmpty() && password.getText().toString().isEmpty() && mobile.getText().toString().isEmpty() && name.getText().toString().isEmpty() && designation.getText().toString().isEmpty() ) {
                    Toast.makeText(Add_Users.this, "Enter Required Data!", Toast.LENGTH_SHORT).show();
                }
                else if (mobile.getText().length() != 10){
                    Toast.makeText(Add_Users.this, "Mobile No. should contain only 10 characters", Toast.LENGTH_SHORT).show();
                }
                else if (email.getText().toString().contains(" ")){
                    Toast.makeText(Add_Users.this, "Email Contains Spaces!", Toast.LENGTH_SHORT).show();
                }
                else if(a!=true){
                    Toast.makeText(Add_Users.this, "Enter Valid Email Address! ", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().length() < 8){
                    Toast.makeText(Add_Users.this, "Password cannot be less than 8 characters", Toast.LENGTH_SHORT).show();
                }
                else if (mobile.getText().toString().contains(" ")){
                    Toast.makeText(Add_Users.this, "Mobile No. Contains Spaces!", Toast.LENGTH_SHORT).show();
                }
                else if (designation.getText().toString().contains(" ")){
                    Toast.makeText(Add_Users.this, "Designation Contains Spaces!", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().contains(" ")){
                    Toast.makeText(Add_Users.this, "Password Contains Spaces!", Toast.LENGTH_SHORT).show();
                }
                else if(name.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Users.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                }
                else if(mobile.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Users.this, "Please enter Mobile No.", Toast.LENGTH_SHORT).show();
                }
                else if(email.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Users.this, "Please enter Email Id", Toast.LENGTH_SHORT).show();
                }
                else if(designation.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Users.this, "Please enter Designation", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Users.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    String u_Name = name.getText().toString();
                    String u_Mobile = mobile.getText().toString();
                    String u_email = email.getText().toString();
                    String u_des = designation.getText().toString();
                    String u_pass = password.getText().toString();

                    dbHandler = new UsersDBHelper(Add_Users.this);
                    Cursor cursor = dbHandler.GetMobileNo(u_Mobile);

                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        userMobile = (cursor.getString(2));
                      //  phone = "+91" + userMobile;
                      //  pswd = (cursor.getString(5));
                      //  emailid=(cursor.getString(3));

                        if ((u_Mobile.contains(userMobile))) {
                            Toast.makeText(getApplicationContext(), "Mobile Number is already registered by another User", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Cursor res = dbHandler.GetEmail(u_email);
                        if (res.getCount() > 0) {
                            res.moveToFirst();
                            userMobile = (res.getString(2));
                         //   phone = "+91" + userMobile;
                         //   pswd = (res.getString(5));
                          //  userEmail = (res.getString(3));
                           // emailid=userEmail;
                            if ((u_email.contains(userEmail))) {
                                Toast.makeText(getApplicationContext(), "Email Id is already registered by another User", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            dbHandler.insertUserDetails(u_Name, u_Mobile, u_email, u_des, u_pass);
                            phone = "+91" + u_Mobile;
                            pswd = u_pass;
                            emailid=u_email;
                            Toast.makeText(getApplicationContext(), "Details Inserted Successfully", Toast.LENGTH_SHORT).show();
                            sendSMS(phone, pswd, emailid);
                            Intent intent = new Intent(Add_Users.this, Users.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }
}
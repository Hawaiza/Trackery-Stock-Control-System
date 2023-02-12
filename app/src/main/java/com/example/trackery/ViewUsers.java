package com.example.trackery;

import static java.lang.Integer.parseInt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewUsers extends AppCompatActivity {

    EditText viewid, viewname, viewmobile, viewemail, viewDesignation, viewPassword;
    ImageButton edituser, deleteuser;
    Button update;
    UsersDBHelper db;
    String emailAddress;
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
        setContentView(R.layout.view_users);

        viewid=(EditText) findViewById(R.id.viewid);
        viewname=(EditText) findViewById(R.id.viewname);
        viewmobile=(EditText) findViewById(R.id.viewmobile);
        viewemail=(EditText) findViewById(R.id.viewemail);
        viewDesignation=(EditText) findViewById(R.id.viewDesignation);
        viewPassword=(EditText) findViewById(R.id.viewPassword);
        update=(Button) findViewById(R.id.updateuserbutton);
        edituser=(ImageButton) findViewById(R.id.edituser);
        deleteuser=(ImageButton) findViewById(R.id.deleteuser);
        db = new UsersDBHelper(getApplicationContext());



        viewid.setFocusableInTouchMode(false);
        viewname.setFocusableInTouchMode(false);
        viewmobile.setFocusableInTouchMode(false);
        viewemail.setFocusableInTouchMode(false);
        viewDesignation.setFocusableInTouchMode(false);
        viewPassword.setFocusableInTouchMode(false);
        update.setVisibility(View.INVISIBLE);

        Intent intent= getIntent();
        String UserID=intent.getStringExtra("UserID");
        int id=parseInt(UserID);

        Cursor res = db.GetSingleUser(id);
        if (res.getCount() > 0) {
            res.moveToFirst();
            viewid.setText(res.getString(0));
            viewname.setText(res.getString(1));
            viewmobile.setText(res.getString(2));
            viewemail.setText(res.getString(3));
            viewDesignation.setText(res.getString(4));
            viewPassword.setText(res.getString(5));
        }

        edituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewname.setFocusableInTouchMode(true);
                viewmobile.setFocusableInTouchMode(true);
                viewemail.setFocusableInTouchMode(true);
                viewDesignation.setFocusableInTouchMode(true);
                viewPassword.setFocusableInTouchMode(true);
                update.setVisibility(View.VISIBLE);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean a = validateEmail(viewemail);
                        if (viewemail.getText().toString().isEmpty() && viewPassword.getText().toString().isEmpty() && viewmobile.getText().toString().isEmpty() && viewname.getText().toString().isEmpty() && viewDesignation.getText().toString().isEmpty() ) {
                            Toast.makeText(ViewUsers.this, "Enter Required Data!", Toast.LENGTH_SHORT).show();
                        }
                        else if (viewPassword.getText().length() < 8){
                            Toast.makeText(ViewUsers.this, "Password cannot be less than 8 characters", Toast.LENGTH_SHORT).show();
                        }
                        else if (viewemail.getText().toString().contains(" ")){
                            Toast.makeText(ViewUsers.this, "Email Contains Spaces!", Toast.LENGTH_SHORT).show();
                        }
                        else if(a!=true){
                            Toast.makeText(ViewUsers.this, "Enter Valid Email Address! ", Toast.LENGTH_SHORT).show();
                        }
                        else if (viewmobile.getText().length() != 10){
                            Toast.makeText(ViewUsers.this, "Mobile No. should contain only 10 characters", Toast.LENGTH_SHORT).show();
                        }
                        else if (viewmobile.getText().toString().contains(" ")){
                            Toast.makeText(ViewUsers.this, "Mobile No. Contains Spaces!", Toast.LENGTH_SHORT).show();
                        }
                        else if (viewDesignation.getText().toString().contains(" ")){
                            Toast.makeText(ViewUsers.this, "Designation Contains Spaces!", Toast.LENGTH_SHORT).show();
                        }
                        else if (viewPassword.getText().toString().contains(" ")){
                            Toast.makeText(ViewUsers.this, "Password Contains Spaces!", Toast.LENGTH_SHORT).show();
                        }
                        else if (viewname.getText().toString().isEmpty()) {
                            Toast.makeText(ViewUsers.this, "Please enter name", Toast.LENGTH_SHORT).show();
                        }
                        else if (viewmobile.getText().toString().isEmpty()) {
                            Toast.makeText(ViewUsers.this, "Please enter Mobile No.", Toast.LENGTH_SHORT).show();
                        }
                        else if (viewemail.getText().toString().isEmpty()) {
                            Toast.makeText(ViewUsers.this, "Please enter Email Id", Toast.LENGTH_SHORT).show();
                        }
                        else if (viewDesignation.getText().toString().isEmpty()) {
                            Toast.makeText(ViewUsers.this, "Please enter Designation", Toast.LENGTH_SHORT).show();
                        }
                        else if (viewPassword.getText().toString().isEmpty()) {
                            Toast.makeText(ViewUsers.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String userid = viewid.getText().toString();
                            String uname = viewname.getText().toString();
                            String umobile = viewmobile.getText().toString();
                            String uemail = viewemail.getText().toString();
                            String udes = viewDesignation.getText().toString();
                            String upass = viewPassword.getText().toString();

                            boolean checkupdateuser = db.updateuser(userid, uname, umobile, uemail, udes, upass);
                            if (checkupdateuser == true) {
                                Toast.makeText(ViewUsers.this, "Data Updated!", Toast.LENGTH_SHORT).show();
                                viewname.setFocusable(false);
                                viewmobile.setFocusable(false);
                                viewemail.setFocusable(false);
                                viewDesignation.setFocusable(false);
                                viewPassword.setFocusable(false);
                                update.setVisibility(View.INVISIBLE);
                            } else {
                                Toast.makeText(ViewUsers.this, "Data Not Updated!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = viewid.getText().toString();
                AlertDialog.Builder a = new AlertDialog.Builder(ViewUsers.this);
                a.setMessage("Do you want to delete this User?")
                        .setTitle("Delete User")
                        .setIcon(R.drawable.deleteicon2)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db = new UsersDBHelper(getApplicationContext());
                                Users.db.deleteuser(userid);
                                Toast.makeText(ViewUsers.this, "User Deleted.", Toast.LENGTH_LONG).show();
                                Intent inte= new Intent(ViewUsers.this, Users.class);
                                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(inte);
                                finish();
                                //  ViewUsers.this.notify();

                                // Intent intent1= new Intent(getApplicationContext(),Users.class);
                                //startActivity(intent1);
                            }
                        });

                a.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ViewUsers.this, "Delete Operation Canceled.", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog c = a.create();
                c.show();
            }
        });


    }

}
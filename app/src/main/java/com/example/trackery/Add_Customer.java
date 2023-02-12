package com.example.trackery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Add_Customer extends AppCompatActivity {
    private EditText id,name,mobile,email;
    private Button b;
    CustomerDBHelper CDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customers);

        Intent intent=getIntent();

        id=(EditText) findViewById(R.id.addcustid);
        name=(EditText) findViewById(R.id.addcustname);
        mobile=(EditText) findViewById(R.id.addcustmobile);
        email=(EditText) findViewById(R.id.addcustemail);
        b=(Button) findViewById(R.id.addcustomerbutton);
        ImageButton back = (ImageButton)findViewById(R.id.back);
        CDB= new CustomerDBHelper(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sv = new Intent(Add_Customer.this,Customer.class);
                startActivity(sv);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Customer.this, "please enter ID", Toast.LENGTH_SHORT).show();
                }
                else if(name.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Customer.this, "please enter name", Toast.LENGTH_SHORT).show();
                }
                else if(mobile.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Customer.this, "please enter mobile no.", Toast.LENGTH_SHORT).show();
                }
                else if(email.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Customer.this, "please enter email", Toast.LENGTH_SHORT).show();
                }else {
                    String cu_id= id.getText().toString();
                    String cu_name= name.getText().toString();
                    String cu_mobile=mobile.getText().toString();
                    String cu_email= email.getText().toString();

                    Boolean insertCustData= CDB.insertCustData(cu_id,cu_name,cu_mobile,cu_email);
                    if (insertCustData==true){
                        Toast.makeText(Add_Customer.this, "Customer Data Inserted!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Add_Customer.this, "Customer Data Not Inserted!", Toast.LENGTH_SHORT).show();
                    }
                }

                }
        });


    }

}


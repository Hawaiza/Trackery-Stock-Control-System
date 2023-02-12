package com.example.trackery;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Update_Customer extends AppCompatActivity {
    private EditText id,name,mobile,email;
    private Button update;
    private ImageButton pencil, del;
    CustomerDBHelper CDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_customer);

        id=(EditText) findViewById(R.id.upCID);
        name=(EditText) findViewById(R.id.upCN);
        mobile=(EditText) findViewById(R.id.upCPH);
        email=(EditText) findViewById(R.id.upCE);
        update=(Button) findViewById(R.id.updatebtn);
        pencil=(ImageButton) findViewById(R.id.pencil);
        del=(ImageButton) findViewById(R.id.deletebtn);
        CDB= new CustomerDBHelper(this);

        id.setFocusable(false);

        Intent i= getIntent();
        String string_id= i.getStringExtra("CID");
        Cursor res= CDB.SingleCustomer(string_id);
        if (res.getCount()>0){
            res.moveToFirst();
            id.setText(res.getString(0));
            name.setText(res.getString(1));
            mobile.setText(res.getString(2));
            email.setText(res.getString(3));
        }
        id.setFocusableInTouchMode(false);
        name.setFocusableInTouchMode(false);
        mobile.setFocusableInTouchMode(false);
        email.setFocusableInTouchMode(false);

        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Update_Customer.this, "ENABLED EDITING", Toast.LENGTH_SHORT).show();
                name.setFocusableInTouchMode(true);
                mobile.setFocusableInTouchMode(true);
                email.setFocusableInTouchMode(true);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert= new AlertDialog.Builder(Update_Customer.this);
                alert.setMessage("Do you want to delete this customer?");
                alert.setTitle("Delete Customer");
                alert.setIcon(R.drawable.deleteicon2);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String CID= id.getText().toString();
                        Boolean delete= CDB.delete(CID);
                        if (delete==true){
                            Toast.makeText(Update_Customer.this, "Customer Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }else
                        {
                            Toast.makeText(Update_Customer.this, "Customer Not Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog a= alert.create();
                alert.show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String CID= id.getText().toString();
                    String CNAME= name.getText().toString();
                    String CMOBILE= mobile.getText().toString();
                    String CEMAIL= email.getText().toString();

                    Boolean checkupdatecust= CDB.updatecust(CID, CNAME, CMOBILE,CEMAIL);
                    if (checkupdatecust==true){
                        Toast.makeText(Update_Customer.this, "Customer Data Updated!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Update_Customer.this, "Customer Data Not Updated!", Toast.LENGTH_SHORT).show();

                    }
                }
        });


    }
}
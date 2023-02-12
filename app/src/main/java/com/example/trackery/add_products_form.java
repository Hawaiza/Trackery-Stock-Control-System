package com.example.trackery;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_products_form extends AppCompatActivity  {
    private EditText id,name,quantity,cost,category;
    private Button addproducts;
    private productDBHandler productDbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products_form);

        Intent intent=getIntent();      //calls the intent from previous activity

        id=(EditText) findViewById(R.id.addenterid);
        name=(EditText) findViewById(R.id.addentername);
        quantity=(EditText) findViewById(R.id.addenterquantity);
        cost=(EditText) findViewById(R.id.addentercost);
        category=(EditText) findViewById(R.id.addentercategory);
        addproducts=(Button) findViewById(R.id.addproductsbutton);

        productDbHandler = new productDBHandler(add_products_form.this);

        addproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Boolean i= productDbHandler.checkID(pid)

                if(id.getText().toString().isEmpty()) {
                    Toast.makeText(add_products_form.this, "Please enter id", Toast.LENGTH_SHORT).show();
                } else if (name.getText().toString().isEmpty()) {
                    Toast.makeText(add_products_form.this, "Please enter name", Toast.LENGTH_SHORT).show();
                }
                else if(quantity.getText().toString().isEmpty()) {
                    Toast.makeText(add_products_form.this, "Please enter quantity", Toast.LENGTH_SHORT).show();
                }
                else if(cost.getText().toString().isEmpty()) {
                    Toast.makeText(add_products_form.this, "Please enter cost", Toast.LENGTH_SHORT).show();
                }
                else if(category.getText().toString().isEmpty()) {
                    Toast.makeText(add_products_form.this, "Please enter category", Toast.LENGTH_SHORT).show();
                }
                /*else if (pid.getText().toString().equals(id)) {
                    Toast.makeText(add_products_form.this, "Above entered id already exists", Toast.LENGTH_SHORT).show();
                }*/
                else {
                    String pname=name.getText().toString();
                    String pquantity=quantity.getText().toString();
                    String pcost=cost.getText().toString();
                    String pcategory=category.getText().toString();
                    String pid=id.getText().toString();


                    productDbHandler.addnewproducts(pid,pname, pquantity, pcategory, pcost);

                    Toast.makeText(add_products_form.this, "product has been added", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    name.setText("");
                    quantity.setText("");
                    cost.setText("");
                    category.setText("");
                }
            }
               /* String pname=name.getText().toString();
                String pquantity=quantity.getText().toString();
                String pcost=cost.getText().toString();
                String pcategory=category.getText().toString();
                String pid=id.getText().toString();

                if(pname.isEmpty() && pquantity.isEmpty() && pcost.isEmpty() && pcategory.isEmpty() && pid.isEmpty()) {
                    Toast.makeText(add_products_form.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                    return;
                }
                productDbHandler.addnewproducts(pid,pname, pquantity, pcategory, pcost);

                Toast.makeText(add_products_form.this, "product has been added", Toast.LENGTH_SHORT).show();
                id.setText("");
                name.setText("");
                quantity.setText("");
                cost.setText("");
                category.setText("");
            }*/
        });

    }
}
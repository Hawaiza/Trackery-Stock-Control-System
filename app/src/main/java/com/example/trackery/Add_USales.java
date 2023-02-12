package com.example.trackery;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Add_USales extends AppCompatActivity {
    USales_Home salesHome;
    private EditText sid,sdate,cid,pid,sqty,scost;
    private Button b;
    USaleDBHelper DB;
    productDBHandler PDB;
    CustomerDBHelper CDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_usales);
        DB = new USaleDBHelper(this);
        PDB = new productDBHandler(this);
        CDB = new CustomerDBHelper(this);


        sid=(EditText) findViewById(R.id.addSID);
        sdate=(EditText) findViewById(R.id.adddate);
        cid=(EditText) findViewById(R.id.addCID);
        pid=(EditText) findViewById(R.id.addPID);
        sqty=(EditText) findViewById(R.id.addQA);
    //    scost=(EditText) findViewById(R.id.addCOST);

        b=(Button) findViewById(R.id.adduserbutton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sid.getText().toString().isEmpty()) {
                    Toast.makeText(Add_USales.this, "please enter sales_id", Toast.LENGTH_SHORT).show();
                } else if (sdate.getText().toString().isEmpty()) {
                    Toast.makeText(Add_USales.this, "please enter date", Toast.LENGTH_SHORT).show();
                } else if (cid.getText().toString().isEmpty()) {
                    Toast.makeText(Add_USales.this, "please enter customer_id", Toast.LENGTH_SHORT).show();
                } else if (pid.getText().toString().isEmpty()) {
                    Toast.makeText(Add_USales.this, "please enter product_id", Toast.LENGTH_SHORT).show();
                } else if (sqty.getText().toString().isEmpty()) {
                    Toast.makeText(Add_USales.this, "please enter quantity", Toast.LENGTH_SHORT).show();
                } //else if (scost.getText().toString().isEmpty()) {
                //  Toast.makeText(Add_USales.this, "please enter cost", Toast.LENGTH_SHORT).show();
                //}
                else {

                    //sales s = new sales();
                    String s_id = sid.getText().toString();
                    String s_date = sdate.getText().toString();
                    String c_id = cid.getText().toString();
                    String p_id = pid.getText().toString();
                    String s_qty = sqty.getText().toString();
                    //String s_cost = scost.getText().toString();

 /*s.setDate(Integer.parseInt(sdate.getText().toString()));
 s.setCust_id(Integer.parseInt(cid.getText().toString()));
 s.setProd_id(Integer.parseInt(pid.getText().toString()));
 s.setQty(Integer.parseInt(sqty.getText().toString()));
 s.setCost(Integer.parseInt(scost.getText().toString()));*/
                    Cursor cursor = PDB.checkproid(p_id);
                    Cursor cursor1 = CDB.checkcusid(c_id);
                    Integer s_cost;
                    Integer pavai;
                    Integer u_qty;
                    if (cursor1.getCount() == 0) {
                       Toast.makeText(Add_USales.this, "Customer does not exist!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (cursor.getCount() == 0) {
                       Toast.makeText(Add_USales.this, "product does not exist!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        cursor.moveToFirst();
                        pavai = Integer.valueOf(cursor.getString(2));
                        if (pavai == 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Add_USales.this);
                            builder.setTitle("Sorry!");
                            builder.setMessage("This product is not available.");
                            builder.setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            return;
                        } else {
                            s_cost = Integer.valueOf(cursor.getString(4)) * Integer.valueOf(s_qty);
                            u_qty = Integer.valueOf(cursor.getString(2)) - Integer.valueOf(s_qty);
                        }
                    }
                    Cursor c = DB.checksaleid(s_id);
                    if (c.getCount() > 0) {
                        Toast.makeText(Add_USales.this, "This SaleID already exists!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        boolean checkinsertsale = DB.insertsale(s_id, s_date, c_id, p_id, s_qty, String.valueOf(s_cost));
                        if (checkinsertsale == true) {
                            Toast.makeText(Add_USales.this, "New Data Inserted!", Toast.LENGTH_SHORT).show();
                            //salesHome.salelist.clear
                            //salesHome.setSaleAdapter();
                            PDB.updateqty(p_id, String.valueOf(u_qty));
                            finish();
                        } else {
                            Toast.makeText(Add_USales.this, "New Data Not Inserted!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

        });
    }


   /* private void savedata() {

        int cid = Integer.parseInt(String.valueOf(editcid.getText()));
        int pid = Integer.parseInt(String.valueOf(editpid.getText()));

        int id = sales.salesArrayList.size();
        sales s2 = new sales(id,cid,pid);
        sales.salesArrayList.add(s2);
        finish();
    }*/


}
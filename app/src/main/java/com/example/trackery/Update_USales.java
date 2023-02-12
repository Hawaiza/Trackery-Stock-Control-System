package com.example.trackery;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Update_USales extends AppCompatActivity {
    private EditText sid,sdate,cid,pid,sqty,scost;
    private Button b;
    private ImageButton pencil,d;
    USaleDBHelper DB;
    productDBHandler PDB;
    CustomerDBHelper CDB;
    String s_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_usales);
        DB = new USaleDBHelper(this);
        PDB = new productDBHandler(this);
        CDB = new CustomerDBHelper(this);

      //  editsale();

        //Intent intent=getIntent();      //calls the intent from previous activity

        sid=(EditText) findViewById(R.id.addSID);
        sdate=(EditText) findViewById(R.id.adddate);
        cid=(EditText) findViewById(R.id.addCID);
        pid=(EditText) findViewById(R.id.addPID);
        sqty=(EditText) findViewById(R.id.addQA);
        scost=(EditText) findViewById(R.id.addCOST);
        b=(Button) findViewById(R.id.updatebtn);
        pencil = (ImageButton) findViewById(R.id.pencil);
        d = (ImageButton) findViewById(R.id.deletebtn);


        s_id = getIntent().getExtras().getString("sid");

        //String s_date=getIntent().getExtras().getString("sdate");
        //String c_id=getIntent().getExtras().getString("cid");

        sid.setText(s_id);
       // sdate.setText(s_date);
        //cid.setText(c_id);

       // Intent i = getIntent();
        //String string_sid = i.getStringExtra("said");
        //int in_sid = parseInt(string_sid);
        setsinglesale();

        //sid.setFocusable(false);
        //sdate.setFocusable(false);
       // sdate.setEnabled(false);
        //sdate.setClickable(false);
      //  cid.setFocusable(false);
        //pid.setFocusable(false);
        //sqty.setFocusable(false);
        //scost.setFocusable(false);
       // b.setVisibility(View.INVISIBLE);
        setfocus();


        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sdate.setFocusableInTouchMode(true);
                //sdate.setEnabled(true);
                cid.setFocusableInTouchMode(true);
                pid.setFocusableInTouchMode(true);
                sqty.setFocusableInTouchMode(true);
                scost.setFocusableInTouchMode(true);
                b.setVisibility(View.VISIBLE);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_id = sid.getText().toString();
                String s_date = sdate.getText().toString();
                String c_id = cid.getText().toString();
                String p_id = pid.getText().toString();
                String s_qty = sqty.getText().toString();
               // String s_cost = scost.getText().toString();

                Cursor cursor = PDB.checkproid(p_id);
                Cursor cursor1 = CDB.checkcusid(c_id);
                Integer s_cost;
                Integer pavai;
                Integer u_qty;

                if (cursor1.getCount() == 0) {
                    Toast.makeText(Update_USales.this, "Customer does not exist!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (cursor.getCount() == 0){
                    Toast.makeText(Update_USales.this, "product does not exist!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    cursor.moveToFirst();
                    pavai = Integer.valueOf(cursor.getString(2));
                    if (pavai == 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Update_USales.this);
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
                Boolean checkforupdate = DB.updatesales(s_id,s_date,c_id,p_id,s_qty, String.valueOf(s_cost));
                if(checkforupdate == true){
                    Toast.makeText(Update_USales.this, "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
                    setsinglesale();
                    setfocus();
                    PDB.updateqty(p_id, String.valueOf(u_qty));
                    // finish();
                }
                else{
                    Toast.makeText(Update_USales.this, "Update Failed!", Toast.LENGTH_SHORT).show();

                }
            }

        });


        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder a=new AlertDialog.Builder(Update_USales.this);
                a.setMessage("Do you want to delete this User?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String s_id = sid.getText().toString();
                                Boolean checkfordlt = DB.deletesale(s_id);
                                if(checkfordlt == true){
                                    Toast.makeText(Update_USales.this, "Data Deleted Successfully!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else{
                                    Toast.makeText(Update_USales.this, "Delete Failed!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setTitle("Delete User")
                        .setIcon(R.drawable.deleteicon2)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Update_USales.this, "Delete Failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog c=a.create();
                c.show();
            }
        });


    }

    private void setsinglesale() {
        Cursor res = DB.GetSingleSale(Integer.parseInt(s_id));
        if (res.getCount() > 0) {
            res.moveToFirst();
            sid.setText(res.getString(0));
            sdate.setText(res.getString(1));
            cid.setText(res.getString(2));
            pid.setText(res.getString(3));
            sqty.setText(res.getString(4));
            scost.setText(res.getString(5));
        }
    }

    public void  setfocus(){
       sid.setFocusable(false);
        sdate.setFocusable(false);
        cid.setFocusable(false);
        pid.setFocusable(false);
        sqty.setFocusable(false);
        scost.setFocusable(false);
        b.setVisibility(View.INVISIBLE);
    }
   /*public void dltsale(String id){

        String s_id = sid.getText().toString();

    }*/

}
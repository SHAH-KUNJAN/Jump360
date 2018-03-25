package com.example.abc.contactsjump360;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Detailview extends AppCompatActivity {

    private Contactlist contact;
    TextView detnm,detno1,detno2,detemail,detaddress;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailview);

        FloatingActionButton deletebtn=(FloatingActionButton)findViewById(R.id.btndelete);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              deleteRecord();
            }
        });


        detnm=(TextView) findViewById(R.id.detailname);
        detno1=(TextView)findViewById(R.id.detailno1);
        detno2=(TextView)findViewById(R.id.detailno2);
        detemail=(TextView)findViewById(R.id.detailemail);
        detaddress=(TextView)findViewById(R.id.detailaddress);

        if(getIntent().getSerializableExtra("contact")!=null){
            contact = (Contactlist) getIntent().getSerializableExtra("contact");
            key = getIntent().getStringExtra("key");
           /* Log.v("details",key + " " + contact.getUsername());*/
            detnm.setText(contact.getUsername());
            detno1.setText(contact.getUserno1());
            if(contact.getUserno2()!=null)
            {
                detno2.setText(contact.getUserno2());
            }
            if(contact.getEmail()!=null) {
                detemail.setText(contact.getEmail());
            }
            if(contact.getAddress()!=null) {
                detaddress.setText(contact.getAddress());
            }

        }

    }

    private void deleteRecord()
    {
        key = getIntent().getStringExtra("key");
        DatabaseReference deleterecord= FirebaseDatabase.getInstance().getReference("KUNJAN").child(key);
        deleterecord.removeValue();
        Toast.makeText(this,"your record id Delete",Toast.LENGTH_SHORT).show();
        finish();

    }

    public void openEditActivity(View view) {
        Intent editActivity = new Intent(this,AddContact.class);
        editActivity.putExtra("IS_EDITING",true);
        editActivity.putExtra("Data",contact);
        editActivity.putExtra("key",key);
        startActivity(editActivity);
    }
}

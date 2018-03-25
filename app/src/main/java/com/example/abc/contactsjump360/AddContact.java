package com.example.abc.contactsjump360;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddContact extends AppCompatActivity {

	private EditText edtUsername, edtUsernumber1,edtUsernumber2,edtaddress,edtemail;
	private Button btnAdd, btnCancel;

    FirebaseDatabase database ;
    DatabaseReference myData ;
    private Contactlist contact;
    private boolean isEditing;
    private String key;
FirebaseAuth mAuth;
FirebaseUser user;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.addcontact);

		edtUsername = (EditText) findViewById(R.id.editUsername);
		edtUsernumber1 = (EditText) findViewById(R.id.editUsernumber1);
		edtUsernumber2 = (EditText) findViewById(R.id.editUsernumber2);
		edtaddress = (EditText) findViewById(R.id.editAddress);
		edtemail=(EditText) findViewById(R.id.editEmail);

		btnAdd = (Button) findViewById(R.id.buttonAdd);
		btnCancel = (Button) findViewById(R.id.buttonCancel);
        database = FirebaseDatabase.getInstance();

        if(getIntent().getBooleanExtra("IS_EDITING",false)){
            contact = (Contactlist) getIntent().getSerializableExtra("Data");
            isEditing = true;
            btnAdd.setText("Update");
            edtUsername.setText(contact.getUsername());
            edtUsernumber1.setText(contact.getUserno1());
            edtUsernumber2.setText(contact.getUserno2());
            edtemail.setText(contact.getEmail());
            edtaddress.setText(contact.getAddress());
            key = getIntent().getStringExtra("key");
        }

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        myData= database.getReference(user.getUid());


		btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addcontactlist();
            }
        });
		btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                edtUsernumber2.setText("");
                edtUsernumber1.setText("");
                edtUsername.setText("");
                edtemail.setText("");
                edtaddress.setText("");
                finish();
                startActivity(new Intent(AddContact.this,MainActivity.class));
            }
        });


	}

	private void addcontactlist()
    {
        String name=edtUsername.getText().toString().trim();
        String no1=edtUsernumber1.getText().toString().trim();
        String no2=edtUsernumber2.getText().toString().trim();
        String email=edtemail.getText().toString().trim();
        String address=edtaddress.getText().toString().trim();

       if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please fill Name fields...",
                    Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(no1)){
            Toast.makeText(this, "Please fill Number fields...",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(!isEditing)
                key = myData.push().getKey();
            Contactlist con = new Contactlist(name, email, address, no1, no2);
            myData.child(key).setValue(con);
            Toast.makeText(this, "Contact Data Save...",
                    Toast.LENGTH_SHORT).show();
            edtUsernumber2.setText("");
            edtUsernumber1.setText("");
            edtUsername.setText("");
            edtemail.setText("");
            edtaddress.setText("");
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }



    }
}

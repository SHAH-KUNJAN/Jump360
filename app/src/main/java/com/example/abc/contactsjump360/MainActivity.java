package com.example.abc.contactsjump360;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    DatabaseReference myData ;
    LinearLayout l1;
FirebaseUser user;
   ListView listViewcontact;
   List<Contactlist> condisp;
   List<String> conkeys;
    FirebaseAuth mAuth;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        user =  mAuth.getCurrentUser();
        myData= FirebaseDatabase.getInstance().getReference(user.getUid());

        listViewcontact=(ListView) findViewById(R.id.listViewcontact);
        l1=(LinearLayout) findViewById(R.id.lay1);

        condisp =new ArrayList<>();
        conkeys =new ArrayList<>();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddContact.class));
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        myData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                condisp.clear();
                conkeys.clear();

                for(DataSnapshot dispsnapshot : dataSnapshot.getChildren()){
                    Contactlist cont=dispsnapshot.getValue(Contactlist.class);
                    condisp.add(cont);
                    conkeys.add(dispsnapshot.getKey());
                }
                DisplayList adapter=new DisplayList(MainActivity.this, condisp,conkeys);
                listViewcontact.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       switch (item.getItemId())
       {
           case R.id.logout:
               FirebaseAuth.getInstance().signOut();
               finish();
               startActivity(new Intent(this,Login.class));
               break;
       }

        return super.onOptionsItemSelected(item);
    }
}

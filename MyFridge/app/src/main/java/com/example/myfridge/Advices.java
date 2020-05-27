package com.example.myfridge;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Advices extends AppCompatActivity {

    TextView link1;
    TextView link2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id =item.getItemId();

        if(id == R.id.homepage){
            Intent myintent = new Intent(Advices.this, MainActivity.class);
            startActivity(myintent);
            return true;
        }
        if(id==R.id.add_item){
            Intent myintent = new Intent(Advices.this, AddProducts.class);
            startActivity(myintent);
            return true;
        }
        if(id==R.id.suggestions){
            Intent myintent = new Intent(Advices.this, Advices.class);
            startActivity(myintent);
            return true;
        }
        if(id==R.id.settings){
            Toast.makeText(this," Jo valid në këtë version! ", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //BACK BUTTON!!!

        link1 = findViewById(R.id._link1);
        link2 = findViewById(R.id._link2);

        link1.setMovementMethod(LinkMovementMethod.getInstance());
        link2.setMovementMethod(LinkMovementMethod.getInstance());
    }
}

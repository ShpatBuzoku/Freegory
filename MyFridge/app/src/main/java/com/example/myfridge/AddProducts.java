package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class AddProducts extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    DatabaseHelper db;
    Button add_data;
    EditText add_product;
    TextView showExpDate;

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
            Intent myintent = new Intent(AddProducts.this, MainActivity.class);
            startActivity(myintent);
            return true;
        }
        if(id==R.id.add_item){
            Intent myintent = new Intent(AddProducts.this, AddProducts.class);
            startActivity(myintent);
            return true;
        }
        if(id==R.id.suggestions){
            Intent myintent = new Intent(AddProducts.this, Advices.class);
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
        setContentView(R.layout.activity_add_products);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //BACK BUTTON!!!

        db = new DatabaseHelper(this);

        add_data=findViewById(R.id.add_data);
        add_product = findViewById(R.id.add_product);
        showExpDate = findViewById(R.id.exp_date);


        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product = add_product.getText().toString();
                String expirydate = showExpDate.getText().toString();
                if(!product.equals("") && !expirydate.equals("") && db.insertData(product,expirydate)){
                    Toast.makeText(AddProducts.this, "E dhëna u shtua!", Toast.LENGTH_SHORT).show();
                    add_product.setText("");
                    backToProductListView();
                }else{
                    Toast.makeText(AddProducts.this, "E dhëna nuk u shtua!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
    }

    public void backToProductListView(){
        Intent intent = new Intent(this,ShowProductsList.class);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
         int y = year; int m = month; int d = dayOfMonth;
         showExpDate.setText(d+"/"+(m+1)+"/"+y);
    }
}

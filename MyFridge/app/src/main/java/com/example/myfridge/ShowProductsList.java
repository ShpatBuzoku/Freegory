package com.example.myfridge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowProductsList extends AppCompatActivity {

    DatabaseHelper db;

    Button add_data;
    EditText add_product;

    ListView productsList;
    ArrayList<Product> listItem;
    Product product;

    TwoColumns_ListAdapter adapter;
   // ArrayAdapter adapter;
    //public int totalProducts;
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
            Intent myintent = new Intent(ShowProductsList.this, MainActivity.class);
            startActivity(myintent);
            return true;
        }
        if(id==R.id.add_item){
            Intent myintent = new Intent(ShowProductsList.this, AddProducts.class);
            startActivity(myintent);
            return true;
        }
        if(id==R.id.suggestions){
            Intent myintent = new Intent(ShowProductsList.this, Advices.class);
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
        setContentView(R.layout.activity_products_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //BACK BUTTON!!!

        db = new DatabaseHelper(this);
        listItem = new ArrayList<>();
       // totalProducts = listItem.size();


        add_data=findViewById(R.id.add_data);
        add_product = findViewById(R.id.add_product);
        productsList = findViewById(R.id.products_list);

        viewData();

        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = productsList.getItemAtPosition(position).toString();
                Toast.makeText(ShowProductsList.this,""+text,Toast.LENGTH_SHORT).show();
            }
        });

        productsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int which_item = position;

                new AlertDialog.Builder(ShowProductsList.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle(" Jeni të sigurtë? ")
                        .setMessage(" Doni të fshini këtë produkt? ")
                        .setPositiveButton(" Po ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               // Product selectedProduct = listItem.get(which_item);
                                db.deleteData(listItem.get(which_item));
                                listItem.remove(which_item);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(" Jo ",null)
                        .show();

                return true;
            }
        });

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddProducts();
            }
        });
    }

    public void openAddProducts(){
        Intent intent = new Intent(this,AddProducts.class);
        startActivity(intent);
    }

    private void viewData() {
        Cursor cursor = db.viewData();

        if(cursor.getCount() == 0){
            Toast.makeText(this,"Nuk ka asnjë produkt në frigoriferin tuaj! ", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                product = new Product(cursor.getString(1),cursor.getString(2)); //index 1 is name, index 0 is ID
                listItem.add(product);
            }

            adapter = new TwoColumns_ListAdapter(this, R.layout.activity_adapter_productview,listItem);
            productsList.setAdapter(adapter);
        }
    }
}

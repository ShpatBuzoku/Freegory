package com.example.myfridge;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

   // ShowProductsList showProductsNumber;

    Button openFridge;
    Button shoppingList;
    ArrayList<Product> totalProducts;
    Product product;
    DatabaseHelper db;

    TextView totalProductsLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startNotificationReceiver();
        createNotificationChannel();

        openFridge=findViewById(R.id.openFridge);
        shoppingList = findViewById(R.id.shoppingList);
        totalProductsLabel = findViewById(R.id.total_products_lbl);

        db = new DatabaseHelper(this);
        totalProducts = new ArrayList<>();

        viewTotalProducts();

        openFridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFridgePage();
            }
        });

        shoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, " Jo valid në këtë version! ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openFridgePage(){
        Intent intent = new Intent(this,ShowProductsList.class);
        startActivity(intent);
    }

    private void viewTotalProducts(){
        Cursor cursor = db.viewData();

        if(cursor.getCount() == 0){
            totalProductsLabel.setText(" Momentalisht nuk keni asnjë produkt në  frigorifer! ");
        }
        else{
            while(cursor.moveToNext()){
                product = new Product(cursor.getString(1),cursor.getString(2)); //index 1 is name, index 0 is ID
                totalProducts.add(product);
            }
            totalProductsLabel.setText(" Ju keni "+totalProducts.size()+" produkte ne frigorifer! ");
        }
    }

    private void startNotificationReceiver(){

        Intent intent = new Intent(MainActivity.this,ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,17);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void createNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "NotificationChannel";
            String description = "Channel for Fridge Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription(description);
        }
    }
}

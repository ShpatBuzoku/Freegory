package com.example.myfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Testing.db";
    private static final String DB_TABLE = "Test";
    //private static final String TEST ="Table";
    //Columns
    private static final String ID = "ID";
    private static final String PRODUCT = "PRODUCT";
    private static final String EXPIRY_DATE = "EXPIRY_DATE";

    private static final String CREATE_TABLE = "CREATE TABLE "+DB_TABLE+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ PRODUCT+" TEXT,"+EXPIRY_DATE+" TEXT )";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL(CREATE_TABLE);
        db.execSQL("create table "+DB_TABLE+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,PRODUCT TEXT,EXPIRY_DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);

        onCreate(db);
    }

    //method for insterting data.
    public boolean insertData(String product, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PRODUCT, product);
        contentValues.put(EXPIRY_DATE,date);

        long result = db.insert(DB_TABLE, null, contentValues);

        return result != -1;  //nese result  =-1 ateher nuk insertohet!
    }

    //method for deleting data.
    public Integer deleteData(Product prd){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DB_TABLE, "PRODUCT = ?",new String[] {prd.getName()});
    }

    //method to view data
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select*from "+DB_TABLE;
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }
}

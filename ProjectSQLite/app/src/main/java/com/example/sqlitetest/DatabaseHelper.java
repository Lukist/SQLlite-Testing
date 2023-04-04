package com.example.sqlitetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //////////////////////////////////////  DECLARACION DE VARIABLES //////////////////////////////////////
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String COLUMN_ID = "ID";


    //////////////////////////////////////////// METODOS //////////////////////////////////////////////////


    //constructor para crear una instancia de la clase
    public DatabaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    //Este metodo es llamado cada vez que se accede por primera vez a la base de datos.
    //Dentro debe haber codigo para crear una base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_AGE + " INT, " + COLUMN_ACTIVE_CUSTOMER + " BOOL)";


        db.execSQL(createTableStatement);
    }

    //------------------------------------------------------------------------------------------------------

    // Esto se llama cada vez que la version de la base de datos cambian.
    // Esto previene que los nuevos usarios crasheen la app al introducior nuevos formatos de tablas
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    //-------------------------------------------------------------------------------------------------------


    //metodo añadido por el programador que añade un registro de customerModel a la base de datos
    public boolean addOne(CustomerModel customerModel) {
        //clase especial que viene de la superclase SQLiteOpenHelper
        SQLiteDatabase db = this.getWritableDatabase();

        //Clase especial que nos permite guardar un par de variables juntas
        ContentValues cv = new ContentValues();

        //Guardamos las clases en el objeto cv
        cv.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE, customerModel.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER, customerModel.isActive());

        //insertamos los datos en la tabla y devolvera un -1 si es que a ocurrido un error en el proceso
        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }

    //--------------------------------------------------------------------------------------------------------------------------

    public List<CustomerModel> getEveryone() {
        List<CustomerModel> returnList = new ArrayList<>();

        //query para optener datos de la base de datos
        String query = "SELECT * FROM " +  CUSTOMER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        //cursor es un tipo de dato de la libreria sqlite que se utiliza para los resultados (resultsets) de las querys
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int customerId = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean isActive = cursor.getInt(3) == 1 ? true : false;

                CustomerModel newCustomer = new CustomerModel(customerId, customerName, customerAge, isActive);
                returnList.add(newCustomer);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return  returnList;
    }


    //------------------------------------------------------------------------------------------------------------------------------------------

    public boolean deleteOne(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("DELETE FROM %s WHERE %s = %s", CUSTOMER_TABLE, COLUMN_ID, customerModel.getId());

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            return true;
        }else {
            return false;
        }
    }
}

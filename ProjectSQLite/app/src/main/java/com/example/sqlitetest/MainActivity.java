package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //referencia a todos los elementos interactuables que se vayan a usar (los declara)
    Button btn_add, btn_viewAll;
    EditText cust_name, cust_age;
    Switch active_customer;
    ListView lv_customer;

    //-----------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------
    //funcion que inicia el programa
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //////////////////////////////  Asgnacion  ///////////////////////////////////////////

        //asigna los valores a los elementos interactuables declarados anteriormente
        //para ello usa el id en el archivo xml main_activity.xml

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewall);
        cust_name = findViewById(R.id.cust_name);
        cust_age = findViewById(R.id.cust_age);
        active_customer = findViewById(R.id.active_customer);
        lv_customer = findViewById(R.id.lv_customer);

        //////////////////////////////  Listeners   //////////////////////////////////////////


        //Acciones de los botones add y viewAll

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    CustomerModel newCustomer = new CustomerModel(-1, cust_name.getText().toString(), Integer.parseInt(cust_age.getText().toString()), active_customer.isChecked());
                    Toast.makeText(MainActivity.this, newCustomer.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    Toast.makeText(MainActivity.this, "A ocurrido un error", Toast.LENGTH_SHORT).show();
                }



            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "View All", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
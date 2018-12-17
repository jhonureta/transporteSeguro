package com.example.personal.transporteseguro;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personal.transporteseguro.bd.AdminSQliteOpenHelper;
import com.example.personal.transporteseguro.tablas.Conductor;
import com.example.personal.transporteseguro.tablas.Cursos;
import com.example.personal.transporteseguro.tablas.bienvenida;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class regitro2_taxi extends AppCompatActivity {
    CheckBox curs1,curs2,curs3,curs4,curs5,antce1,antec2;
    String cur1="",cur2="",cur3 = "", cur4 = "", cur5 ="";
    Button botonRegistro;
    EditText antec;
    Dialog myDialog;
    Button si, no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regitro2_taxi);

        try {
            curs1 = (CheckBox) findViewById(R.id.curso1);
            curs2 = (CheckBox) findViewById(R.id.curso2);
            curs3 = (CheckBox) findViewById(R.id.curso3);
            curs4 = (CheckBox) findViewById(R.id.curso4);
            curs5 = (CheckBox) findViewById(R.id.curso5);
            antce1 = (CheckBox) findViewById(R.id.antecedente_si);
            antec2 = (CheckBox) findViewById(R.id.antecedente_no);
            botonRegistro = (Button) findViewById(R.id.btnRegistroCursos);
            antec = (EditText) findViewById(R.id.txt_nombre);
            antec.setVisibility(View.GONE);
            myDialog = new Dialog(this);
            antce1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (antce1.isChecked()) {
                        antec2.setChecked(false);
                        antec.setVisibility(View.VISIBLE);
                    } else {
                        antce1.setChecked(false);
                        antec.setVisibility(View.GONE);
                    }
                }
            });
            antec2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (antec2.isChecked()) {

                        antce1.setChecked(false);
                        antec.setVisibility(View.GONE);

                    }
                }
            });



            cur1 = curs1.isChecked()?"Seguridad Indistrial":"";
            cur2 = curs2.isChecked()?"Seguridad Fisica":"";
            cur3 = curs3.isChecked()?"Seguridad al Cliente":"";
            cur4 = curs4.isChecked()?"Manejo Defensivo":"";
            cur5 = curs5.isChecked()?"Manejo Ofensivo":"";


            botonRegistro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                            registrar();
                            guardar();
                            Intent intent = new Intent(getApplicationContext(), menuTaxista.class);
                            startActivity(intent);

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR


                        moveTaskToBack(true);



        }
        return super.onKeyDown(keyCode, event);
    }


    public void alerta_vacio(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¡Alerta!")
                .setContentText("Ud marco 'Si' explique su antecedente")
                .show();
    }


    public void registrar(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "registro", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(bienvenida.CAMPO_ESTADO, "2");

        Long idResultante = db.insert(bienvenida.TABLA_BIENVENIDA, null, values);
        if (idResultante > 0) {


        }
    }


    public void guardar(){

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "cursos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Cursos.CAMPO_CURSO1, cur1);
        values.put(Cursos.CAMPO_CURSO2, cur2);
        values.put(Cursos.CAMPO_CURSO3, cur3);
        values.put(Cursos.CAMPO_CURSO4, cur4);
        values.put(Cursos.CAMPO_CURSO5, cur5);


        Long idResultante = db.insert(Cursos.TABLA_CURSOS, null, values);
        if (idResultante > 0) {

        } else {


        }
    }



}

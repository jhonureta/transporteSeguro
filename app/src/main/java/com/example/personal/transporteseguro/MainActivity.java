package com.example.personal.transporteseguro;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personal.transporteseguro.bd.AdminSQliteOpenHelper;
import com.example.personal.transporteseguro.tablas.Operadora;
import com.example.personal.transporteseguro.tablas.Parentesco;
import com.example.personal.transporteseguro.tablas.Persona;
import com.example.personal.transporteseguro.tablas.discapacidad;
import com.example.personal.transporteseguro.tablas.licencia;

public class MainActivity extends AppCompatActivity {


    ImageView image;//VARIABLE DE TIPO IMAGEN
    TextView text;//VARIABLE TE TIPO TEXVIEW
    Button  re;//VARIABLE DE TIPO BUTTON

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ENLAZANDO LAS VARIABLES DECLARADAS AL PRINCIPIO DE LA CLASE CON LOS ID DECLARADOS EN LA INTERFAZ
        image = (ImageView)findViewById(R.id.imageView);
        text = (TextView)findViewById(R.id.txt_mensaje);
        re = (Button)findViewById(R.id.button2);
        //FIN ENLAZANDO LAS VARIABLES DECLARADAS AL PRINCIPIO DE LA CLASE CON LOS ID DECLARADOS EN LA INTERFAZ**


        //METODO ONCLICK PARA PASAR ALA SIGUIENTE ACTIVIDAD
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), seleccion.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        //FIN METODO ONCLICK PARA PASAR ALA SIGUIENTE ACTIVIDAD****




        //REGISTRANDO  LAS OPERADORAS EN SQLITE PARA POSTERIORMENTE SE LLENADOS EN EL SPINNER
        registrar_operadora();
        registrar_operadora1();
        registrar_operadora2();
        registrar_operadora3();
        //FIN REGISTRANDO LAS OPERADORAS EN SQLITE PARA POSTERIORMENTE SE LLENADOS EN EL SPINNER***

        //REGISTRANDO EL TIPO DE PERSONAS EN  SQLITE PARA POSTERIORMENTE SE LLENADOS EN EL SPINNER
        registrar_persona();
        registrar_persona2();
        registrar_persona3();
        registrar_persona4();
        registrar_persona5();
        //FIN REGISTRANDO EL TIPO DE PERSONAS EN  SQLITE PARA POSTERIORMENTE SE LLENADOS EN EL SPINNER**

        //REGISTRANDO EL TIPO DE PARENTESCO PARA EL CONTACTO EN  SQLITE PARA POSTERIORMENTE SE LLENADOS EN EL SPINNER
        registrar_parentesco();
        registrar_parentesco1();
        registrar_parentesco2();
        registrar_parentesco3();
        registrar_parentesco4();
        registrar_parentesco5();
        //FIN REGISTRANDO EL TIPO DE PARENTESCO PARA EL CONTACTO EN  SQLITE PARA POSTERIORMENTE SE LLENADOS EN EL SPINNER***

        //REGISTRANDO EL TIPO DE DISCAPACIDAD EN SQLITE PARA POSTERIORMENTE SER LLAMADO EN EL SPINNER
        registrar_discapacidad1();
        registrar_discapacidad2();
        registrar_discapacidad3();
        //FIN REGISTRANDO EL TIPO DE DISCAPACIDAD EN SQLITE PARA POSTERIORMENTE SER LLAMADO EN EL SPINNER***

        //REGISTRANDO EL TIPO DE LICENCIA EN SQLITE PARA POSTERIORMENTE SER LLAMADO EN EL SPINNER
        registrar_licencia();
        registrar_licencia1();
        registrar_licencia2();
        registrar_licencia4();
        registrar_licencia3();
        registrar_licencia6();
        //FIN REGISTRANDO EL TIPO DE DISCAPACIDAD EN SQLITE PARA POSTERIORMENTE SER LLAMADO EN EL SPINNER**+


        //HACIENDO A ANIMACION DEL TEXTO EN IMAGEN
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.transaccion);
        image.setAnimation(myanim);
        text.setAnimation(myanim);
        //FIN HACIENDO A ANIMACION DEL TEXTO EN IMAGEN**

    }




    //REGISTRANDO DATOS EN SQLITE EN LA TABLA OPRADORA
    public void registrar_operadora() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "operadora", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Operadora.COD_ID, 1);
        values.put(Operadora.CAMPO_NOMBRE, "Seleccione Operadora...");
        values.put(Operadora.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Operadora.TABLA_OPERADORA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }

    public void registrar_operadora1() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "operadora", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Operadora.COD_ID, 2);
        values.put(Operadora.CAMPO_NOMBRE, "CNT");
        values.put(Operadora.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Operadora.TABLA_OPERADORA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }

    public void registrar_operadora2() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "operadora", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Operadora.COD_ID, 3);
        values.put(Operadora.CAMPO_NOMBRE, "CLARO");
        values.put(Operadora.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Operadora.TABLA_OPERADORA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }
    public void registrar_operadora3() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "operadora", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Operadora.COD_ID, 4);
        values.put(Operadora.CAMPO_NOMBRE, "MOVISTAR");
        values.put(Operadora.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Operadora.TABLA_OPERADORA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }
    //FIN REGISTRANDO DATOS EN SQLITE EN LA TABLA OPRADORA**



    //METODO EN CASA DE PRESIONAR LA TECLA DE REGRESAR SALGA DE LA APP
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //FIN METODO EN CASA DE PRESIONAR LA TECLA DE REGRESAR SALGA DE LA APP**


    //REGISTRANDO DATOS EN SQLITE EN LA TABLA PERSONA
    public void registrar_persona() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "persona", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Persona.COD_ID, 1);
        values.put(Persona.CAMPO_NOMBRE, "Seleccione tipo persona...");
        values.put(Persona.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Persona.TABLA_PERSONA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }

    public void registrar_persona2() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "persona", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Persona.COD_ID, 2);
        values.put(Persona.CAMPO_NOMBRE, "NIÑO");
        values.put(Persona.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Persona.TABLA_PERSONA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }

    public void registrar_persona3() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "persona", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Persona.COD_ID, 3);
        values.put(Persona.CAMPO_NOMBRE, "TERCERA EDAD");
        values.put(Persona.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Persona.TABLA_PERSONA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }


    public void registrar_persona4() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "persona", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Persona.COD_ID, 4);
        values.put(Persona.CAMPO_NOMBRE, "HOMBRE");
        values.put(Persona.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Persona.TABLA_PERSONA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }
    public void registrar_persona5() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "persona", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Persona.COD_ID, 5);
        values.put(Persona.CAMPO_NOMBRE, "MUJER");
        values.put(Persona.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Persona.TABLA_PERSONA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();

    }
    //FIN REGISTRANDO DATOS EN SQLITE EN LA TABLA PERSONA**


    //REGISTRANDO DATOS EN SQLITE EN LA TABLA PARENTESCO
    public void registrar_parentesco() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "parentesco", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Parentesco.COD_ID, 1);
        values.put(Parentesco.CAMPO_NOMBRE, "Seleccione Parentesco...");
        values.put(Parentesco.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Parentesco.TABLA_PARENTESCO, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();

    }


    public void registrar_parentesco1() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "parentesco", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Parentesco.COD_ID, 2);
        values.put(Parentesco.CAMPO_NOMBRE, "PAPÁ");
        values.put(Parentesco.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Parentesco.TABLA_PARENTESCO, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();

    }


    public void registrar_parentesco2() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "parentesco", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Parentesco.COD_ID, 3);
        values.put(Parentesco.CAMPO_NOMBRE, "MAMÁ");
        values.put(Parentesco.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Parentesco.TABLA_PARENTESCO, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();

    }
    public void registrar_parentesco3() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "parentesco", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Parentesco.COD_ID, 4);
        values.put(Parentesco.CAMPO_NOMBRE, "TÍO");
        values.put(Parentesco.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Parentesco.TABLA_PARENTESCO, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();

    }

    public void registrar_parentesco4() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "parentesco", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Parentesco.COD_ID, 5);
        values.put(Parentesco.CAMPO_NOMBRE, "ABUELO");
        values.put(Parentesco.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Parentesco.TABLA_PARENTESCO, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();

    }

    public void registrar_parentesco5() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "parentesco", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Parentesco.COD_ID, 2);
        values.put(Parentesco.CAMPO_NOMBRE, "PRIMO");
        values.put(Parentesco.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(Parentesco.TABLA_PARENTESCO, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();

    }
    //FIN REGISTRANDO DATOS EN SQLITE EN LA TABLA PARENTESCO


    //REGISTRANDO DATOS EN SQLITE EN LA TABLA DISCAPACIDAD
    public void registrar_discapacidad1() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "discapacidad", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(discapacidad.COD_ID, 1);
        values.put(discapacidad.CAMPO_NOMBRE, "Seleccione discapacidad...");
        values.put(discapacidad.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(discapacidad.TABLA_DISCAPACIDAD, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();

    }

    public void registrar_discapacidad2() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "discapacidad", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(discapacidad.COD_ID, 2);
        values.put(discapacidad.CAMPO_NOMBRE, "SI");
        values.put(discapacidad.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(discapacidad.TABLA_DISCAPACIDAD, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }
    public void registrar_discapacidad3() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "discapacidad", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(discapacidad.COD_ID, 3);
        values.put(discapacidad.CAMPO_NOMBRE, "NO");
        values.put(discapacidad.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(discapacidad.TABLA_DISCAPACIDAD, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }
    //FIN REGISTRANDO DATOS EN SQLITE EN LA TABLA DISCAPACIDAD***


    //REGISTRANDO DATOS EN SQLITE EN LA TABLA LICENCIA
    public void registrar_licencia() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "licencia", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(licencia.COD_ID, 1);
        values.put(licencia.CAMPO_NOMBRE, "Seleccione tipo licencia...");
        values.put(licencia.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(licencia.TABLA_LICENCIA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();

    }
    //REGISTRANDO DATOS EN SQLITE EN LA TABLA LICENCIA
    public void registrar_licencia1() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "licencia", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(licencia.COD_ID, 2);
        values.put(licencia.CAMPO_NOMBRE, "C");
        values.put(licencia.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(licencia.TABLA_LICENCIA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }
    public void registrar_licencia2() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "licencia", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(licencia.COD_ID, 3);
        values.put(licencia.CAMPO_NOMBRE, "B");
        values.put(licencia.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(licencia.TABLA_LICENCIA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }
    public void registrar_licencia3() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "licencia", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(licencia.COD_ID, 4);
        values.put(licencia.CAMPO_NOMBRE, "D");
        values.put(licencia.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(licencia.TABLA_LICENCIA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }
    public void registrar_licencia4 () {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "licencia", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(licencia.COD_ID, 5);
        values.put(licencia.CAMPO_NOMBRE, "E");
        values.put(licencia.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(licencia.TABLA_LICENCIA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();



    }

    public void registrar_licencia6() {

        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "licencia", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(licencia.COD_ID, 5);
        values.put(licencia.CAMPO_NOMBRE, "D");
        values.put(licencia.CAMPO_DESCRIPCION, "");


        Long idResultante =   db.insert(licencia.TABLA_LICENCIA, null, values);
        if(idResultante>0){

        }else {

        }

        db.close();

    }
    //FIN REGISTRANDO DATOS EN SQLITE EN LA TABLA LICENCIA***


}

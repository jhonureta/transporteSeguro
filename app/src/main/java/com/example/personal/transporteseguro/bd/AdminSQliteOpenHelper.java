package com.example.personal.transporteseguro.bd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.personal.transporteseguro.tablas.Conductor;
import com.example.personal.transporteseguro.tablas.Contac;
import com.example.personal.transporteseguro.tablas.Cursos;
import com.example.personal.transporteseguro.tablas.Operadora;
import com.example.personal.transporteseguro.tablas.Parentesco;
import com.example.personal.transporteseguro.tablas.Persona;
import com.example.personal.transporteseguro.tablas.bienvenida;
import com.example.personal.transporteseguro.tablas.discapacidad;
import com.example.personal.transporteseguro.tablas.licencia;
import com.example.personal.transporteseguro.tablas.usuario;

import java.util.ArrayList;

/**
 * Created by Personal on 26/01/2018.
 */

public class AdminSQliteOpenHelper extends SQLiteOpenHelper{

    public AdminSQliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Operadora.CREAR_TABLA_OPERADORA);
        db.execSQL(Persona.CREAR_TABLA_PERSONA);
        db.execSQL(Parentesco.CREAR_TABLA_PARENTESCO);
        db.execSQL(usuario.CREAR_TABLA_USUARIO);
        db.execSQL(Contac.CREAR_TABLA_PARIENTE);
        db.execSQL(bienvenida.CREAR_TABLA_ESTADO);
        db.execSQL(discapacidad.CREAR_TABLA_DISCAPACIDAD);
        db.execSQL(licencia.CREAR_TABLA_LIC);
        db.execSQL(Conductor.CREAR_TABLA_CONDUCTOR);
        db.execSQL(Cursos.CREAR_TABLA_CURSOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


        db.execSQL("DROP TABLE IF EXITS " + Operadora.CREAR_TABLA_OPERADORA);
        db.execSQL("DROP TABLE IF EXITS " + Persona.CREAR_TABLA_PERSONA);
        db.execSQL("DROP TABLE IF EXITS " + Parentesco.CREAR_TABLA_PARENTESCO);
        db.execSQL("DROP TABLE IF EXITS " + usuario.CREAR_TABLA_USUARIO);
        db.execSQL("DROP TABLE IF EXITS " + Contac.CREAR_TABLA_PARIENTE);
        db.execSQL("DROP TABLE IF EXITS " + bienvenida.CREAR_TABLA_ESTADO);
        db.execSQL("DROP TABLE IF EXITS " + discapacidad.CREAR_TABLA_DISCAPACIDAD);
        db.execSQL("DROP TABLE IF EXITS " + licencia.CREAR_TABLA_LIC);
        db.execSQL("DROP TABLE IF EXITS " + Conductor.CREAR_TABLA_CONDUCTOR);
        db.execSQL("DROP TABLE IF EXITS " + Cursos.CREAR_TABLA_CURSOS);
    }





}

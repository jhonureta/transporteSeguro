package com.example.personal.transporteseguro.tablas;

/**
 * Created by Personal on 04/12/2018.
 */

public class Persona {

    public static final String COD_ID="cod_per";
    public static final String TABLA_PERSONA="persona";
    public static final String CAMPO_NOMBRE="nom_per";
    public static final String CAMPO_DESCRIPCION="desc_per";

    public static final String CREAR_TABLA_PERSONA="CREATE TABLE " +
            ""+TABLA_PERSONA+" ("+COD_ID+" " +
            "INTEGER NOT NULL PRIMARY KEY, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_DESCRIPCION+" TEXT)";
}

package com.example.personal.transporteseguro.tablas;

/**
 * Created by Personal on 04/12/2018.
 */

public class Operadora {
    public static final String COD_ID="cod_cat";
    public static final String TABLA_OPERADORA="operadora";
    public static final String CAMPO_NOMBRE="nom_oper";
    public static final String CAMPO_DESCRIPCION="desc_oper";

    public static final String CREAR_TABLA_OPERADORA="CREATE TABLE " +
            ""+TABLA_OPERADORA+" ("+COD_ID+" " +
            "INTEGER NOT NULL PRIMARY KEY, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_DESCRIPCION+" TEXT)";
}

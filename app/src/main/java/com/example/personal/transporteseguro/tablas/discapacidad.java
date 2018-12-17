package com.example.personal.transporteseguro.tablas;

/**
 * Created by Personal on 09/12/2018.
 */

public class discapacidad {
    public static final String COD_ID="cod_dic";
    public static final String TABLA_DISCAPACIDAD="discapacidad";
    public static final String CAMPO_NOMBRE="nom_dis";
    public static final String CAMPO_DESCRIPCION="desc_dis";

    public static final String CREAR_TABLA_DISCAPACIDAD="CREATE TABLE " +
            ""+TABLA_DISCAPACIDAD+" ("+COD_ID+" " +
            "INTEGER NOT NULL PRIMARY KEY, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_DESCRIPCION+" TEXT)";
}

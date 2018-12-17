package com.example.personal.transporteseguro.tablas;

/**
 * Created by Personal on 08/12/2018.
 */

public class bienvenida {
    public static final String COD_ID="cod_bien";
    public static final String TABLA_BIENVENIDA="registro";
    public static final String CAMPO_ESTADO="est_app";
    public static final String CAMPO_DESCRIPCION="desc_app";

    public static final String CREAR_TABLA_ESTADO="CREATE TABLE " +
            ""+TABLA_BIENVENIDA+" ("+COD_ID+" " +
            "INTEGER NOT NULL PRIMARY KEY, "+CAMPO_ESTADO+" INT,"+CAMPO_DESCRIPCION+" TEXT)";
}

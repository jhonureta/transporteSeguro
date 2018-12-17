package com.example.personal.transporteseguro.tablas;

/**
 * Created by Personal on 09/12/2018.
 */

public class licencia {
    public static final String COD_ID="cod_lic";
    public static final String TABLA_LICENCIA="licencia";
    public static final String CAMPO_NOMBRE="nom_lic";
    public static final String CAMPO_DESCRIPCION="desc_lic";

    public static final String CREAR_TABLA_LIC="CREATE TABLE " +
            ""+TABLA_LICENCIA+" ("+COD_ID+" " +
            "INTEGER NOT NULL PRIMARY KEY, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_DESCRIPCION+" TEXT)";
}

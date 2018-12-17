package com.example.personal.transporteseguro.tablas;

/**
 * Created by Personal on 08/12/2018.
 */

public class Contac {
    public static final String COD_USU="cod_par";
    public static final String TABLA_PARIENTE="pariente";
    public static final String CAMPO_NOMBRE="nom_par";
    public static final String CAMPO_APELLIDO="ape_par";
    public static final String CAMPO_PARENTESCO="tip_par";
    public static final String CAMPO_CELULAR="cel_par";
    public static final String CAMPO_OPERADORA="oper_par";
    public static final String CAMPO_CORREO="corr_par";
    public static final String CAMPO_TELEFONO_FIJO="tel_par";


    public static final String CREAR_TABLA_PARIENTE="CREATE TABLE " +
            ""+TABLA_PARIENTE+" ("+COD_USU+" " +
            "INTEGER NOT NULL PRIMARY KEY, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_APELLIDO+" TEXT,"+CAMPO_CORREO+" TEXT,"+CAMPO_PARENTESCO+" TEXT,"+CAMPO_CELULAR+" TEXT,"+CAMPO_OPERADORA+" TEXT,"+CAMPO_TELEFONO_FIJO+" TEXT)";
}

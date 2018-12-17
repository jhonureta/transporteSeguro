package com.example.personal.transporteseguro.tablas;

/**
 * Created by Personal on 08/12/2018.
 */

public class usuario {
    public static final String COD_USU="cod_usu";
    public static final String TABLA_USUARIO="usuario";
    public static final String CAMPO_NOMBRE="nom_usu";
    public static final String CAMPO_APELLIDO="ape_usu";
    public static final String CAMPO_CORREO="corr_usu";
    public static final String CAMPO_CLAVE="cla_usu";
    public static final String CAMPO_CELULAR="cel_usu";
    public static final String CAMPO_OPERADORA="oper_usu";
    public static final String CAMPO_TELEFONO_FIJO="tel_usu";
    public static final String CAMPO_USUARIO="tip_usu";
    public static final String CAMPO_DISCAPACIDAD="dis_con";

    public static final String CREAR_TABLA_USUARIO="CREATE TABLE " +
            ""+TABLA_USUARIO+" ("+COD_USU+" " +
            "INTEGER NOT NULL PRIMARY KEY, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_APELLIDO+" TEXT,"+CAMPO_CORREO+" TEXT,"+CAMPO_CLAVE+" TEXT,"+CAMPO_CELULAR+" TEXT,"+CAMPO_OPERADORA+" TEXT,"+CAMPO_TELEFONO_FIJO+" TEXT,"+CAMPO_USUARIO+" TEXT, "+CAMPO_DISCAPACIDAD+" TEXT)";
}

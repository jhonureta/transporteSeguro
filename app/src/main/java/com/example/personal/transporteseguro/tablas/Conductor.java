package com.example.personal.transporteseguro.tablas;

/**
 * Created by Personal on 09/12/2018.
 */

public class Conductor {
    public static final String COD_USU="cod_cond";
    public static final String TABLA_CONDUCTOR="conductor";
    public static final String CAMPO_NOMBRE="nom_con";
    public static final String CAMPO_APELLIDO="ape_con";
    public static final String CAMPO_FECHA="fech_con";
    public static final String CAMPO_EDAD="edad_con";
    public static final String CAMPO_LICENCIA="lic_con";
    public static final String CAMPO_EXPERENCIA="exp_con";
    public static final String CAMPO_CORREO="corr_usu";
    public static final String CAMPO_CLAVE="cla_usu";
    public static final String CAMPO_CELULAR="cel_usu";
    public static final String CAMPO_OPERADORA="oper_usu";
    public static final String CAMPO_TELEFONO_FIJO="tel_usu";
    public static final String CAMPO_DISCAPACIDAD="dis_con";


    public static final String CREAR_TABLA_CONDUCTOR="CREATE TABLE " +
            ""+TABLA_CONDUCTOR+" ("+COD_USU+" " +
            "INTEGER NOT NULL PRIMARY KEY, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_APELLIDO+" TEXT,"+CAMPO_FECHA+" TEXT,"+CAMPO_EDAD+" INT, "+CAMPO_LICENCIA+" TEXT, "+CAMPO_EXPERENCIA+" TEXT, "+CAMPO_CORREO+" TEXT,"+CAMPO_CLAVE+" TEXT,"+CAMPO_CELULAR+" TEXT,"+CAMPO_OPERADORA+" TEXT,"+CAMPO_TELEFONO_FIJO+" TEXT,"+CAMPO_DISCAPACIDAD+" TEXT)";

}

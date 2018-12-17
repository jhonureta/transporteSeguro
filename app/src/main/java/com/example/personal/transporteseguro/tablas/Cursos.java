package com.example.personal.transporteseguro.tablas;

/**
 * Created by Personal on 09/12/2018.
 */

public class Cursos {
    public static final String COD_USU="cod_curs";
    public static final String TABLA_CURSOS="cursos";

    public static final String CAMPO_CURSO1="curs1_usu";
    public static final String CAMPO_CURSO2="curs2_usu";
    public static final String CAMPO_CURSO3="curs3_usu";
    public static final String CAMPO_CURSO4="curs4_usu";
    public static final String CAMPO_CURSO5="curs5_usu";


    public static final String CREAR_TABLA_CURSOS="CREATE TABLE " +
            ""+TABLA_CURSOS+" ("+COD_USU+" " +
            "INTEGER NOT NULL PRIMARY KEY, "+CAMPO_CURSO1+" TEXT, "+CAMPO_CURSO2+" TEXT,"+CAMPO_CURSO3+" TEXT, "+CAMPO_CURSO4+" TEXT,"+CAMPO_CURSO5+" TEXT)";

}

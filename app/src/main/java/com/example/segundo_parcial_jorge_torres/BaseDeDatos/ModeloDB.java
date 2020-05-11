package com.example.segundo_parcial_jorge_torres.BaseDeDatos;

public class ModeloDB {
    public static final String NOMBRE_BD = "bd";
    public static final String NOMBRE_TABLA = "personas";
    public static final String COL_CEDULA = "cedula";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_ESTRATO = "estrato";
    public static final String COL_SALARIO = "salario";
    public static final String COL_NIVEL_EDUCATIVO = "niveleducativo";

    public static final String CREAR_TABLA_REGISTROS = "CREATE TABLE " +
            "" + NOMBRE_TABLA + " ( " + COL_CEDULA + " INTEGER PRIMARY KEY, " +
            " " + COL_NOMBRE + " TEXT, " + COL_ESTRATO + " INTEGER, " +
            " " + COL_SALARIO + " INTEGER, " + COL_NIVEL_EDUCATIVO + " INTEGER)";
}

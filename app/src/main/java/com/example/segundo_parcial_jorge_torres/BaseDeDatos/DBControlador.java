package com.example.segundo_parcial_jorge_torres.BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.segundo_parcial_jorge_torres.Clases.Persona;

import java.util.ArrayList;

public class DBControlador {
    private DBHelper helper;

    public DBControlador(Context context) {
        this.helper = new DBHelper(context, ModeloDB.NOMBRE_BD, null, 1);
    }

    public long agregarRegistro(Persona persona) {
        try {
            SQLiteDatabase database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ModeloDB.COL_CEDULA, persona.getCedula());
            values.put(ModeloDB.COL_NOMBRE, persona.getNombre());
            values.put(ModeloDB.COL_ESTRATO, persona.getEstrato());
            values.put(ModeloDB.COL_SALARIO, persona.getSalario());
            values.put(ModeloDB.COL_NIVEL_EDUCATIVO, persona.getNivel_educativo());
            return database.insert(ModeloDB.NOMBRE_TABLA, null, values);
        } catch (Exception e) {
            return -1L;
        }
    }

    public int actualizarRegistro(Persona persona) {
        try {
            SQLiteDatabase database = helper.getWritableDatabase();
            ContentValues valoresActualizados = new ContentValues();
            valoresActualizados.put(ModeloDB.COL_NOMBRE, persona.getNombre());
            valoresActualizados.put(ModeloDB.COL_ESTRATO, persona.getEstrato());
            valoresActualizados.put(ModeloDB.COL_SALARIO, persona.getSalario());
            valoresActualizados.put(ModeloDB.COL_NIVEL_EDUCATIVO, persona.getNivel_educativo());

            String campoParaActualizar = ModeloDB.COL_CEDULA + " = ?";
            String[] argumentosParaActualizar = {String.valueOf(persona.getCedula())};

            return database.update(ModeloDB.NOMBRE_TABLA, valoresActualizados, campoParaActualizar, argumentosParaActualizar);
        } catch (Exception e) {
            return 0;
        }
    }

    public int borrarRegistro(Persona persona) {
        try {
            SQLiteDatabase database = helper.getWritableDatabase();
            String[] argumentos = {String.valueOf(persona.getCedula())};
            return database.delete(ModeloDB.NOMBRE_TABLA, ModeloDB.COL_CEDULA + " = ?", argumentos);
        } catch (Exception e) {
            return 0;
        }
    }

    public Persona buscarPersona(int cedula) {

        SQLiteDatabase database = helper.getReadableDatabase();

        String[] columnasConsultar = {ModeloDB.COL_CEDULA, ModeloDB.COL_NOMBRE, ModeloDB.COL_ESTRATO
                , ModeloDB.COL_SALARIO, ModeloDB.COL_NIVEL_EDUCATIVO};
        String[] argumento = {String.valueOf(cedula)};
        Cursor cursor = database.query(ModeloDB.NOMBRE_TABLA, columnasConsultar
                , ModeloDB.COL_CEDULA + " = ?", argumento, null, null, null);

        if (cursor == null) {
            return null;
        }

        if (!cursor.moveToFirst()) {
            return null;
        }

        Persona persona = new Persona(cursor.getInt(0), cursor.getString(1)
                , cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
        cursor.close();
        return persona;
    }

    public ArrayList<Persona> optenerRegistros() {
        ArrayList<Persona> personas = new ArrayList<>();

        SQLiteDatabase database = helper.getReadableDatabase();

        String[] columnasConsultar = {ModeloDB.COL_CEDULA, ModeloDB.COL_NOMBRE, ModeloDB.COL_ESTRATO
                , ModeloDB.COL_SALARIO, ModeloDB.COL_NIVEL_EDUCATIVO};

        Cursor cursor = database.query(ModeloDB.NOMBRE_TABLA, columnasConsultar
                , null, null, null, null, null);

        if (cursor == null) {
            return personas;
        }

        if (!cursor.moveToFirst()) {
            return personas;
        }

        do {
            Persona persona = new Persona(cursor.getInt(0), cursor.getString(1)
                    , cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
            personas.add(persona);
        } while (cursor.moveToNext());

        cursor.close();
        return personas;
    }
}

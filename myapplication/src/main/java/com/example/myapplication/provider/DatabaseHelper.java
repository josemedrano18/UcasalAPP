package com.example.myapplication.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.provider.ContractParaCarreras;

/**
 * Clase envoltura para el gestor de Bases de datos
 */
class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context,
                          String name,
                          SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase database) {
        createTable(database); // Crear la tabla "gasto"
    }

    /**
     * Crear tabla en la base de datos
     *
     * @param database Instancia de la base de datos
     */
    private void createTable(SQLiteDatabase database) {
        String cmd = "CREATE TABLE " + ContractParaCarreras.CARRERAS+ " (" +
                ContractParaCarreras.Columnas.COD_CARRERA + " INTEGER PRIMARY KEY, " +
                ContractParaCarreras.Columnas.NOM_CARRERA + " VARCHAR(40) UNIQUE, " +
                ContractParaCarreras.Columnas.EJE_CARRERA + " VARCHAR (60), " +
                ContractParaCarreras.Columnas.TITULO + " TEXT, " +
                ContractParaCarreras.Columnas.DURACION + " VARCHAR(20)," +
                ContractParaCarreras.Columnas.SALIDA + " LONGTEXT," +
                ContractParaCarreras.Columnas.PERFIL + " LONGTEXT," +
                ContractParaCarreras.Columnas.REQUISITOS + " MEDIUMTEXT," +
                ContractParaCarreras.Columnas.CURSO + " TEXT," +
                ContractParaCarreras.Columnas.PLAN_ESTUDIO + " VARCHAR(60)," +
                ContractParaCarreras.Columnas.ID_REMOTA + " TEXT UNIQUE," +
                ContractParaCarreras.Columnas.ESTADO + " INTEGER NOT NULL DEFAULT "+ ContractParaCarreras.ESTADO_OK+"," +
                ContractParaCarreras.Columnas.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try { db.execSQL("drop table " + ContractParaCarreras.CARRERAS); }
        catch (SQLiteException e) { }
        onCreate(db);
    }

}
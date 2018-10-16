package com.example.myapplication.utils;


import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.example.myapplication.provider.ContractParaCarreras;

import org.json.JSONException;
import org.json.JSONObject;

public class Utilidades {
    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_ID = 0;
    public static final int COLUMNA_ID_REMOTA = 1;
    public static final int COLUMNA_NOM_CARRERA= 2;
    public static final int COLUMNA_EJE_CARRERA = 3;
    public static final int COLUMNA_TITULO= 4;
    public static final int COLUMNA_DURACION = 5;
    public static final int COLUMNA_SALIDA= 6;
    public static final int COLUMNA_PERFIL = 7;
    public static final int COLUMNA_REQUISITOS = 8;
    public static final int COLUMNA_CURSO= 9;
    public static final int COLUMNA_PLAN_ESTUDIO= 10;
    /**
     * Determina si la aplicación corre en versiones superiores o iguales
     * a Android LOLLIPOP
     *
     * @return booleano de confirmación
     */
    public static boolean materialDesign() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Copia los datos de un gasto almacenados en un cursor hacia un
     * JSONObject
     *
     * @param c cursor
     * @return objeto jason
     */
    public static JSONObject deCursorAJSONObject(Cursor c) {
        JSONObject jObject = new JSONObject();
        String nom_carrera;
        String eje_carrera;
        String titulo;
        String duracion;
        String salida;
        String perfil;
        String requisitos;
        String curso;
        String plan_estudio;

         nom_carrera=c.getString(COLUMNA_NOM_CARRERA);
         eje_carrera=c.getString(COLUMNA_EJE_CARRERA);
         titulo=c.getString(COLUMNA_TITULO);
         duracion=c.getString(COLUMNA_DURACION);
         salida=c.getString(COLUMNA_SALIDA);
         perfil=c.getString(COLUMNA_PERFIL);
         requisitos=c.getString(COLUMNA_REQUISITOS);
         curso=c.getString(COLUMNA_CURSO);
         plan_estudio=c.getString(COLUMNA_PLAN_ESTUDIO);



        try {
            jObject.put(ContractParaCarreras.Columnas.NOM_CARRERA, nom_carrera);
            jObject.put(ContractParaCarreras.Columnas.EJE_CARRERA, eje_carrera);
            jObject.put(ContractParaCarreras.Columnas.TITULO, titulo);
            jObject.put(ContractParaCarreras.Columnas.DURACION, duracion);
            jObject.put(ContractParaCarreras.Columnas.SALIDA, salida);
            jObject.put(ContractParaCarreras.Columnas.PERFIL, perfil);
            jObject.put(ContractParaCarreras.Columnas.REQUISITOS, requisitos);
            jObject.put(ContractParaCarreras.Columnas.CURSO, curso);
            jObject.put(ContractParaCarreras.Columnas.PLAN_ESTUDIO, plan_estudio);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject", String.valueOf(jObject));

        return jObject;
    }
}
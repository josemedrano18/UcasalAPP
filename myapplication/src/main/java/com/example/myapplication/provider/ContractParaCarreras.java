package com.example.myapplication.provider;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;
public class ContractParaCarreras {
    /**
     * Autoridad del Content Provider
     */
    public final static String AUTHORITY
            = "com.example.myapplication";
    /**
     * Representación de la tabla a consultar
     */
    public static final String CARRERAS = "carreras";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + AUTHORITY + CARRERAS;
    /**
     * Tipo MIME que retorna la consulta de {link CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + AUTHORITY + CARRERAS;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + CARRERAS);
    /**
     * Comparador de URIs de contenido
     */
    public static final UriMatcher uriMatcher;
    /**
     * Código para URIs de multiples registros
     */
    public static final int ALLROWS = 1;
    /**
     * Código para URIS de un solo registro
     */
    public static final int SINGLE_ROW = 2;


    // Asignación de URIs
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, CARRERAS, ALLROWS);
        uriMatcher.addURI(AUTHORITY, CARRERAS + "/#", SINGLE_ROW);
    }

    // Valores para la columna ESTADO
    public static final int ESTADO_OK = 0;
    public static final int ESTADO_SYNC = 1;


    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }
        public final static String COD_CARRERA = "cod_carrera";
        public final static String NOM_CARRERA = "nom_carrera";
        public final static String EJE_CARRERA = "eje_carrera";
        public final static String TITULO = "titulo";
        public final static String DURACION = "duracion";
        public final static String SALIDA= "salida";
        public final static String PERFIL= "perfil";
        public final static String REQUISITOS= "requisitos";
        public final static String CURSO= "curso";
        public final static String PLAN_ESTUDIO= "plan_estudio";


        public static final String ESTADO = "estado";
        public static final String ID_REMOTA = "idRemota";
        public final static String PENDIENTE_INSERCION = "pendiente_insercion";

    }
}

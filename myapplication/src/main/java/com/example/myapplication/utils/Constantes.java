package com.example.myapplication.utils;


/**
 * Constantes
 */
public class Constantes {

    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta característica.
     */
    private static final String PUERTO_HOST = "3306";

    /**
     * Dirección IP de genymotion o AVD
     */
    //private static final String IP = "http://10.0.3.2";
    private static final String IP = "http://192.168.0.102";

    /**
     * URLs del Web Service
     */
    public static final String GET_URL = "http://127.0.0.1::8080/ucasal/obtener_carreras.php";
    public static final String INSERT_URL = IP + "/sync/web/insertar_gasto.php";

    /**
     * Campos de las respuestas Json
     */
    public static final String COD_CARRERA = "cod_carrera";
    public static final String ESTADO= "estado";
    public static final String CARRERAS = "carreras";
/*
    public static final String TITULO = "gastos";
    public static final String  DURACION= "";
    public static final String SALIDA = "";
    public static final String PERFIL = "";
    public static final String REQUISITOS = "";
    public static final String  CURSO = "";
    public static final String PLAN_ESTUDIO = "";
*/
    public static final String MENSAJE = "mensaje";
    public static final String SUCCESS = "1";
    public static final String FAILED = "2";

    /**
     * Tipo de cuenta para la sincronización
     */
    public static final String ACCOUNT_TYPE = "com.example.myapplication.account";


}
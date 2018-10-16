package com.example.myapplication.sync;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.example.myapplication.utils.Utilidades;
import com.example.myapplication.utils.Constantes;
import com.example.myapplication.R;
import com.example.myapplication.provider.ContractParaCarreras;
import com.example.myapplication.web.Carrera;
import com.example.myapplication.web.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maneja la transferencia de datos entre el servidor y el cliente
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = SyncAdapter.class.getSimpleName();

    ContentResolver resolver;
    private Gson gson = new Gson();

    /**
     * Proyección para las consultas
     */
    private static final String[] PROJECTION = new String[]{
            ContractParaCarreras.Columnas._ID,
            ContractParaCarreras.Columnas.ID_REMOTA,
            ContractParaCarreras.Columnas.NOM_CARRERA,
           ContractParaCarreras.Columnas.EJE_CARRERA,
           ContractParaCarreras.Columnas.TITULO,
           ContractParaCarreras.Columnas.DURACION,
            ContractParaCarreras.Columnas.SALIDA,
            ContractParaCarreras.Columnas.PERFIL,
           ContractParaCarreras.Columnas.REQUISITOS,
            ContractParaCarreras.Columnas.CURSO,
            ContractParaCarreras.Columnas.PLAN_ESTUDIO
    };

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

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        resolver = context.getContentResolver();
    }

    /**
     * Constructor para mantener compatibilidad en versiones inferiores a 3.0
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        resolver = context.getContentResolver();
    }

    public static void inicializarSyncAdapter(Context context) {
        obtenerCuentaASincronizar(context);

    }

    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              final SyncResult syncResult) {

        Log.i(TAG, "onPerformSync()...");

        boolean soloSubida = extras.getBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, false);

        if (!soloSubida) {
            realizarSincronizacionLocal(syncResult);
        } else {
            realizarSincronizacionRemota();
        }
    }

    private void realizarSincronizacionLocal(final SyncResult syncResult) {
        Log.i(TAG, "Actualizando el cliente.");

        VolleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest( Request.Method.GET,
                        Constantes.GET_URL,


                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaGet(response, syncResult);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, error.networkResponse.toString());
                            }
                        }
                )
        );
    }

    /**
     * Procesa la respuesta del servidor al pedir que se retornen todos los gastos.
     *
     * @param response   Respuesta en formato Json
     * @param syncResult Registro de resultados de sincronización
     */
    private void procesarRespuestaGet(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocales(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void realizarSincronizacionRemota() {
        Log.i(TAG, "Actualizando el servidor...");

        iniciarActualizacion();

        Cursor c = obtenerRegistrosSucios();

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros sucios.");

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(COLUMNA_ID);

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERT_URL,
                                Utilidades.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsert(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error Volley: " + error.getMessage());
                                    }
                                }

                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" + getParamsEncoding();
                            }
                        }
                );
            }

        } else {
            Log.i(TAG, "No se requiere sincronización");
        }
        c.close();
    }

    /**
     * Obtiene el registro que se acaba de marcar como "pendiente por sincronizar" y
     * con "estado de sincronización"
     *
     * @return Cursor con el registro.
     */
    private Cursor obtenerRegistrosSucios() {
        Uri uri = ContractParaCarreras.CONTENT_URI;
        String selection = ContractParaCarreras.Columnas.PENDIENTE_INSERCION + "=? AND "
                + ContractParaCarreras.Columnas.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", ContractParaCarreras.ESTADO_SYNC + ""};

        return resolver.query(uri, PROJECTION, selection, selectionArgs, null);
    }

    /**
     * Cambia a estado "de sincronización" el registro que se acaba de insertar localmente
     */
    private void iniciarActualizacion() {
        Uri uri = ContractParaCarreras.CONTENT_URI;
        String selection = ContractParaCarreras.Columnas.PENDIENTE_INSERCION + "=? AND "
                + ContractParaCarreras.Columnas.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", ContractParaCarreras.ESTADO_OK + ""};

        ContentValues v = new ContentValues();
        v.put(ContractParaCarreras.Columnas.ESTADO, ContractParaCarreras.ESTADO_SYNC);

        int results = resolver.update(uri, v, selection, selectionArgs);
        Log.i(TAG, "Registros puestos en cola de inserción:" + results);
    }

    /**
     * Limpia el registro que se sincronizó y le asigna la nueva id remota proveida
     * por el servidor
     *
     * @param idRemota id remota
     */
    private void finalizarActualizacion(String idRemota, int idLocal) {
        Uri uri = ContractParaCarreras.CONTENT_URI;
        String selection = ContractParaCarreras.Columnas._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(idLocal)};

        ContentValues v = new ContentValues();
        v.put(ContractParaCarreras.Columnas.PENDIENTE_INSERCION, "0");
        v.put(ContractParaCarreras.Columnas.ESTADO, ContractParaCarreras.ESTADO_OK);
        v.put(ContractParaCarreras.Columnas.ID_REMOTA, idRemota);

        resolver.update(uri, v, selection, selectionArgs);
    }

    /**
     * Procesa los diferentes tipos de respuesta obtenidos del servidor
     *
     * @param response Respuesta en formato Json
     */
    public void procesarRespuestaInsert(JSONObject response, int idLocal) {

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);
            // Obtener identificador del nuevo registro creado en el servidor
            String idRemota = response.getString(Constantes.COD_CARRERA);

            switch (estado) {
                case Constantes.SUCCESS:
                    Log.i(TAG, mensaje);
                    finalizarActualizacion(idRemota, idLocal);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Actualiza los registros locales a través de una comparación con los datos
     * del servidor
     *
     * @param response   Respuesta en formato Json obtenida del servidor
     * @param syncResult Registros de la sincronización
     */
    private void actualizarDatosLocales(JSONObject response, SyncResult syncResult) {

        JSONArray gastos = null;

        try {
            // Obtener array "gastos"
            gastos = response.getJSONArray(Constantes.CARRERAS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Carrera[] res = gson.fromJson(gastos != null ? gastos.toString() : null, Carrera[].class);
        List<Carrera> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        // Tabla hash para recibir las entradas entrantes
        HashMap<String, Carrera> expenseMap = new HashMap<String, Carrera>();
        for (Carrera e : data) {
            expenseMap.put(e.cod_carrera, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractParaCarreras.CONTENT_URI;
        String select = ContractParaCarreras.Columnas.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, PROJECTION, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String nom_carrera;
        String eje_carrera;
        String titulo;
        String duracion;
        String salida;
        String perfil;
        String requisitos;
        String curso;
        String plan_estudio;
        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(COLUMNA_ID);
            nom_carrera=c.getString(COLUMNA_NOM_CARRERA);
            eje_carrera=c.getString(COLUMNA_EJE_CARRERA);
            titulo=c.getString(COLUMNA_TITULO);
            duracion=c.getString(COLUMNA_DURACION);
            salida=c.getString(COLUMNA_SALIDA);
            perfil=c.getString(COLUMNA_PERFIL);
            requisitos=c.getString(COLUMNA_REQUISITOS);
            curso=c.getString(COLUMNA_CURSO);
            plan_estudio=c.getString(COLUMNA_PLAN_ESTUDIO);

            Carrera match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractParaCarreras.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b = match.nom_carrera != nom_carrera;
                boolean b1 = match.eje_carrera != null && !match.eje_carrera.equals(eje_carrera);
                boolean b2 = match.titulo != null && !match.titulo.equals(titulo);
                boolean b3 = match.duracion != null && !match.duracion.equals(duracion);
                boolean b4 = match.salida != null && !match.salida.equals(salida);
                boolean b5 = match.perfil != null && !match.perfil.equals(perfil);
                boolean b6= match.requisitos != null && !match.requisitos.equals(requisitos);
                boolean b7 = match.curso != null && !match.curso.equals(curso);
                boolean b8 = match.plan_estudio != null && !match.plan_estudio.equals(plan_estudio);
                if (b || b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 ) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContractParaCarreras.Columnas.NOM_CARRERA, match.nom_carrera)
                            .withValue(ContractParaCarreras.Columnas.EJE_CARRERA, match.eje_carrera)
                            .withValue(ContractParaCarreras.Columnas.TITULO, match.titulo)
                            .withValue(ContractParaCarreras.Columnas.DURACION, match.duracion)
                            .withValue(ContractParaCarreras.Columnas.SALIDA, match.salida)
                            .withValue(ContractParaCarreras.Columnas.PERFIL, match.perfil)
                            .withValue(ContractParaCarreras.Columnas.REQUISITOS, match.requisitos)
                            .withValue(ContractParaCarreras.Columnas.CURSO, match.curso)
                            .withValue(ContractParaCarreras.Columnas.PLAN_ESTUDIO, match.plan_estudio)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractParaCarreras.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Carrera e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de: " + e.cod_carrera);
            ops.add(ContentProviderOperation.newInsert(ContractParaCarreras.CONTENT_URI)
                    .withValue(ContractParaCarreras.Columnas.ID_REMOTA, e.cod_carrera)
                    .withValue(ContractParaCarreras.Columnas.NOM_CARRERA, e.nom_carrera)
                    .withValue(ContractParaCarreras.Columnas.EJE_CARRERA, e.eje_carrera)
                    .withValue(ContractParaCarreras.Columnas.TITULO, e.titulo)
                    .withValue(ContractParaCarreras.Columnas.DURACION, e.duracion)
                    .withValue(ContractParaCarreras.Columnas.SALIDA, e.salida)
                    .withValue(ContractParaCarreras.Columnas.PERFIL, e.perfil)
                    .withValue(ContractParaCarreras.Columnas.REQUISITOS, e.requisitos)
                    .withValue(ContractParaCarreras.Columnas.CURSO, e.curso)
                    .withValue(ContractParaCarreras.Columnas.PLAN_ESTUDIO, e.plan_estudio)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(ContractParaCarreras.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractParaCarreras.CONTENT_URI,
                    null,
                    false);
            Log.i(TAG, "Sincronización finalizada.");

        } else {
            Log.i(TAG, "No se requiere sincronización");
        }

    }

    /**
     * Inicia manualmente la sincronización
     *
     * @param context    Contexto para crear la petición de sincronización
     * @param onlyUpload Usa true para sincronizar el servidor o false para sincronizar el cliente
     */
    public static void sincronizarAhora(Context context, boolean onlyUpload) {
        Log.i(TAG, "Realizando petición de sincronización manual.");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        if (onlyUpload)
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, true);
        ContentResolver.requestSync(obtenerCuentaASincronizar(context),
                context.getString(R.string.provider_authority), bundle);
    }

    /**
     * Crea u obtiene una cuenta existente
     *
     * @param context Contexto para acceder al administrador de cuentas
     * @return cuenta auxiliar.
     */
    public static Account obtenerCuentaASincronizar(Context context) {
        // Obtener instancia del administrador de cuentas
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Crear cuenta por defecto
        Account newAccount = new Account(
                context.getString(R.string.app_name), Constantes.ACCOUNT_TYPE);

        // Comprobar existencia de la cuenta
        if (null == accountManager.getPassword(newAccount)) {

            // Añadir la cuenta al account manager sin password y sin datos de usuario
            if (!accountManager.addAccountExplicitly(newAccount, "", null))
                return null;

        }
        Log.i(TAG, "Cuenta de usuario obtenida.");
        return newAccount;
    }

}
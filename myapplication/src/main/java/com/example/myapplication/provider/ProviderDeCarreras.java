package com.example.myapplication.provider;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class ProviderDeCarreras extends ContentProvider {

    /**
     * Nombre de la base de datos
     */
    private static final String DATABASE_NAME = "ucasal.db";
    /**
     * Versión actual de la base de datos
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Instancia global del Content Resolver
     */
    private ContentResolver resolver;
    /**
     * Instancia del administrador de BD
     */
    private DatabaseHelper databaseHelper;

    @Override
    public boolean onCreate() {
        // Inicializando gestor BD
        databaseHelper = new DatabaseHelper(
                getContext(),
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );

        resolver = getContext().getContentResolver();

        return true;
    }

    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        // Obtener base de datos
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // Comparar Uri
        int match = ContractParaCarreras.uriMatcher.match(uri);

        Cursor c;

        switch (match) {
            case ContractParaCarreras.ALLROWS:
                // Consultando todos los registros
                c = db.query(ContractParaCarreras.CARRERAS, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                c.setNotificationUri(
                        resolver,
                        ContractParaCarreras.CONTENT_URI);
                break;
            case ContractParaCarreras.SINGLE_ROW:
                // Consultando un solo registro basado en el Id del Uri
                long cod_carrera = ContentUris.parseId(uri);
                c = db.query(ContractParaCarreras.CARRERAS, projection,
                        ContractParaCarreras.Columnas._ID + " = " + cod_carrera,
                        selectionArgs, null, null, sortOrder);
                c.setNotificationUri(
                        resolver,
                        ContractParaCarreras.CONTENT_URI);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        return c;

    }

    @Override
    public String getType(Uri uri) {
        switch (ContractParaCarreras.uriMatcher.match(uri)) {
            case ContractParaCarreras.ALLROWS:
                return ContractParaCarreras.MULTIPLE_MIME;
            case ContractParaCarreras.SINGLE_ROW:
                return ContractParaCarreras.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Tipo de gasto desconocido: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Validar la uri
        if (ContractParaCarreras.uriMatcher.match(uri) != ContractParaCarreras.ALLROWS) {
            throw new IllegalArgumentException("URI desconocida : " + uri);
        }
        ContentValues contentValues;
        if (values != null) {
            contentValues = new ContentValues(values);
        } else {
            contentValues = new ContentValues();
        }

        // Inserción de nueva fila
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long rowId = db.insert(ContractParaCarreras.CARRERAS, null, contentValues);
        if (rowId > 0) {
            Uri uri_carrera = ContentUris.withAppendedId(
                    ContractParaCarreras.CONTENT_URI, rowId);
            resolver.notifyChange(uri_carrera, null, false);
            return uri_carrera;
        }
        throw new SQLException("Falla al insertar fila en : " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int match = ContractParaCarreras.uriMatcher.match(uri);
        int affected;

        switch (match) {
            case ContractParaCarreras.ALLROWS:
                affected = db.delete(ContractParaCarreras.CARRERAS,
                        selection,
                        selectionArgs);
                break;
            case ContractParaCarreras.SINGLE_ROW:
                long cod_carrera = ContentUris.parseId(uri);
                affected = db.delete(ContractParaCarreras.CARRERAS,
                        ContractParaCarreras.Columnas.ID_REMOTA + "=" + cod_carrera
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange(uri, null, false);
                break;
            default:
                throw new IllegalArgumentException("Elemento gasto desconocido: " +
                        uri);
        }
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        switch (ContractParaCarreras.uriMatcher.match(uri)) {
            case ContractParaCarreras.ALLROWS:
                affected = db.update(ContractParaCarreras.CARRERAS, values,
                        selection, selectionArgs);
                break;
            case ContractParaCarreras.SINGLE_ROW:
                String cod_carrera = uri.getPathSegments().get(1);
                affected = db.update(ContractParaCarreras.CARRERAS, values,
                        ContractParaCarreras.Columnas.ID_REMOTA + "=" + cod_carrera
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        resolver.notifyChange(uri, null, false);
        return affected;
    }

}

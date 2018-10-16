package com.example.myapplication.Activities;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.myapplication.Fragments.fragmentoprueba;
import com.example.myapplication.R;
import com.example.myapplication.provider.ContractParaCarreras;
import com.example.myapplication.sync.SyncAdapter;
import com.example.myapplication.ui.AdaptadorDeCostos;

public class Carreras3 extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdaptadorDeCostos adapter;
    private TextView emptyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carreras3);
       recyclerView = (RecyclerView) findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdaptadorDeCostos(this);
        recyclerView.setAdapter(adapter);
        emptyView = (TextView) findViewById(R.id.recyclerview_data_empty);

        getSupportLoaderManager().initLoader(0, null, this);

        /* SyncAdapter.inicializarSyncAdapter(this);*/
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        emptyView.setText("Cargando datos...");
        // Consultar todos los registros

       return new CursorLoader(
                this,
                ContractParaCarreras.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        emptyView.setText("");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}

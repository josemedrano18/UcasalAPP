package com.example.myapplication.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.myapplication.ExpLVAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Carreras extends AppCompatActivity {

    private ExpandableListView expLV;
    private ExpLVAdapter adapter;
    private ArrayList<String> listFacultad;
    private Map<String, ArrayList<String>> mapChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carreras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab;
///        fab = (FloatingActionButton) findViewById(R.id.fab);
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
     expLV = (ExpandableListView) findViewById(R.id.expLV);
     listFacultad = new ArrayList<>();
     mapChild = new HashMap<>();
     cargarDatos();
    }
    private void cargarDatos()
    {
        ArrayList<String> listIngenieria = new ArrayList<>();
        ArrayList<String> listCiencias = new ArrayList<>();
        ArrayList<String> listArtesyHum = new ArrayList<>();
        ArrayList<String> listArqUrb = new ArrayList<>();
        ArrayList<String> listCsSalud = new ArrayList<>();
        ArrayList<String> listCsJurid= new ArrayList<>();
        ArrayList<String> listEconomiaAdm= new ArrayList<>();

        listFacultad.add("");
        listFacultad.add("Ciencias");
        listFacultad.add("Ingeniería");
        listFacultad.add("Artes y Humanidades");
        listFacultad.add("Arquitectura y Urbanismo");
        listFacultad.add("Ciencias de la Salud");
        listFacultad.add("Ciencias Jurídicas");
        listFacultad.add("Economía y Administración");


        listIngenieria.add("Ingeniería Civil");
        listIngenieria.add("Ingeniería en Informática");
        listIngenieria.add("Ingeniería Industrial");
        listIngenieria.add("Ingeniería en Telecomunicaciones");


        mapChild.put(listFacultad.get(2),listIngenieria);

        adapter=new ExpLVAdapter(listFacultad, mapChild, this);
        expLV.setAdapter(adapter);

        expLV.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(Carreras.this, DescripcionCarrera.class);
                startActivity(intent);
                return false;
            }
        });
    }
 /*   private ExpandableListView.OnChildClickListener myListItemClicked =  new     ExpandableListView.OnChildClickListener() {

        public boolean onChildClick(ExpandableListView expLV, View v,
                                    int groupPosition, int childPosition, long id) {

   *//*         //get the group header
            HeaderInfo headerInfo = deptList.get(groupPosition);
            //get the child info
            DetailInfo detailInfo =  headerInfo.getProductList().get(childPosition);
            //start new activity with specific child information
            //--Add below codes to your code*//*

//            myIntent.putExtra("information",detailInfo.getInfor());
            return false;
        }

    };*/

   /* @Override
    private void onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
    {
        Intent intent = new Intent(Carreras.this, DescripcionCarrera.class);
        startActivity(intent);
    }
*/
}

package com.example.myapplication.Activities;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import ru.whalemare.sheetmenu.SheetMenu;

public class Contacto extends AppCompatActivity {
//    String[] mTestArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        /*findViewById(R.id.btnShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });*/
        ListView lv= (ListView)findViewById(R.id.listContacto);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                showMenu();
            }
        });
    }

    private void showMenu() {
        SheetMenu.with(this)
                .setTitle("Opciones de contacto:")
                .setMenu(R.menu.menucontacto)
                .show();
    }
}

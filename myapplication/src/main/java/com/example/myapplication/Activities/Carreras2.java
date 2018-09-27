package com.example.myapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.Fragments.ListaCarreras;
import com.example.myapplication.R;

public class Carreras2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carreras2);
       /* if(savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().replace(this,new ListaCarreras()).commit();
        }*/
    }
}

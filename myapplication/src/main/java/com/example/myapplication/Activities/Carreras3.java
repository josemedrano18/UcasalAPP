package com.example.myapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import com.example.myapplication.Fragments.fragmentoprueba;
import com.example.myapplication.R;

public class Carreras3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carreras3);
        Fragment newFragment = new fragmentoprueba();
        FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
// Replace whatever is in the container view with this fragment,
// and add the transaction to the back stack
        transaccion.replace (R.id.container, newFragment);
        transaccion.addToBackStack(null);
// Commit the transaction
        transaccion.commit ();
    }
}

package com.example.myapplication.Activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Sedes extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sedes);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng saltacap = new LatLng(-24.740181274624742, -65.39247201661703);
        mMap.addMarker(new MarkerOptions().position(saltacap).title("Campo Castañares, Salta - Tel: xxxxxxx"));


        // mMap.moveCamera(CameraUpdateFactory.newLatLng(saltacap));
        LatLng metan= new LatLng(-25.488962182314697,-64.97070457925952);
        mMap.addMarker(new MarkerOptions().position(metan).title("Av. 9 de Julio 116, Metán - Tel: xxxxxxx"));
        LatLng tartagal= new LatLng(-22.516323, -63.804257);
        mMap.addMarker(new MarkerOptions().position(tartagal).title("Cornejo 455, Tartagal - Tel: xxxxxxx"));
        LatLng oran=new LatLng(-23.131956, -64.324184);
        mMap.addMarker(new MarkerOptions().position(oran).title("25 de Mayo 350, Orán - Tel: xxxxxxx"));
        LatLng pellegrini=new LatLng(-24.799407, -65.416115);
        mMap.addMarker(new MarkerOptions().position(pellegrini).title("Pellegrini 790, Salta - Tel: xxxxxxx"));
        LatLng baires=new LatLng(-34.604445, -58.380205);
        mMap.addMarker(new MarkerOptions().position(baires).title("Roque Sáenz Peña 950, CABA - Tel: xxxxxxx"));
        LatLng riocuarto=new LatLng(-33.118657, -64.343368);
        mMap.addMarker(new MarkerOptions().position(riocuarto).title("Av. Jaime Gil 625, Río Cuarto - Tel: xxxxxxx"));
        LatLng riogrande=new LatLng(-53.770002, -67.705131);
        mMap.addMarker(new MarkerOptions().position(riogrande).title("12 de Octubre 325, Río Grande - Tel: xxxxxxx"));
        CameraPosition camera= new CameraPosition.Builder()
                .target(riocuarto)
                .zoom(4)
                .tilt(15)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));

    }
}

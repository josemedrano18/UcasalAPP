package com.example.myapplication.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Adapters.CarrerasAdapter;
import com.example.myapplication.entidades.VolleySingleton;
import com.example.myapplication.entidades.Carrera;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaCarreras.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaCarreras#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaCarreras extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerCarreras;
    ArrayList<Carrera> listaCarreras;

    ProgressDialog progress;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    public ListaCarreras() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaCarreras.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaCarreras newInstance(String param1, String param2) {
        ListaCarreras fragment = new ListaCarreras();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_lista_carreras, container, false);

        listaCarreras = new ArrayList<>();

       recyclerCarreras= vista.findViewById(R.id.idRecycler);
       recyclerCarreras.setLayoutManager(new LinearLayoutManager(this.getContext()));
       recyclerCarreras.setHasFixedSize(true);

       request= Volley.newRequestQueue(getContext());

        cargarWebService();

        return vista;

    }

    private void cargarWebService() {

        ProgressDialog progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando...");
        progress.show();

//        String ip=getString(R.string.ip);

        String url="http://localhost:8080/ucasal/ListaCarreras.php";

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

@Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());
        progress.hide();
    }

@Override
    public void onResponse(JSONObject response) {
       Carrera carrera=null;

        JSONArray json=response.optJSONArray("carrera");

        try {

            for (int i=0;i<json.length();i++){
                carrera=new Carrera();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                carrera.setTitulo(jsonObject.optString("titulo"));
                carrera.setNombre(jsonObject.optString("nombre"));
                carrera.setDuracion(jsonObject.optString("duracion"));
                listaCarreras.add(carrera);
            }
            progress.hide();
            CarrerasAdapter adapter=new CarrerasAdapter(listaCarreras);
            recyclerCarreras.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            progress.hide();
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

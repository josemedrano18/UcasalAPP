package com.example.myapplication.Adapters;


import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import com.example.myapplication.entidades.Carrera;
        import com.example.myapplication.R;
        import com.example.myapplication.Activities.Carreras;

import java.util.List;


/**
 * Created by CHENAO on 6/08/2017.
 */

public class CarrerasAdapter extends RecyclerView.Adapter<CarrerasAdapter.CarrerasHolder>{

    List<Carrera> listaCarreras;

    public CarrerasAdapter(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    @Override
    public CarrerasHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.carreras_list,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new CarrerasHolder(vista);
    }

    @Override
    public void onBindViewHolder(CarrerasHolder holder, int position) {
        holder.txtNombre.setText(listaCarreras.get(position).getNombre().toString());
        holder.txtTitulo.setText(listaCarreras.get(position).getTitulo().toString());
        holder.txtDuracionn.setText(listaCarreras.get(position).getDuracion().toString());
    }

    @Override
    public int getItemCount() {
        return listaCarreras.size();
    }

    public class CarrerasHolder extends RecyclerView.ViewHolder{

        TextView txtNombre,txtTitulo,txtDuracionn;

        public CarrerasHolder(View itemView) {
            super(itemView);
            txtNombre= (TextView) itemView.findViewById(R.id.txtNombre);
            txtTitulo= (TextView) itemView.findViewById(R.id.txtTitulo);
            txtDuracionn= (TextView) itemView.findViewById(R.id.txtDuracion);

        }
    }
}
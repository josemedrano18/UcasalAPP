package com.example.myapplication.ui;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.myapplication.R;

/**
 * Adaptador del recycler view
 */
public class AdaptadorDeCostos extends RecyclerView.Adapter<AdaptadorDeCostos.ExpenseViewHolder> {
    private Cursor cursor;
    private Context context;

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nom_carrera;
        public TextView duracion;
        public TextView eje_carrera;


        public ExpenseViewHolder(View v) {
            super(v);
            nom_carrera = (TextView)  v.findViewById(R.id.nom_carrera);
            duracion = (TextView)  v.findViewById(R.id.duracion);
            eje_carrera =(TextView)  v.findViewById(R.id.eje_carrera);


        }
    }

    public AdaptadorDeCostos(Context context) {
        this.context= context;

    }

    @Override
    public int getItemCount() {
        if (cursor!=null)
            return cursor.getCount();
        return 0;
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_layout, viewGroup, false);
        return new ExpenseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        String nom_carrera;
        String duracion;
        String eje_carrera;


        nom_carrera = cursor.getString(1);
        eje_carrera = cursor.getString(2);
        duracion = cursor.getString(3);

        viewHolder.nom_carrera.setText(nom_carrera);
        viewHolder.eje_carrera.setText(eje_carrera);
        viewHolder.duracion.setText(duracion);
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }
}

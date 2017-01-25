package agalvezmarco.wdil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by aleja on 25/01/2017.
 */

public class AdaptadorLibros extends ArrayAdapter<Libro> {

    private TextView titulo;
    private TextView info;

    public AdaptadorLibros(Context context, ArrayList<Libro> datos) {
        super(context, R.layout.elem_libro, datos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.elem_libro, null);

        Libro aux = getItem(position);

        titulo = (TextView) item.findViewById(R.id.nombreLibro);
        titulo.setText(aux.getNombre());

        info = (TextView) item.findViewById(R.id.pagsLibro);
        if (aux.getPagTotales() == 0) {
            info.setText(""+aux.getPagActual());
        } else {
            info.setText(aux.getPagActual() + " / " + aux.getPagTotales());
        }
        return item;
    }
}

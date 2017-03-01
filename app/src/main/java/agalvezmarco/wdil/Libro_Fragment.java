package agalvezmarco.wdil;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Libro_Fragment extends Fragment {

    private ListView lista;
    private ArrayList<Libro> datos;
    private FloatingActionButton fab;
    private AdaptadorLibros adaptador;
    private boolean puedoGuardar = true;


    public Libro_Fragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.libro_fragment, container, false);
        datos = new ArrayList<>();
        datos = Usuario.getUsuario().getLibros();
        lista = (ListView) rootView.findViewById(R.id.listLibros);

        rellenarLista();

        fab = (FloatingActionButton) rootView.findViewById(R.id.nuevoLibro);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearDialogoNuevoLibro().show();

            }
        });
        return rootView;
    }

    private AlertDialog crearDialogoNuevoLibro() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialognuevolibro, null);

        final EditText titulo = (EditText) v.findViewById(R.id.nombreLibro);
        final EditText pagActual = (EditText) v.findViewById(R.id.pagActualD);
        final EditText pagTotal = (EditText) v.findViewById(R.id.pagTotalD);
        builder.setView(v);

        builder.setPositiveButton("Crear!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Libro aux = new Libro();
                if (!(titulo.getText() == null) && !(titulo.getText().toString().equals("")))
                    aux.setNombre(titulo.getText().toString());

                else {
                    Toast.makeText(getActivity(), "No has introducido nombre, saliendo.", Toast.LENGTH_LONG);
                    puedoGuardar = false;
                    dialog.dismiss();
                }

                if (!(pagActual.getText() == null) && !(pagActual.getText().toString().equals("")))
                    aux.setPagActual(Integer.parseInt(pagActual.getText().toString()));
                else aux.setPagActual(0);

                if (!(pagTotal.getText() == null) && (!pagTotal.getText().toString().equals("")))
                    aux.setPagTotales(Integer.parseInt(pagTotal.getText().toString()));
                else aux.setPagTotales(0);

                if (puedoGuardar) {

                    datos.add(aux);
                    Usuario.getUsuario().setLibros(datos);
                    rellenarLista();
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancelar.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    public void rellenarLista() {

        adaptador = new AdaptadorLibros(getContext(), datos);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                crearDialogoEditarLibro(datos.get(position)).show();
            }
        });
        lista.setAdapter(adaptador);


    }

    private AlertDialog crearDialogoEditarLibro(Libro libro) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_editar_libro, null);

        final EditText pag = (EditText) v.findViewById(R.id.pagsLibroMenu);
        final TextView totales = (TextView) v.findViewById(R.id.totales);
        final Button subir = (Button) v.findViewById(R.id.subirPag);
        final Button bajar = (Button) v.findViewById(R.id.bajarPag);

        final Libro pos = libro;
        pag.setText(""+pos.getPagActual());
        totales.setText("/" + pos.getPagTotales());

        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pag.setText(""+(Integer.parseInt(pag.getText().toString()) + 1));
            }
        });

        bajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pag.setText(""+(Integer.parseInt(pag.getText().toString()) - 1));
            }
        });

        builder.setView(v);

        builder.setPositiveButton("Editar!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int index = datos.indexOf(pos);
                Libro aux = datos.get(index);

                aux.setPagActual(Integer.parseInt(pag.getText().toString()));
                datos.remove(index);
                datos.add(index, aux);

                Usuario.getUsuario().setLibros(datos);
                rellenarLista();

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancelar.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}

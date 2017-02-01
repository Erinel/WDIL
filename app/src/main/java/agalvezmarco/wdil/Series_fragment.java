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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by aleja on 19/12/2016.
 */

public class Series_fragment extends Fragment {

    private ArrayList<Serie> series;
    private AutoCompleteTextView entrada;
    private String[] nombres;
    private FloatingActionButton fab;
    private boolean puedoGuardar = true;
    private TextView temporadaTexto;
    private TextView capituloTexto;
    private Button addTemporada;
    private Button minusTemporada;
    private Button addCapitulo;
    private Button minusCapitulo;
    private TextView listaSeries;
    private Usuario usuario;


    public Series_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.series_fragment, container, false);
        entrada = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompletarSeries);
        asignarValoresAutocomplete();
        fab = (FloatingActionButton) rootView.findViewById(R.id.nuevaSerie);
        temporadaTexto = (TextView) rootView.findViewById(R.id.textSeason);
        capituloTexto = (TextView) rootView.findViewById(R.id.textCap);
        addCapitulo = (Button) rootView.findViewById(R.id.addCap);
        addTemporada = (Button) rootView.findViewById(R.id.addSeason);
        minusTemporada = (Button) rootView.findViewById(R.id.sustractSeason);
        minusCapitulo = (Button) rootView.findViewById(R.id.sustractCap);
        listaSeries = (TextView) rootView.findViewById(R.id.allSeries);
        estadoBotones(false);
        usuario.getUsuario();

        entrada.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0; i < series.size(); i++) {
                    if(entrada.getText().toString().equals(series.get(i).getNombre())) {
                        temporadaTexto.setText("" +series.get(i).getTemporada());
                        capituloTexto.setText("" +series.get(i).getCapitulo());
                        estadoBotones(true);
                        visibilidadBotones(View.VISIBLE);
                    }
                }


            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearDialogoNuevaSerie().show();

            }
        });

        addCapitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < series.size(); i++) {
                    if(entrada.getText().toString().equals(series.get(i).getNombre())) {
                        series.get(i).setCapitulo(series.get(i).getCapitulo() + 1);
                        usuario.setSeries(series);
                        capituloTexto.setText("" +series.get(i).getCapitulo());
                    }
                }
            }
        });

        minusCapitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < series.size(); i++) {
                    if(entrada.getText().toString().equals(series.get(i).getNombre())) {
                        series.get(i).setCapitulo(series.get(i).getCapitulo() - 1);
                        usuario.setSeries(series);
                        capituloTexto.setText("" +series.get(i).getCapitulo());
                    }
                }
            }
        });

        addTemporada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < series.size(); i++) {
                    if(entrada.getText().toString().equals(series.get(i).getNombre())) {
                        series.get(i).setTemporada(series.get(i).getTemporada() + 1);
                        usuario.setSeries(series);
                        temporadaTexto.setText("" +series.get(i).getTemporada());
                    }
                }
            }
        });

        minusTemporada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < series.size(); i++) {
                    if(entrada.getText().toString().equals(series.get(i).getNombre())) {
                        series.get(i).setTemporada(series.get(i).getTemporada() - 1);
                        usuario.setSeries(series);
                        temporadaTexto.setText("" +series.get(i).getTemporada());
                    }
                }
            }
        });

        listaSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mostrar = "";
                if(!series.isEmpty()) {

                    for (Serie s : series) {

                        mostrar += s.getNombre() + ": (T" + s.getTemporada() + ", Cp" + s.getCapitulo() + ") \n";

                    }
                }

                else {

                    mostrar = "Aun no has introducido ninguna serie!";

                }
                listaSeries.setText("");
                listaSeries.setText(mostrar);
            }
        });

        return rootView;
    }

    //Asigna los valores al campo de texto para rellenar.
    private void asignarValoresAutocomplete() {
        series = Usuario.getUsuario().getSeries();

        nombres = new String[series.size()];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = series.get(i).getNombre();
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, nombres);
        entrada.setAdapter(adaptador);
    }
    private AlertDialog crearDialogoNuevaSerie() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialognuevaserie, null);

        final EditText nombre = (EditText) v.findViewById(R.id.nombreNuevo);
        final EditText temporada = (EditText) v.findViewById(R.id.temporadaNuevo);
        final EditText capitulo = (EditText) v.findViewById(R.id.capituloNuevo);
        builder.setView(v);

        builder.setPositiveButton("Crear!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Serie aux = new Serie();
                if (!(nombre.getText() == null) && !(nombre.getText().toString().equals("")))
                    aux.setNombre(nombre.getText().toString());

                else {
                    Toast.makeText(getActivity(), "No has introducido nombre, saliendo.", Toast.LENGTH_LONG);
                    puedoGuardar = false;
                    dialog.dismiss();
                }
                if (!(temporada.getText() == null) && !(temporada.getText().toString().equals("")))
                    aux.setTemporada(Integer.parseInt(temporada.getText().toString()));
                else aux.setTemporada(0);
                if (!(capitulo.getText() == null) && !(capitulo.getText().toString().equals("")))
                    aux.setCapitulo(Integer.parseInt(capitulo.getText().toString()));
                else aux.setCapitulo(0);
                if (puedoGuardar) {
                    asignarValoresAutocomplete();
                    usuario.setSeries(series);
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

    private void estadoBotones(Boolean estado) {
        addCapitulo.setEnabled(estado);
        addTemporada.setEnabled(estado);
        minusCapitulo.setEnabled(estado);
        minusTemporada.setEnabled(estado);
    }

    private void visibilidadBotones(int estado) {
        addCapitulo.setVisibility(estado);
        addTemporada.setVisibility(estado);
        minusCapitulo.setVisibility(estado);
        minusTemporada.setVisibility(estado);
    }


}


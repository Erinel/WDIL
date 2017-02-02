package agalvezmarco.wdil;

import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
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


public class Manga_Fragment extends Fragment {

    private ArrayList<Manga> mangas;
    private AutoCompleteTextView entrada;
    private String[] nombres;
    private FloatingActionButton fab;
    private boolean puedoGuardar = true;
    private TextView capituloTexto;
    private Button addCapitulo;
    private Button minusCapitulo;


    public Manga_Fragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.manga_fragment, container, false);
        entrada = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompletarManga);
        mangas = Usuario.getUsuario().getMangas();
        if(!mangas.isEmpty())
        asignarValoresAutocomplete();
        fab = (FloatingActionButton) rootView.findViewById(R.id.nuevoManga);
        capituloTexto = (TextView) rootView.findViewById(R.id.textCap);
        addCapitulo = (Button) rootView.findViewById(R.id.addCapM);
        minusCapitulo = (Button) rootView.findViewById(R.id.sustractCapM);
        estadoBotones(false);

        entrada.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0; i < mangas.size(); i++) {
                    if(entrada.getText().toString().equals(mangas.get(i).getNombre())) {
                        capituloTexto.setText("" +mangas.get(i).getCapitulo());
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

                for(int i = 0; i < mangas.size(); i++) {
                    if(entrada.getText().toString().equals(mangas.get(i).getNombre())) {
                        mangas.get(i).setCapitulo(mangas.get(i).getCapitulo() + 1);
                        capituloTexto.setText("" +mangas.get(i).getCapitulo());
                       Usuario.getUsuario().setMangas(mangas);
                    }
                }
            }
        });

        minusCapitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < mangas.size(); i++) {
                    if(entrada.getText().toString().equals(mangas.get(i).getNombre())) {
                        mangas.get(i).setCapitulo(mangas.get(i).getCapitulo() - 1);
                        Usuario.getUsuario().setMangas(mangas);
                        capituloTexto.setText("" +mangas.get(i).getCapitulo());
                    }
                }
            }
        });


        return rootView;
    }

    //Asigna los valores al campo de texto para rellenar.
    private void asignarValoresAutocomplete() {


        nombres = new String[mangas.size()];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = mangas.get(i).getNombre();
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, nombres);
        entrada.setAdapter(adaptador);
    }
    private AlertDialog crearDialogoNuevaSerie() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialognuevomanga, null);

        final EditText nombre = (EditText) v.findViewById(R.id.nombreNuevoM);
        final EditText capitulo = (EditText) v.findViewById(R.id.capituloNuevoM);
        builder.setView(v);

        builder.setPositiveButton("Crear!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Manga aux = new Manga();
                if (!(nombre.getText() == null) && !(nombre.getText().toString().equals("")))
                    aux.setNombre(nombre.getText().toString());

                else {
                    Toast.makeText(getActivity(), "No has introducido nombre, saliendo.", Toast.LENGTH_LONG);
                    puedoGuardar = false;
                    dialog.dismiss();
                }

                if (!(capitulo.getText() == null) && !(capitulo.getText().toString().equals("")))
                    aux.setCapitulo(Integer.parseInt(capitulo.getText().toString()));
                else aux.setCapitulo(0);
                if (puedoGuardar) {
                    asignarValoresAutocomplete();
                    Usuario.getUsuario().setMangas(mangas);
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
        minusCapitulo.setEnabled(estado);
    }

    private void visibilidadBotones(int estado) {
        addCapitulo.setVisibility(estado);
        minusCapitulo.setVisibility(estado);
    }

}

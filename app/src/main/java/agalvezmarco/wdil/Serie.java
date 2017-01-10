package agalvezmarco.wdil;

import android.content.ContentValues;

/**
 * Created by aleja on 20/12/2016.
 */

public class Serie {

    private String nombre;
    private int temporada;
    private int capitulo;

    public Serie(String nombre, int temporada, int capitulo) {

        this.nombre = nombre;
        this.temporada = temporada;
        this.capitulo = capitulo;

    }

    public Serie() {

    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(SeriesContract.SerieEntry.NOMBRE, nombre);
        cv.put(SeriesContract.SerieEntry.TEMPORADA, temporada);
        cv.put(SeriesContract.SerieEntry.CAPITULO, capitulo);
        return cv;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public int getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(int capitulo) {
        this.capitulo = capitulo;
    }
}

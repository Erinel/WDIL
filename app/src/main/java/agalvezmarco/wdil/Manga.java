package agalvezmarco.wdil;

import android.content.ContentValues;

/**
 * Created by alu20911095r on 12/01/17.
 */

public class Manga {

    private String nombre;

    public int getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(int capitulo) {
        this.capitulo = capitulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(SeriesContract.MangaEntry.NOMBRE, nombre);
        cv.put(SeriesContract.MangaEntry.CAPITULO, capitulo);
        return cv;
    }

    private int capitulo;

    public Manga() {}

    public Manga(String nombre, int capitulo) {

        this.nombre = nombre;
        this.capitulo = capitulo;

    }
}

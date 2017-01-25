package agalvezmarco.wdil;

import android.content.ContentValues;

/**
 * Created by aleja on 25/01/2017.
 */

public class Libro {

    private String nombre;

    public int getPagTotales() {
        return pagTotales;
    }

    public void setPagTotales(int pagTotales) {
        this.pagTotales = pagTotales;
    }

    public int getPagActual() {
        return pagActual;
    }

    public void setPagActual(int pagActual) {
        this.pagActual = pagActual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private int pagActual;
    private int pagTotales;

    public Libro() {

    }

    public Libro(String nombre, int pagActual, int pagTotales) {

        this.nombre = nombre;
        this.pagActual = pagActual;
        this.pagTotales = pagTotales;

    }

    public Libro(String nombre, int pagActual) {

        this.nombre = nombre;
        this.pagActual = pagActual;
        this.pagTotales = 0;

    }

    public ContentValues toContentValues() {

        ContentValues ret = new ContentValues();
        ret.put(DBContract.LibroEntry.NOMBRE, nombre);
        ret.put(DBContract.LibroEntry.PAGACTUAL, pagActual);
        ret.put(DBContract.LibroEntry.PAGTOTALES, pagTotales);
        return ret;

    }

}

package agalvezmarco.wdil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by aleja on 20/12/2016.
 */

public class GestionDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Series.db";

    public GestionDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBContract.SerieEntry.TABLE_NAME + " ("
                + DBContract.SerieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DBContract.SerieEntry.NOMBRE + " TEXT NOT NULL,"
                + DBContract.SerieEntry.TEMPORADA + " INTEGER NOT NULL,"
                + DBContract.SerieEntry.CAPITULO + " INTEGER NOT NULL,"
                + "UNIQUE (" + DBContract.SerieEntry.NOMBRE + "))");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBContract.MangaEntry.TABLE_NAME + "("
                + DBContract.MangaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBContract.MangaEntry.NOMBRE + " TEXT NOT NULL,"
                + DBContract.MangaEntry.CAPITULO + " INTEGER NOT NULL, "
                + "UNIQUE (" + DBContract.MangaEntry.NOMBRE + "))");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBContract.LibroEntry.TABLE_NAME + "("
                + DBContract.LibroEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBContract.LibroEntry.NOMBRE + " TEXT NOT NULL,"
                + DBContract.LibroEntry.PAGACTUAL + " INTEGER NOT NULL,"
                + DBContract.LibroEntry.PAGTOTALES + " INTEGER, "
                + "UNIQUE (" + DBContract.LibroEntry.NOMBRE + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long guardarSerie(Serie serie) {

        SQLiteDatabase db = getWritableDatabase();
        return db.insert(DBContract.SerieEntry.TABLE_NAME, null, serie.toContentValues());

    }

    public ArrayList<Serie> recuperarDatos() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + DBContract.SerieEntry.TABLE_NAME;
        Cursor c = db.rawQuery(query, null);
        ArrayList<Serie> aux = new ArrayList<>();

        while (c.moveToNext()) {
            String nombre = c.getString(c.getColumnIndex(DBContract.SerieEntry.NOMBRE));
            int temporada = c.getInt(c.getColumnIndex(DBContract.SerieEntry.TEMPORADA));
            int capitulo = c.getInt(c.getColumnIndex(DBContract.SerieEntry.CAPITULO));
            aux.add(new Serie(nombre, temporada, capitulo));

        }

        return aux;
    }

    public int updateSerie(Serie serie) {

        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("temporada", serie.getTemporada());
        cv.put("capitulo", serie.getCapitulo());

        return db.update(DBContract.SerieEntry.TABLE_NAME, cv, DBContract.SerieEntry.NOMBRE + "= '" + serie.getNombre() + "'", null);

    }

    public long guardarManga(Manga manga) {

        SQLiteDatabase db = getWritableDatabase();
        return db.insert(DBContract.MangaEntry.TABLE_NAME, null, manga.toContentValues());

    }

    public ArrayList<Manga> recuperarDatosManga() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + DBContract.MangaEntry.TABLE_NAME;
        Cursor c = db.rawQuery(query, null);
        ArrayList<Manga> aux = new ArrayList<>();

        while (c.moveToNext()) {
            String nombre = c.getString(c.getColumnIndex(DBContract.MangaEntry.NOMBRE));
            int capitulo = c.getInt(c.getColumnIndex(DBContract.MangaEntry.CAPITULO));
            aux.add(new Manga(nombre, capitulo));

        }

        return aux;
    }

    public int updateManga(Manga manga) {

        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBContract.MangaEntry.CAPITULO, manga.getCapitulo());

        return db.update(DBContract.MangaEntry.TABLE_NAME, cv, DBContract.MangaEntry.NOMBRE + "= '" + manga.getNombre() + "'", null);

    }

    public long guardarLibro(Libro libro) {

        SQLiteDatabase db = getWritableDatabase();
        return db.insert(DBContract.LibroEntry.TABLE_NAME, null, libro.toContentValues());

    }

    public ArrayList<Libro> recuperarDatosLibro() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + DBContract.LibroEntry.TABLE_NAME;
        Cursor c = db.rawQuery(query, null);
        ArrayList<Libro> aux = new ArrayList<>();

        while (c.moveToNext()) {
            String nombre = c.getString(c.getColumnIndex(DBContract.LibroEntry.NOMBRE));
            int pagActual = c.getInt(c.getColumnIndex(DBContract.LibroEntry.PAGACTUAL));
            int pagTotales = c.getInt(c.getColumnIndex(DBContract.LibroEntry.PAGTOTALES));
            aux.add(new Libro(nombre, pagActual, pagTotales));

        }

        return aux;
    }

    public int updateLibro(Libro libro) {

        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBContract.LibroEntry.PAGACTUAL, libro.getPagActual());
        cv.put(DBContract.LibroEntry.PAGTOTALES, libro.getPagTotales());

        return db.update(DBContract.LibroEntry.TABLE_NAME, cv, DBContract.LibroEntry.NOMBRE + "= '" + libro.getNombre() + "'", null);

    }

}

package agalvezmarco.wdil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by aleja on 20/12/2016.
 */

public class GestionDBSeries extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Series.db";

    public GestionDBSeries(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + SeriesContract.SerieEntry.TABLE_NAME + " ("
                + SeriesContract.SerieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SeriesContract.SerieEntry.NOMBRE + " TEXT NOT NULL,"
                + SeriesContract.SerieEntry.TEMPORADA + " INTEGER NOT NULL,"
                + SeriesContract.SerieEntry.CAPITULO + " INTEGER NOT NULL,"
                + "UNIQUE (" + SeriesContract.SerieEntry.NOMBRE + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long guardarSerie(Serie serie) {

        SQLiteDatabase db = getWritableDatabase();
        return db.insert(SeriesContract.SerieEntry.TABLE_NAME, null, serie.toContentValues());

    }

    public ArrayList<Serie> recuperarDatos() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + SeriesContract.SerieEntry.TABLE_NAME;
        Cursor c = db.rawQuery(query, null);
        ArrayList<Serie> aux = new ArrayList<>();

        while(c.moveToNext()) {
            String nombre = c.getString(c.getColumnIndex(SeriesContract.SerieEntry.NOMBRE));
            int temporada = c.getInt(c.getColumnIndex(SeriesContract.SerieEntry.TEMPORADA));
            int capitulo = c.getInt(c.getColumnIndex(SeriesContract.SerieEntry.CAPITULO));
            aux.add(new Serie(nombre, temporada, capitulo));

        }

        return aux;
    }

    public int updateSerie(Serie serie) {

        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("temporada", serie.getTemporada());
        cv.put("capitulo", serie.getCapitulo());

        return db.update(SeriesContract.SerieEntry.TABLE_NAME, cv, SeriesContract.SerieEntry.NOMBRE + "= '" + serie.getNombre() + "'", null);

    }



}

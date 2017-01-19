package agalvezmarco.wdil;

import android.provider.BaseColumns;

/**
 * Created by aleja on 20/12/2016.
 */

public class SeriesContract {

    public static abstract class SerieEntry implements BaseColumns {
        public static final String TABLE_NAME ="serie";

        public static final String _ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String TEMPORADA = "temporada";
        public static final String CAPITULO = "capitulo";
    }

    public static abstract class MangaEntry implements BaseColumns {

        public static final String TABLE_NAME ="manga";

        public static final String _ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String CAPITULO = "capitulo";

    }

}

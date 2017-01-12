package agalvezmarco.wdil;

import android.provider.BaseColumns;

/**
 * Created by aleja on 20/12/2016.
 */

public class MangasContract {

    public static abstract class SerieEntry implements BaseColumns {
        public static final String TABLE_NAME ="serie";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String TEMPORADA = "temporada";
        public static final String CAPITULO = "capitulo";
    }
}

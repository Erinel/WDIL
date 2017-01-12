package agalvezmarco.wdil;

import android.provider.BaseColumns;

/**
 * Created by aleja on 12/01/2017.
 */

public class MangasContract {

    public static abstract class MangaEntry implements BaseColumns {
        public static final String TABLE_NAME ="manga";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String CAPITULO = "capitulo";
    }
}

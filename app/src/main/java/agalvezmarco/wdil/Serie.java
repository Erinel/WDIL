package agalvezmarco.wdil;


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

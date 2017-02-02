package agalvezmarco.wdil;

import java.util.ArrayList;


public class Usuario {

    private String email;
    private String nick;
    private ArrayList<Libro> libros;
    private ArrayList<Manga> mangas;
    private ArrayList<Serie> series;
    private static Usuario usuario;


    public static Usuario getUsuario(){
        if(usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    private Usuario() {
        series = new ArrayList<>();
        mangas = new ArrayList<>();
        libros = new ArrayList<>();
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public void setLibros(ArrayList<Libro> libros) {
        this.libros = libros;
    }

    public ArrayList<Manga> getMangas() {
        return mangas;
    }

    public void setMangas(ArrayList<Manga> mangas) {
        this.mangas = mangas;
    }

    public ArrayList<Serie> getSeries() {
        return series;
    }

    public void setSeries(ArrayList<Serie> series) {
        this.series = series;
    }



}

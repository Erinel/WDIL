package agalvezmarco.wdil;

import java.util.ArrayList;

/**
 * Created by aleja on 28/01/2017.
 */

public class Usuario {

    private String email;
    private String nick;
    private ArrayList<Libro> libros;
    private ArrayList<Manga> mangas;
    private ArrayList<Serie> series;
    private static Usuario usuario;

    public static Usuario getUsuario(String email, String nick){
        if(usuario == null) {
            usuario = new Usuario(email, nick);
        }
        return usuario;
    }

    public static Usuario getUsuario(){

        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    private Usuario() {

    }

    private Usuario(String email, String nick) {
        this.email = email;
        this.nick = nick;
    }



    //Getters and setters

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

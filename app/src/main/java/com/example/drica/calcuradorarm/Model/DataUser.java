package com.example.drica.calcuradorarm.Model;

public class DataUser {




    private String nombre;
    private String apellido;
    private int edad;
    private int talla;

    public DataUser() {
    }

    public DataUser( String  nombre, String apellido, int edad, int talla) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.talla = talla;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getTalla() {
        return talla;
    }

    public void setTalla(int talla) {
        this.talla = talla;
    }
}

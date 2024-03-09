package com.usbbog.cbs_app.model;

public class Users {

    private String nombre;
    private int edad;
    private String correo;
    private String genero;
    private int dni;
    private String password;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Users{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", correo='" + correo + '\'' +
                ", genero='" + genero + '\'' +
                ", dni=" + dni +
                ", password=" + password +
                '}';
    }

    public Users(String nombre, int edad, String correo, String genero, int dni, String password) {
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        this.genero = genero;
        this.dni = dni;
        this.password = password;
    }
}

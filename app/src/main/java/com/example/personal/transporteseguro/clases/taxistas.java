package com.example.personal.transporteseguro.clases;

/**
 * Created by Personal on 09/12/2018.
 */

public class taxistas {

    String latitude;
    String longitude;
    String telefono;
    String cedula;
    String nombre;
    String color;
    String placa;
    String clase;
    String marca;
    public taxistas(String latitude, String longitude, String telefono, String cedula, String nombre, String color, String placa, String clase, String marca, String direccion) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.telefono = telefono;
        this.cedula = cedula;
        this.nombre = nombre;
        this.color = color;
        this.placa = placa;
        this.clase = clase;
        this.marca = marca;
        this.direccion = direccion;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPlaca() {
        return placa;
    }

    public String getColor() {
        return color;
    }

    public String getMarca() {
        return marca;
    }

    public String getClase() {
        return clase;
    }

    public String getDireccion() {
        return direccion;
    }

    String direccion;







}

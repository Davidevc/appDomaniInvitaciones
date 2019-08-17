package com.example.domanisistemainvitaciones.modelos;

public class ClienteEntrada {

    private String Nombre;
    private String Correo;
    private String dia;
    private String mes;

    public ClienteEntrada() {
    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    @Override
    public String toString() {
        return "ClienteEntrada{" +
                "Nombre='" + Nombre + '\'' +
                ", Correo='" + Correo + '\'' +
                ", dia='" + dia + '\'' +
                ", mes='" + mes + '\'' +
                '}';
    }
}

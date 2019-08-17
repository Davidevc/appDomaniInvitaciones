package com.example.domanisistemainvitaciones.modelos;

public class ClienteEntrada {

    public int uid;
    public String Nombre;
    public String Correo;
    public String FechaNac;

    public ClienteEntrada() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public String getFechaNac() {
        return FechaNac;
    }

    public void setFechaNac(String fechaNac) {
        FechaNac = fechaNac;
    }

    @Override
    public String toString() {
        return "ClienteEntrada{" +
                "uid=" + uid +
                ", Nombre='" + Nombre + '\'' +
                ", Correo='" + Correo + '\'' +
                ", FechaNac='" + FechaNac + '\'' +
                '}';
    }
}

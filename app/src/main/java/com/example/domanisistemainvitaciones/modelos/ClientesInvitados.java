package com.example.domanisistemainvitaciones.modelos;

public class ClientesInvitados {
    private String nombre;
    private String correo;
    private Long id;

    public ClientesInvitados(String nombre, String correo, Long id) {
        this.nombre = nombre;
        this.correo = correo;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClientesInvitados{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

package com.example.domanisistemainvitaciones.modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class ClientesInvitados implements Parcelable {
    private String nombre;
    private String correo;
    private Long id;

    public ClientesInvitados(String nombre, String correo, Long id) {
        this.nombre = nombre;
        this.correo = correo;
        this.id = id;
    }

    protected ClientesInvitados(Parcel in) {
        nombre = in.readString();
        correo = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
    }

    public static final Creator<ClientesInvitados> CREATOR = new Creator<ClientesInvitados>() {
        @Override
        public ClientesInvitados createFromParcel(Parcel in) {
            return new ClientesInvitados(in);
        }

        @Override
        public ClientesInvitados[] newArray(int size) {
            return new ClientesInvitados[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(correo);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
    }
}

package com.example.a41638707.proyectofinal;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Date;

public class Libros implements Serializable{

    private int IdLibro;
    private String Nombre;
    private String Descripcion;
    private String Imagen;
    private int IdUsuario;
    private int Anio;
    private int IdMateria;
    private String Vendido;
    private String Materia;

    public Libros(int idlibro, String nombre, String descripcion, String imagen, int idUsuario, int idMateria, String materia, String vendido)
    {
        IdLibro=idlibro;
        Nombre=nombre;
        Descripcion=descripcion;
        Imagen=imagen;
        IdUsuario=idUsuario;
        IdMateria=idMateria;
        Vendido=vendido;
        Materia=materia;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getDesc() + " " + getImg() + " " + getIdM() + " " + getVendido() + " " + getMat();
    }



    public int getId()
    {
        return IdLibro;
    }
    public String getMat()
    {
        return Materia;
    }
    public String getNombre()
    {
        return Nombre;
    }
    public String getDesc()
    {
        return Descripcion;
    }
    public String getImg()
    {
        return Imagen;
    }
    public int getIdU()
    {
        return IdUsuario;
    }
    public Integer getAño()
    {
        return Anio;
    }
    public int getIdM()
    {
        return IdMateria;
    }
    public String getVendido()
    {
        return Vendido;
    }

}

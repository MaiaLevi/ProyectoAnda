package com.example.a41638707.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class VerLibro extends AppCompatActivity {
    int IdLibro;
    String Nombre;
    String Descripcion;
    String Imagen;
    int IdUsuario;
    int Año;
    String Materia;
    int IdMateria;
    String Vendido;
    TextView edtNombre;
    TextView txvDesc;
    TextView txvImg;
    TextView txvidmat;
    TextView txvvendido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_libro);

        Intent elIntent = getIntent();
        IdLibro = elIntent.getIntExtra("ParamId",0);
        Nombre = elIntent.getStringExtra("ParamNomb");
        Descripcion=elIntent.getStringExtra("ParamDesc");
        Imagen = elIntent.getStringExtra("ParamImg");
        IdUsuario=elIntent.getIntExtra("ParamIDU",0);
        IdMateria=elIntent.getIntExtra("ParamIDM",1);
        Vendido = elIntent.getStringExtra("ParamVendido");
        Materia = elIntent.getStringExtra("ParamMateria");

        obtenerREferencias();
        edtNombre.setText(Nombre);
        if (Vendido=="0")
        {
            txvvendido.setText("En Venta");
        }else
        {
            txvvendido.setText("Vendido");
        }

        txvDesc.setText(Descripcion);
        txvImg.setText(Imagen);
        txvidmat.setText(Materia + "");

    }
    public void obtenerREferencias()
    {
        edtNombre = (TextView) findViewById(R.id.edtNombre);
        txvDesc=(TextView) findViewById(R.id.txvDesc);
        txvImg=(TextView) findViewById(R.id.txvimg);
        txvidmat=(TextView) findViewById(R.id.idmateria);
        txvvendido = (TextView) findViewById(R.id.txvvendido);

    }
}

package com.example.a41638707.proyectofinal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Version;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ListarLibrosPropios extends AppCompatActivity {
    public static final String PARAMETRO1 = "com.example.polshu.tpn01.PARAMETRO1";
    ListView lstLibros;
    Libros MiLibro;
    ArrayList<Libros> listaLibros =new ArrayList<Libros>();
    Button btnAtras;
    List<Libros> listLibros = new ArrayList<Libros>();
    ArrayAdapter<Libros> adaptador=null;
    ImageView imgAgregar, imgModificar, imgEliminar;
    Libros libroSeleccionado;
    ProgressDialog progressDialog;
    Libros Libroselec;
    String url;
    int idUsuario=1;
    boolean click=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_libros_propios);
        ObtenerReferencias();
        progressDialog=new ProgressDialog(this);
        url="http://daiuszw.hol.es/bd/listarLibrosPropios.php?IdUsuario=";
        url+=idUsuario;
        new listarLibros().execute(url);
        if (adaptador!=null)
        {adaptador.notifyDataSetChanged();
        }
        lstLibros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                libroSeleccionado= listaLibros.get(position);
                int Id = libroSeleccionado.getId();
                String nombr =libroSeleccionado.getNombre();
                String des = libroSeleccionado.getDesc();
                String img = libroSeleccionado.getImg();
                int idu = libroSeleccionado.getIdU();
                int idM = libroSeleccionado.getIdM();
                String Vendido=libroSeleccionado.getVendido();
                int Año = libroSeleccionado.getAño();
                String Materia=libroSeleccionado.getMat();
                ActivityVerL(Id, nombr, des, img, idu, idM, Materia,Vendido, Año);
            }
        });
        imgAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IniciarAgregarActividad();
            }
        });
        imgModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imgEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
    private void ActivityVerL(int Id, String nombr, String des, String img, int idu, int idM, String Materia, String Vendido, int Año)
    {
        Intent nuevaActivity = new Intent(this, VerLibro.class);
        Bundle datos = new Bundle();
        datos.putInt("ParamId", Id);
        datos.putString("ParamNomb", nombr);
        datos.putString("ParamDesc",des);
        datos.putString("ParamImg",img);
        datos.putInt("ParamIDU",idu);
        datos.putInt("ParamIDM",idM);
        datos.putString("ParamMateria",Materia);
        datos.putString("ParamVendido",Vendido);
        nuevaActivity.putExtras(datos);
        startActivity(nuevaActivity);
    }
    private void ObtenerReferencias()
    {
        lstLibros=(ListView)findViewById(R.id.lstLibros);
        imgAgregar=(ImageView)findViewById(R.id.imgAgregar);
        imgModificar=(ImageView)findViewById(R.id.imgModificar);
        imgEliminar=(ImageView)findViewById(R.id.imgEliminar);
    }
    private void IniciarAgregarActividad()
    {
       //Activity agregar
    }
    private void IniciarModificarActividad(int i)
    {
        //activity modificar
    }

    private Dialog confirmarEliminar(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert Dialog");
        builder.setMessage("¿Está seguro que desea eliminar el libro?");
        builder.setPositiveButton("Eliminar", new  DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Diálogos", "Confirmación Aceptada.");
               // EliminarLibro(Libroselec.getId());
                new listarLibros().execute(url);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Diálogos", "Confirmación Cancelada.");
                dialog.cancel();
            }
        });
        return builder.create();
    }
    private class listarLibros extends AsyncTask<String, Void, ArrayList<Libros>> {
        private OkHttpClient client = new OkHttpClient();
        @Override
        protected ArrayList<Libros> doInBackground(String... params) {
            String url = params[0];
            Request request = new Request.Builder()
                    //error aca
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();  // Llamado al API
                return parsearLibros(response.body().string());      // Convierto el resultado en Evento

            } catch (IOException | JSONException e) {
                Log.d("Error", e.getMessage());                          // Error de Network o al parsear JSON
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.show();}
        @Override
        protected void onPostExecute(ArrayList<Libros> listaLibros) {
            super.onPostExecute(listaLibros);
            progressDialog.dismiss();
            //adapter
            adaptador = new ArrayAdapter<Libros>(ListarLibrosPropios.this, android.R.layout.simple_list_item_1, listaLibros);
            lstLibros.setAdapter(adaptador);
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        ArrayList<Libros> parsearLibros (String JSONstring) throws JSONException {
            JSONObject json = new JSONObject(JSONstring);
            JSONArray respJSON = json.getJSONArray("result");
            for (int i = 0; i < respJSON.length(); i++)
            {
                JSONObject obj = respJSON.getJSONObject(i);
                int idLibro = obj.getInt("IdLibro");
                String Nombre = obj.getString("Nombre");
                String Descr = obj.getString("Descripcion");
                String Imagen = obj.getString("Imagen");
                int IdUsuario = 1;
                int IdMateria = obj.getInt("IdMateria");
                String Materia = obj.getString("NombreMat");
                String Vendido = obj.getString("Vendido");
                Libros unLibro = new Libros (idLibro,Nombre,Descr,Imagen,IdUsuario,IdMateria,Materia, Vendido);
                listaLibros.add(unLibro);
            }
            return listaLibros;
        }
    }
   /* private class traerLibro extends AsyncTask<String, Void, Libros> {
        private OkHttpClient client = new OkHttpClient();
        @Override
        protected Libros doInBackground(String... params) {
            String url = params[0];

            Request request = new Request.Builder()
                    //error aca
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();  // Llamado al API
                return parsearLibro(response.body().string());      // Convierto el resultado en Evento

            } catch (IOException | JSONException e) {
                Log.d("Error", e.getMessage());                          // Error de Network o al parsear JSON
                return null;
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.show();}
        @Override
        protected void onPostExecute(Libros libro) {
            super.onPostExecute(libro);
            progressDialog.dismiss();
            edtDescr.setText(MiLibro.getImg());
        }
        Evento parsearLibro(String JSONstring) throws JSONException {
            JSONObject json = new JSONObject(JSONstring);                 // Convierto el String recibido a JSONObject
            int id = json.getInt("IdLibro");
            String nombre = json.getString("Nombre");
            String Imagen= json.getString("Imagen");
            String descripcion = json.getString("Descripcion");
            int idMateria = json.getInt("IdMateria");
            int idUsu=1;
            String Vendido = json.getString("Vendido");
            MiLibro = new Libros(id, nombre, descripcion, Imagen, idUsu, idMateria, Vendido);
            return MiLibro;
        }
    }*/
}

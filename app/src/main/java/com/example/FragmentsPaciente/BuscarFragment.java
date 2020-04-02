package com.example.FragmentsPaciente;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.SeccionCitaMedica.RecylclerViewAdapter;
import com.example.Usuarios.Medico;
import com.example.Usuarios.Usuario;
import com.example.Usuarios.UsuarioFactory;
import com.example.proyectofinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuscarFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    RecyclerView recyclerViewMedicos;
    private ArrayList<Medico> medicos;
    private ProgressDialog dialog;
    private ArrayList<Medico> medicosCopia;
    private RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);


        //Solo de ejemplo
        //getListMedicos();
        recyclerViewMedicos = view.findViewById(R.id.RecylerListMedicos);
        recyclerViewMedicos.setLayoutManager(new LinearLayoutManager(getContext()));

        request = Volley.newRequestQueue(getContext());

        dialog = new ProgressDialog(getContext());

        CargarWebService();

        medicos = new ArrayList<>();
        medicosCopia = new ArrayList<Medico>();

        SearchView searchView = view.findViewById(R.id.svBuscarTodp);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarMedico(newText);
                return false;
            }
        });

        return view;
    }

    public void CargarWebService(){

        //dialog = new ProgressDialog(getContext());
        dialog.setMessage("Consultando lista de medicos...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONConsultarListaMedicos.php";

        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        Medico medico;
        UsuarioFactory usuarioFactory = new UsuarioFactory();

        JSONArray json = response.optJSONArray("medico");

        try {
            for (int i = 0; i < json.length(); i++){
                //Patron
                medico = (Medico)usuarioFactory.getUsuario("medico");
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                String sexo = jsonObject.getString("Sexo");
                if(sexo.equalsIgnoreCase("masculino")){
                    medico.setNombre("Dr. " + jsonObject.getString("Nombres") + " " + jsonObject.getString("Apellidos"));
                }
                else{
                    medico.setNombre("Dra. " + jsonObject.getString("Nombres") + " " + jsonObject.getString("Apellidos"));
                }
                medico.setEspecialidad(jsonObject.getString("Especialidad"));
                medico.setHospital(jsonObject.getString("Hospital"));
                medico.setIdMedico(jsonObject.getString("IdMedico"));
                medico.setFoto(R.drawable.medico1);

                medicos.add(medico);
            }
            dialog.dismiss();
            RecylclerViewAdapter adapter = new RecylclerViewAdapter(medicos);
            recyclerViewMedicos.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            dialog.dismiss();
            Toast.makeText(getContext(), "No se ha podido establecer conexion con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "Ha ocurrido un error al consultar los medicos", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    //Ejemplo de como se llenara la lista de los medicos
    private ArrayList<Medico> getListMedicos(){
        ArrayList<Medico> medicos = new ArrayList<>();
        //las imagens son de prueba, las pueden borrar
        medicos.add(new Medico("Daniel Tejada Montero","Medico General","El Morgan",R.drawable.medico1));
        medicos.add(new Medico("Andres Guzman","Medico General","Hospital San Antonio",R.drawable.medico2));
        medicos.add(new Medico("Eiron Diaz","Medico General","Hospital San Antonio",R.drawable.medico2));
        return medicos;
    }

    public void buscarMedico(String frase){

        medicosCopia.clear();
        for (Medico medico: medicos){
            String nombre = medico.getNombre();
            nombre = nombre.toLowerCase();

            if(nombre.contains(frase)){
                medicosCopia.add(medico);
            }
        }

        RecylclerViewAdapter adapter = new RecylclerViewAdapter(medicosCopia);
        recyclerViewMedicos.setAdapter(adapter);
    }
}

package com.example.FragmentsMedico;

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
import com.example.SeccionCitaMedica.RecylclerViewAdapterPac;
import com.example.Usuarios.Medico;
import com.example.Usuarios.Paciente;
import com.example.Usuarios.Usuario;
import com.example.Usuarios.UsuarioFactory;
import com.example.proyectofinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MedicoBuscarFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    RecyclerView recyclerViewPacientes;
    private ArrayList<Paciente> pacientes;
    private ProgressDialog dialog;
    private ArrayList<Paciente> pacientesCopia;
    private RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar_medico, container, false);

        recyclerViewPacientes = view.findViewById(R.id.RecylerListPacientes);
        recyclerViewPacientes.setLayoutManager(new LinearLayoutManager(getContext()));

        request = Volley.newRequestQueue(getContext());

        dialog = new ProgressDialog(getContext());

        CargarWebService();

        pacientes = new ArrayList<>();
        pacientesCopia = new ArrayList<Paciente>();

        SearchView searchView = view.findViewById(R.id.svBuscarTodp);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarPaciente(newText);
                return false;
            }
        });

        return view;
    }

    public void CargarWebService(){

        //dialog = new ProgressDialog(getContext());
        dialog.setMessage("Consultando lista de pacientes...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONConsultarListaPacientes.php";

        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        //UsuarioFactory factory = new UsuarioFactory();
        Paciente paciente;

        JSONArray json = response.optJSONArray("paciente");

        try {
            for (int i = 0; i < json.length(); i++){
                //paciente = factory.getUsuario("paciente");
                paciente = new Paciente();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                paciente.setNombre(jsonObject.getString("Nombres") + " " + jsonObject.getString("Apellidos"));
                paciente.setSexo(jsonObject.getString("Sexo"));
                paciente.setEmail(jsonObject.getString("correo"));
                paciente.setIdPaciente(jsonObject.getString("IdPacientes"));
                paciente.setFoto(R.drawable.medico2);

                pacientes.add(paciente);
            }
            dialog.dismiss();
            RecylclerViewAdapterPac adapter = new RecylclerViewAdapterPac(pacientes);
            recyclerViewPacientes.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            dialog.dismiss();
            Toast.makeText(getContext(), "No se ha podido establecer conexion con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "Ha ocuurido un error al consultar los pacientes", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    public void buscarPaciente(String frase){
        pacientesCopia.clear();
        for (Paciente paciente: pacientes){
            String nombre = paciente.getNombre();
            nombre = nombre.toLowerCase();

            if(nombre.contains(frase)){
                pacientesCopia.add(paciente);
            }
        }

        RecylclerViewAdapterPac adapter = new RecylclerViewAdapterPac(pacientesCopia);
        recyclerViewPacientes.setAdapter(adapter);
    }
}

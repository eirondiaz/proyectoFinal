package com.example.FragmentsPaciente;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        JSONArray json = response.optJSONArray("medico");

        try {
            for (int i = 0; i < json.length(); i++){
                medico = new Medico();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                medico.setNombre("Dr. " + jsonObject.getString("Nombres") + " " + jsonObject.getString("Apellidos"));
                medico.setEspecialidad(jsonObject.getString("Especialidad"));
                medico.setHospital("Hospital " + i);
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
        Toast.makeText(getContext(), "No existe medicos registrados", Toast.LENGTH_SHORT).show();
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
}

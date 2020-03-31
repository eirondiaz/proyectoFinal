package com.example.SeccionMedico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.SeccionCitaMedica.RecylclerViewAdapterCitaAcept;
import com.example.SeccionCitaMedica.RecylclerViewAdapterCitaAceptada;
import com.example.Usuarios.Cita;
import com.example.proyectofinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CitasAceptadas extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    private RecyclerView recyclerViewCitasAceptada;
    private ArrayList<Cita> citas;
    private ProgressDialog dialog;
    private ArrayList<Cita> citasCopia;
    private String idMedico, hospital;
    private RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_aceptadas);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        idMedico = preferences.getString("idmedico", "");
        hospital = preferences.getString("hospital", "");

        recyclerViewCitasAceptada = findViewById(R.id.RecylerListCitas);
        recyclerViewCitasAceptada.setLayoutManager(new LinearLayoutManager(this));

        request = Volley.newRequestQueue(this);

        dialog = new ProgressDialog(this);

        CargarWebService();

        citas = new ArrayList<>();
        citasCopia = new ArrayList<Cita>();
    }

    public void CargarWebService(){

        //dialog = new ProgressDialog(getContext());
        dialog.setMessage("Consultando citas...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONConsultarCitasAceptByIdMedico.php?id=" + idMedico;

        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        Cita cita;

        JSONArray json = response.optJSONArray("cita");

        try {
            for (int i = 0; i < json.length(); i++){
                cita = new Cita();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                cita.setNombrePac(jsonObject.getString("Nombres") + " " + jsonObject.getString("Apellidos"));
                cita.setStatus(jsonObject.getString("Status"));
                cita.setHospitalMed(hospital);
                cita.setFecha(jsonObject.getString("Fecha") + ", " + jsonObject.getString("Hora"));
                cita.setIdCita(jsonObject.getString("IdCita"));
                cita.setFoto(R.drawable.medico2);

                citas.add(cita);
            }
            dialog.dismiss();
            RecylclerViewAdapterCitaAceptada adapter = new RecylclerViewAdapterCitaAceptada(citas);
            recyclerViewCitasAceptada.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            dialog.dismiss();
            Toast.makeText(this, "No se ha podido establecer conexion con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "No tienes citas aceptadas", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }
}

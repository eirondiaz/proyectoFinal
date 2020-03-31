package com.example.SeccionMedico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PerfilCitaAceptada extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    private String idCita;
    private TextView tvPaciente, tvTelefono, tvFecha, tvHora, tvObservacion, tvStatus;
    private ProgressDialog dialog;
    private Button btnCancelar;
    private String urlCancelar = "https://proyectofinalprog2.000webhostapp.com/wsJSONCancelarCita.php";

    private RequestQueue request;
    private JSONObject jsonObject;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cita_aceptada);

        try{
            Bundle b = getIntent().getExtras();
            idCita = b.getString("id");
        }catch (Exception e){ }

        tvPaciente = findViewById(R.id.tvPaciente);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvFecha = findViewById(R.id.tvFecha);
        tvHora = findViewById(R.id.tvHora);
        tvStatus = findViewById(R.id.tvStatus);
        tvObservacion = findViewById(R.id.tvObervacion);

        request = Volley.newRequestQueue(this);

        CargarWebService();

        btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PerfilCitaAceptada.this);
                dialog.setTitle("Advertencia")
                        .setMessage("Deseas cancelar esta cita?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CancelarCita(urlCancelar);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                dialog.show();
            }
        });
    }

    private void CargarWebService(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando Cita...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONCargarCitaMed.php?id=" + idCita;
        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("cita");

        try {
            jsonObject = json.getJSONObject(0);

            tvFecha.setText(jsonObject.getString("Fecha"));
            tvPaciente.setText(jsonObject.getString("Nombres") + " " + jsonObject.getString("Apellidos"));
            tvTelefono.setText(jsonObject.getString("Telefono"));
            tvStatus.setText(jsonObject.getString("Status"));
            tvHora.setText(jsonObject.getString("Hora"));
            tvObservacion.setText(jsonObject.getString("Observaciones"));
            dialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
            dialog.dismiss();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    private void CancelarCita(String url){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Cancelando cita...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PerfilCitaAceptada.this, "Cita cancelada correctamente", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                startActivity(new Intent(PerfilCitaAceptada.this, DashboardMedico.class));
                finish();
                /*if(!response.isEmpty()){
                    Toast.makeText(PerfilCitas.this, "Cita cancelada correctamente", Toast.LENGTH_SHORT).show();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PerfilCitaAceptada.this, "Ha ocurrido un error al cancelar la cita", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", idCita);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class PerfilCitas extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    private String id;
    private TextView tvMedico, tvHospital, tvFecha, tvHora, tvObservacion;
    private ProgressDialog dialog;
    private Button btnCancelar;
    private String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONCancelarCita.php";

    private RequestQueue request;
    private JSONObject jsonObject;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_citas);

        try{
            Bundle b = getIntent().getExtras();
            id = b.getString("id");
        }catch (Exception e){ }

        tvMedico = findViewById(R.id.tvMedico);
        tvHospital = findViewById(R.id.tvHospital);
        tvFecha = findViewById(R.id.tvFecha);
        tvHora = findViewById(R.id.tvHora);
        tvObservacion = findViewById(R.id.tvObervacion);

        request = Volley.newRequestQueue(this);

        CargarWebService();

        btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PerfilCitas.this);
                dialog.setTitle("Advertencia")
                        .setMessage("Deseas cancelar esta cita?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CancelarCita(url);
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

        String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONCargarCita.php?id=" + id;
        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("cita");

        try {
            jsonObject = json.getJSONObject(0);

            tvFecha.setText(jsonObject.getString("Fecha"));
            tvMedico.setText(jsonObject.getString("Nombres") + " " + jsonObject.getString("Apellidos"));
            tvHospital.setText(jsonObject.getString("Hospital"));
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PerfilCitas.this, "Cita cancelada correctamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PerfilCitas.this, MainActivity.class));
                finish();
                /*if(!response.isEmpty()){
                    Toast.makeText(PerfilCitas.this, "Cita cancelada correctamente", Toast.LENGTH_SHORT).show();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PerfilCitas.this, "Ha ocurrido un error al cancelar la cita", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", id);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

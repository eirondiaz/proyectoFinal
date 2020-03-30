package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PerfilMedico extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener {

    private String id;
    private TextView tvEspecialidad, tvHospital, tvTelefono, tvCorreo, tvNombre;
    private Button btnAgendarCita;

   private LinearLayout linearLayout;
    private RequestQueue requestQueue;
    private  JSONObject jsonObject;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_medico);

        try{
            Bundle b = getIntent().getExtras();
            id = b.getString("id");
        }catch (Exception e){

        }

        requestQueue = Volley.newRequestQueue(getApplication());

        tvEspecialidad = findViewById(R.id.tvEspecialidad);
        tvHospital = findViewById(R.id.tvHospital);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvNombre = findViewById(R.id.tvMainNombre);
        linearLayout = findViewById(R.id.Lemail);
        btnAgendarCita = findViewById(R.id.btnAgendar);
        btnAgendarCita.setOnClickListener(PerfilMedico.this);
        dialog = new ProgressDialog(this);

        CargarWebService();

linearLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       mardarCorreo();

    }
});
    }

   private void mardarCorreo() {
       String recojer = tvCorreo.getText().toString();
        Intent email= new Intent(Intent.ACTION_SEND);
        email.setData(Uri.parse("mailto:"));
       email.setType("message/rfc822");
       email.putExtra(Intent.EXTRA_EMAIL,new String[]{recojer});



        try{
            startActivity(Intent.createChooser(email,"Send Email"));

        }catch (Exception e){

           Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public void CargarWebService(){
        dialog.setMessage("Cargando perfil...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONCargarPerfilMedico.php?id=" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(request);
    }



    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("medico");

        try {
            jsonObject = json.getJSONObject(0);

            tvEspecialidad.setText(jsonObject.getString("Especialidad"));
            tvHospital.setText("Clinica 1");
            tvTelefono.setText(jsonObject.getString("Telefono"));
            tvCorreo.setText(jsonObject.getString("correo"));
            tvNombre.setText(jsonObject.getString("Nombres") + " " + jsonObject.getString("Apellidos"));
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAgendar){
            Intent i = new Intent(PerfilMedico.this, AgendarCita.class);
            startActivity(i);
        }
    }

}




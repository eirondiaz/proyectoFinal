package com.example.SeccionPaciente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.example.AgendarCita;
import com.example.proyectofinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PerfilMedico extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    private String id, medico, especialidad, hospital;
    private TextView tvEspecialidad, tvHospital, tvTelefono, tvCorreo, tvNombre;
    private LinearLayout layoutLlamar, linearLayout;
    private Button btnAgendar;

    private RequestQueue requestQueue;
    private JSONObject jsonObject;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_medico);

        try{
            Bundle b = getIntent().getExtras();
            id = b.getString("id");
        }catch (Exception e){ }

        requestQueue = Volley.newRequestQueue(getApplication());

        tvEspecialidad = findViewById(R.id.tvEspecialidad);
        tvHospital = findViewById(R.id.tvHospital);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvNombre = findViewById(R.id.tvMainNombre);
        linearLayout = findViewById(R.id.Lemail);

        dialog = new ProgressDialog(this);

        CargarWebService();

        btnAgendar = findViewById(R.id.btnAgendar);
        btnAgendar.setOnClickListener(this);

        layoutLlamar = findViewById(R.id.layoutLlamar);
        layoutLlamar.setOnClickListener(this);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mardarCorreo();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.layoutLlamar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Avertencia")
                        .setMessage("Estás seguro que desea llamar a este medico?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                llamar();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.btnAgendar:
                Intent i = new Intent(PerfilMedico.this, AgendarCita.class);
                Bundle b = new Bundle();
                b.putString("id", id);
                b.putString("medico", medico);
                b.putString("especialidad", especialidad);
                b.putString("hospital", hospital);
                i.putExtras(b);
                startActivity(i);
                break;
        }
    }

    public void llamar() {
        String telefono = "tel:"+tvTelefono.getText();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }else{
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(telefono)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                llamar();
            }else{
                Toast.makeText(getApplicationContext(), "Permiso denegado", Toast.LENGTH_LONG).show();
            }
        }
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
            especialidad = jsonObject.getString("Especialidad");
            tvHospital.setText(jsonObject.getString("Hospital"));
            hospital = jsonObject.getString("Hospital");
            tvTelefono.setText(jsonObject.getString("Telefono"));
            tvCorreo.setText(jsonObject.getString("correo"));
            tvNombre.setText(jsonObject.getString("Nombres") + " " + jsonObject.getString("Apellidos"));
            medico = jsonObject.getString("Nombres");
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
}

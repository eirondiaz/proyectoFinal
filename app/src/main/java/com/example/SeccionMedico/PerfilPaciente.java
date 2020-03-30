package com.example.SeccionMedico;

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

public class PerfilPaciente extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    private String id;
    private TextView tvFecha, tvTelefono, tvCorreo, tvNombre;
    private LinearLayout lyCorreo, lyLlamar;
    private RequestQueue requestQueue;
    private JSONObject jsonObject;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_paciente);

        try{
            Bundle b = getIntent().getExtras();
            id = b.getString("id");
        }catch (Exception e){

        }

        requestQueue = Volley.newRequestQueue(getApplication());

        tvFecha = findViewById(R.id.tvFecha);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvNombre = findViewById(R.id.tvMainNombre);
        lyCorreo = findViewById(R.id.Lemail);

        dialog = new ProgressDialog(this);

        CargarWebService();

        lyLlamar = findViewById(R.id.layoutLlamar);
        lyLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PerfilPaciente.this);
                builder.setTitle("Avertencia")
                        .setMessage("Estás seguro que desea llamar a este paciente?")
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
            }
        });

        lyCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mardarCorreo();
            }
        });
    }

    private void CargarWebService(){
        dialog.setMessage("Cargando perfil...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONCargarPerfilPaciente.php?id=" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(request);
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("paciente");

        try {
            jsonObject = json.getJSONObject(0);

            tvFecha.setText(jsonObject.getString("Fecha_Nacimiento"));
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
}

package com.example.SeccionMedico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Usuarios.Medico;
import com.example.PreLogin;
import com.example.Usuarios.UsuarioFactory;
import com.example.proyectofinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MedicoLogin extends AppCompatActivity implements View.OnClickListener , Response.Listener<JSONObject>, Response.ErrorListener {

    private ImageView imgBack;
    private Button btnLogIn, btnRegistro;
    private EditText edEmail, edContraseña;
    private  Medico medico ;
    private  JSONObject jsonObject;
    private RequestQueue requestQueue;
    private String email ;
    private String password, tipo;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_login);

         requestQueue = Volley.newRequestQueue(getApplication()) ;

        edEmail = findViewById(R.id.edEmail);
        edContraseña = findViewById(R.id.edContraseña);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);

        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);

        btnRegistro = findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(this);


        UsuarioFactory usuarioFactory = new UsuarioFactory();
         medico = (Medico) usuarioFactory.getUsuario("medico");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgBack:
                startActivity(new Intent(MedicoLogin.this, PreLogin.class));
                break;
            case R.id.btnLogIn:
                cargarServer();
                break;
            case R.id.btnRegistro:
                startActivity(new Intent(MedicoLogin.this, RegistroMedico.class));
                break;
        }
    }


    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("usuario");

        try {
              jsonObject = json.getJSONObject(0);
              medico.setIdUsuario(jsonObject .optInt("idUsuario"));
              medico.setEmail(jsonObject.optString("correo"));
              medico.setContraseña(jsonObject.optString("pass"));
              medico.setNombre(jsonObject.optString("Nombres"));
              medico.setApellido(jsonObject.optString("Apellidos"));
              medico.setTelefono(jsonObject.optString("Telefono"));
              medico.setSexo(jsonObject.optString("Sexo"));
              medico.setIdMedico(jsonObject.getString("IdMedico"));
              tipo = jsonObject.getString("tipo");
              medico.setExequatur(jsonObject.getString("Exequatur"));
              medico.setEspecialidad(jsonObject.getString("Especialidad"));
              medico.setArs(jsonObject.getString("seguro"));
              medico.setHospital(jsonObject.getString("Hospital"));

          //  Toast.makeText(this, medico.getEmail() + " " + medico.getContraseña(), Toast.LENGTH_SHORT ).show();
             progressDialog.hide();

            if(medico.getEmail().equals(email) && medico.getContraseña().equals(password)){
                SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("email", email);
                editor.putString("nombre", medico.getNombre());
                editor.putString("apellido", medico.getApellido());
                editor.putString("telefono", medico.getTelefono());
                editor.putString("sexo", medico.getSexo());
                editor.putString("tipo", tipo);
                editor.putString("especialidad", medico.getEspecialidad());
                editor.putString("hospital", medico.getHospital());
                editor.putString("pass", password);
                editor.putString("idmedico", medico.getIdMedico());
                editor.commit();
                medicoHome();
              }  else {
                  AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                      dialog.setTitle("Aviso!");
                      dialog.setMessage("Usuario y contraseña incorrecta");
                      dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                          }
                      }) ;
                      dialog.create().show();
              }

        }
        catch (JSONException  j){
            j.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
      progressDialog.hide();
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Aviso!");
        alerta.setMessage("Acaba de ocurrir un eror");
        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }) ;

        alerta.create().show();
    }

    public void cargarServer() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciando seccion...");
        progressDialog.show();

          email = edEmail.getText().toString();
          password = edContraseña.getText().toString();
          String url ="https://proyectofinalprog2.000webhostapp.com/loginUsuario.php?correo=%22"+email+"%22&&pass=%22"+password+"%22";
          JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url , null, this , this );
          requestQueue.add(request);
    }

    public void medicoHome(){
        Intent home = new Intent(MedicoLogin.this, DashboardMedico.class);
        home.putExtra("idUsuario", medico.getIdUsuario());
        startActivity(home);
    }
}

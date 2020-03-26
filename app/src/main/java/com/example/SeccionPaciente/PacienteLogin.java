package com.example.SeccionPaciente;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.MainActivity;
import com.example.Usuarios.Paciente;
import com.example.PreLogin;
import com.example.Usuarios.Usuario;
import com.example.Usuarios.UsuarioFactory;
import com.example.proyectofinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PacienteLogin extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

    private ImageView imgBack;
    private Button btnLogIn, btnRegistro;
    private EditText edEmail, edContraseña;
    private ProgressDialog dialog;
    private  JSONObject jsonObject, jsonObject2;
    private String email, password;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_login);

        dialog = new ProgressDialog(this);

        requestQueue = Volley.newRequestQueue(getApplication());

        edEmail = findViewById(R.id.edEmail);
        edContraseña = findViewById(R.id.edContraseña);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);

        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);

        btnRegistro = findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgBack:
                startActivity(new Intent(PacienteLogin.this, PreLogin.class));
                break;
            case R.id.btnLogIn:
                cargarServer();
                break;
            case R.id.btnRegistro:
                startActivity(new Intent(PacienteLogin.this, RegistroPaciente.class));
                break;
        }
    }

    public void cargarServer() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Iniciando Sesion...");
        dialog.show();

        email = edEmail.getText().toString();
        password = edContraseña.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
        else {
            String url = "https://proyectofinalprog2.000webhostapp.com/datos.php?correo=%22" + email + "%22&&pass=%22" + password + "%22";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            requestQueue.add(request);
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("usuario");

        UsuarioFactory factory = new UsuarioFactory();
        Usuario pac = factory.getUsuario("paciente");

        try {
            jsonObject = json.getJSONObject(0);
            pac.setIdUsuario(jsonObject .optInt("idUsuario"));
            pac.setEmail(jsonObject.optString("correo"));
            pac.setContraseña(jsonObject.optString("pass"));
            pac.setNombre(jsonObject.optString("Nombres"));
            pac.setApellido(jsonObject.optString("Apellidos"));
            pac.setTelefono(jsonObject.optString("Telefono"));
            pac.setSexo(jsonObject.optString("Sexo"));
            String tipo = jsonObject.getString("tipo");

            dialog.hide();

            if(pac.getEmail().equals(email) && pac.getContraseña().equals(password)){
                SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("email", email);
                editor.putString("nombre", pac.getNombre());
                editor.putString("apellido", pac.getApellido());
                editor.putString("telefono", pac.getTelefono());
                editor.putString("sexo", pac.getSexo());
                editor.putString("tipo", tipo);
                editor.commit();
                startActivity(new Intent(PacienteLogin.this, MainActivity.class));
                finish();
            }
            else{
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Aviso!");
                dialog.setMessage("Usuario y contraseña incorrecta");
                dialog.setPositiveButton("dialog", new DialogInterface.OnClickListener() {
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
        dialog.hide();
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

    /*public void LogIn(String url) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Iniciando Sesión");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                JSONArray jsonArray = null;
                JSONObject object = null;
                
                if (!response.isEmpty()){
                    Toast.makeText(PacienteLogin.this, "Correcto", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else{
                    Toast.makeText(PacienteLogin.this, "Invalido", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

                try {
                    //jsonObject = new JSONObject(response);
                    //jsonArray = jsonObject.getJSONArray("usuario");

                    if (!response.isEmpty()) {

                        object = jsonArray.getJSONObject(0);

                        String correo = object.getString("correo").trim();
                        String pass = object.getString("pass").trim();
                        dialog.dismiss();

                        Toast.makeText(PacienteLogin.this, "Correcto", Toast.LENGTH_SHORT).show();

                        //startActivity(new Intent(PacienteLogin.this, MainActivity.class));
                        //dialog.dismiss();
                    } else {
                        Toast.makeText(PacienteLogin.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    Toast.makeText(PacienteLogin.this, "Catch", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PacienteLogin.this, error.toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", edEmail.getText().toString());
                parametros.put("pass", edContraseña.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/
}

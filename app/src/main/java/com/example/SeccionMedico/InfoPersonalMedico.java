package com.example.SeccionMedico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.PreLogin;
import com.example.SeccionPaciente.InformacionPersonal;
import com.example.Usuarios.Medico;
import com.example.proyectofinal.R;

import org.json.JSONObject;

public class InfoPersonalMedico extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    private EditText edNombre, edApellido, edTelefono, edCorreo;
    private TextView tvMainNombre;
    private Button btnLogOut, btnEditar;
    private String idMedico, nombre, apellido, telefono, correo;
    boolean editMode = true;

    private RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_personal_medico);

        edNombre = findViewById(R.id.edNombre);
        edApellido = findViewById(R.id.edApellido);
        edTelefono = findViewById(R.id.edTelefono);
        edCorreo = findViewById(R.id.edCorreo);
        tvMainNombre = findViewById(R.id.tvMainNombre);

        getPreferences();

        btnEditar = findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(this);

        request = Volley.newRequestQueue(this);

        btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);
    }

    private void getPreferences(){
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        tvMainNombre.setText(preferences.getString("nombre", "") + " " + preferences.getString("apellido", ""));
        edNombre.setText(preferences.getString("nombre", ""));
        edApellido.setText(preferences.getString("apellido", ""));
        edTelefono.setText(preferences.getString("telefono", ""));
        edCorreo.setText(preferences.getString("email", ""));
        idMedico = preferences.getString("idmedico", "");
    }

    private void CargarWebService(){

        nombre = edNombre.getText().toString();
        apellido = edApellido.getText().toString();
        telefono = edTelefono.getText().toString();
        correo = edCorreo.getText().toString();

        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
        else{

            String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONEditarMedico.php?nombre=" + nombre + "&apellido=" + apellido + "&telefono=" + telefono + "&correo=" + correo + "&id=" + idMedico;
            url = url.replace(" ", "%20");

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            request.add(jsonObjectRequest);
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
        tvMainNombre.setText(nombre + " " + apellido);
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombre", nombre);
        editor.putString("apellido", apellido);
        editor.putString("telefono", telefono);
        editor.putString("email", correo);
        editor.commit();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnLogOut:
                SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("tipo", "");
                editor.commit();
                startActivity(new Intent(InfoPersonalMedico.this, PreLogin.class));
                finish();
                break;
            case R.id.btnEditar:
                editProcess();
                break;
        }
    }

    public void editProcess(){
        if (editMode){
            btnEditar.setText("GUARDAR");
            edNombre.setEnabled(true);
            edApellido.setEnabled(true);
            edTelefono.setEnabled(true);
            edCorreo.setEnabled(true);

            edNombre.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            edApellido.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            edTelefono.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            edCorreo.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            edNombre.requestFocus();
            edNombre.setSelection(edNombre.getText().length());

            btnEditar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_save_black_24dp, 0, 0, 0);

            editMode = false;
        }
        else{
            btnEditar.setText("editar");

            edNombre.setEnabled(false);
            edApellido.setEnabled(false);
            edTelefono.setEnabled(false);
            edCorreo.setEnabled(false);

            edNombre.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            edApellido.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            edTelefono.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            edCorreo.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

            btnEditar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edit_black_24dp, 0, 0, 0);

            String nombre = edNombre.getText().toString();
            String apellido = edApellido.getText().toString();
            String telefono = edTelefono.getText().toString();

            if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()){
                Toast.makeText(this, "No puedes dejar campos vacios", Toast.LENGTH_SHORT).show();
            }
            else{
                CargarWebService();
                editMode = true;
            }
        }
    }
}

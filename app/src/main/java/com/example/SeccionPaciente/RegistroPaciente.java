package com.example.SeccionPaciente;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Usuarios.Paciente;
import com.example.proyectofinal.R;

import org.json.JSONObject;

import java.util.Calendar;

public class RegistroPaciente extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    private Spinner spSexo;
    private Button btnFecha, btnRegistrar;
    private EditText edNombre, edApellido, edTelefono, edEmail, edContraseña, edRepContra;
    private String[] sexoList = new String[]{"Masculino", "Femenino"};
    private String nombre, apellido, telefono, email, contraseña, repcontraseña, fecha, sexo;

    private RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        edNombre = findViewById(R.id.edNombre);
        edApellido = findViewById(R.id.edApellido);
        edTelefono = findViewById(R.id.edTelefono);
        edEmail = findViewById(R.id.edEmail);
        edContraseña = findViewById(R.id.edContraseña);
        edRepContra = findViewById(R.id.edRepContra);

        request = Volley.newRequestQueue(this);

        spSexo = findViewById(R.id.spSexo);
        spSexo.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sexoList));

        btnFecha = findViewById(R.id.fecha);
        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);
    }

    public void CargarWebService(){

        dialog = new ProgressDialog(this);
        dialog.setMessage("Registrando...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String correo = edEmail.getText().toString();
        String pass = edContraseña.getText().toString();
        String reppass = edRepContra.getText().toString();
        String nombre = edNombre.getText().toString();
        String apellido = edApellido.getText().toString();
        String telefono = edTelefono.getText().toString();
        String sexo = spSexo.getSelectedItem().toString();
        String fecha = btnFecha.getText().toString();

        if (correo.isEmpty() || pass.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || fecha.equalsIgnoreCase("fecha de nacimiento")) {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
        else{

            if(pass.length() < 6){
                Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            else{
                if(!pass.equals(reppass)){
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else{
                    String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONRegistroPaciente2.php?correo=" + correo + "&pass=" + pass + "&Nombres=" + nombre + "&Apellidos=" + apellido + "&Telefono=" + telefono + "&Sexo=" + sexo + "&Fecha_Nacimiento=" + fecha;

                    url = url.replace(" ", "%20");

                    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                    request.add(jsonObjectRequest);
                }
            }
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        startActivity(new Intent(RegistroPaciente.this, PacienteLogin.class));
        finish();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
        dialog.dismiss();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnRegistrar:
                CargarWebService();
                break;
        }
    }

    private void getDate()
    {
        final Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);

        DatePickerDialog datepicker = new DatePickerDialog(RegistroPaciente.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btnFecha.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        } , 2000, 12, 12);
        //datepicker.getDatePicker().setMinDate(System.currentTimeMillis());
        //c.set(1995, 01, 01);
        //datepicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datepicker.show();
    }
}

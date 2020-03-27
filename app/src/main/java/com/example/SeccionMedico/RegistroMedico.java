package com.example.SeccionMedico;

import androidx.appcompat.app.AppCompatActivity;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.Usuarios.Medico;
import com.example.proyectofinal.R;

import org.json.JSONObject;

import java.util.Calendar;

public class RegistroMedico extends AppCompatActivity implements View.OnClickListener , Response.Listener<JSONObject> , Response.ErrorListener {

    private Spinner spSexo, spEspecialidad, spARS, spClinica;
    private Button btnRegistrar, btnFecha;
    private EditText edNombre, edApellido, edTelefono, edEmail, edContraseña, edRepContra, edExequatur;
    private String nombre, apellido , email, contraseña, repcontraseña, fecha, sexo, especialidad, ars, hospital;
    private  int exequatur,  telefono ;
    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private  JsonObjectRequest jsonObjectRequest;

    private String[] sexoList = new String[]{"Masculino", "Femenino"};

    private String[] especialidadList = new String[]
            {"Especialidad", "Anestesiología", "Alergología", "Cardiología", "Gastroenterología", "Geriatría", "Endocrinología"};

    private String[] ARSList = new String[]
            {"No tengo seguro", "ARS Universal", "ARS Palic Salud"};

    private String[] clinicaList = new String[]
            {"Hospital", "Hospital Local Boca Chica", "Dentistica Enmanuel"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medico);

        edNombre = findViewById(R.id.edNombre);
        edApellido = findViewById(R.id.edApellido);
        edTelefono = findViewById(R.id.edTelefono);
        edEmail = findViewById(R.id.edEmail);
        edExequatur = findViewById(R.id.edExequtur);
        edContraseña = findViewById(R.id.edContraseña);
        edRepContra = findViewById(R.id.edRepContra);

        btnFecha = findViewById(R.id.fecha);
        btnFecha.setOnClickListener(this);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);

        spSexo = findViewById(R.id.spSexo);
        spSexo.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sexoList));

        spEspecialidad = findViewById(R.id.spEspecialidad);
        spEspecialidad.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, especialidadList));

        spARS = findViewById(R.id.spARS);
        spARS.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ARSList));

        spClinica = findViewById(R.id.spClinica);
        spClinica.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, clinicaList));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnRegistrar:

                break;
            case R.id.fecha:
                getDate();
                break;
        }
    }

    private void getDate()
    {
        final Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);

        DatePickerDialog datepicker = new DatePickerDialog(RegistroMedico.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btnFecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        } , dia, mes, ano);
        datepicker.getDatePicker().setMinDate(System.currentTimeMillis());
        datepicker.show();
    }


    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }




    public void cargarServer() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando usuario...");
        progressDialog.show();
    //    edNombre, edApellido, edTelefono, edEmail, edContraseña, edRepContra, edExequatur;
     // nombre, apellido , email, contraseña, repcontraseña, fecha, sexo, especialidad, ars, hospital;
     //   exequatur,  telefono ;

         nombre = edNombre.getText().toString();
         apellido = edApellido.getText().toString();
         telefono =Integer.getInteger(edTelefono.getText().toString());
         email = edEmail.getText().toString();
         contraseña = edContraseña.getText().toString();
         repcontraseña = edRepContra.getText().toString();
         fecha = btnFecha.getText().toString();
         sexo = spSexo.getSelectedItem().toString();
         exequatur = Integer.getInteger(edExequatur.getText().toString());
         especialidad = especialidadList[spARS.getSelectedItemPosition()];
          ars = ARSList [spARS.getSelectedItemPosition()];
          hospital = clinicaList[spClinica.getSelectedItemPosition()];


        String url = "\n" +
                "https://proyectofinalprog2.000webhostapp.com/registroMedico.php?nombre=%22"+nombre+"%22&apellido=%22"+apellido+"%22" +
                "&telefono="+telefono+"&correo=%22"+email+"%22&pass=%22"+contraseña+"%22&sexo=%22"+sexo+"%22&fecha=%27"+fecha+"%27" +
                "&exequatur="+exequatur+"&especialida=%22"+especialidad+"%22&hospital=%22d"+hospital+"%22&seguro=%22"+ars+"%22";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url , null, this , this );
        requestQueue.add(request);
    }

}

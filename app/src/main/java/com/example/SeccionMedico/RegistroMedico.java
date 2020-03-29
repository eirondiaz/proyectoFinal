package com.example.SeccionMedico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.android.volley.toolbox.Volley;
import com.example.Usuarios.Medico;
import com.example.proyectofinal.R;

import org.json.JSONObject;

import java.util.Calendar;

public class RegistroMedico extends AppCompatActivity implements View.OnClickListener , Response.Listener<JSONObject> , Response.ErrorListener {

    private Spinner spSexo, spEspecialidad, spARS, spClinica;
    private Button btnRegistrar, btnFecha;
    private EditText edNombre, edApellido, edTelefono, edEmail, edContraseña, edRepContra, edExequatur;
    private String nombre, apellido , email, contraseña, repcontraseña, fecha, sexo, especialidad, ars, hospital, exequatur,  telefono ;
    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private  JsonObjectRequest request;

    private String[] sexoList = new String[]{"Masculino", "Femenino"};

    private String[] especialidadList = new String[]
            {"ANATOMÍA PATOLÓGICA","ANESTESIOLOGÍA", "ANGIOLOGÍA GENERAL y HEMODINAMIA" , "CARDIOLOGÍA" , "CARDIÓLOGO INFANTIL" , "CIRUGÍA GENERAL" ,
                    "CIRUGÍA CARDIOVASCULAR" , "CIRUGÍA DE CABEZA Y CUELLO" , "CIRUGÍA DE TÓRAX (CIRUGÍA TORÁCICA)" , "CIRUGÍA INFANTIL (CIRUGÍA PEDIÁTRICA)" ,
                    "CIRUGÍA PLÁSTICA Y REPARADORA" , "CIRUGÍA VASCULAR PERIFÉRICA" , "CLÍNICA MÉDICA", "COLOPROCTOLOGÍA", "DERMATOLOGÍA", "DIAGNOSTICO POR IMÁGENES",
                    "ENDOCRINOLOGÍA", "ENDOCRINÓLOGO INFANTIL", "FARMACOLOGÍA CLÍNICA", "FISIATRÍA (MEDICINA FÍSICA Y REHABILITACIÓN)", "GASTROENTEROLOGÍA",
                    "GASTROENTERÓLOGO INFANTIL", "GASTROENTERÓLOGO INFANTIL", "GERIATRÍA", "GINECOLOGÍA", "HEMATOLOGÍA", "HEMATÓLOGO INFANTIL", "HEMOTERAPIA E INMUNOHEMATOLOGÍA", "INFECTOLOGÍA",
                    "INFECTÓLOGO INFANTIL", "MEDICINA DEL DEPORTE", "MEDICINA GENERAL y/o MEDICINA DE FAMILIA", "MEDICINA LEGAL", "MEDICINA NUCLEAR", "MEDICINA DEL TRABAJO",
                    "NEFROLOGÍA", "NEFRÓLOGO INFANTIL", "NEONATOLOGÍA", "NEUMONOLOGÍA", "NEUMONÓLOGO INFANTIL", "NEUROCIRUGÍA", "NEUROLOGÍA", "NEURÓLOGO INFANTIL", "NUTRICIÓN", "OBSTETRICIA",
                    "OFTALMOLOGÍA", "ONCOLOGÍA", "ONCÓLOGO INFANTIL", "ORTOPEDIA Y TRAUMATOLOGÍA", "OTORRINOLARINGOLOGÍA", "PEDIATRÍA", "PSIQUIATRÍA", "PSIQUIATRÍA INFANTO JUVENIL",
                    "RADIOTERAPIA O TERAPIA RADIANTE", "REUMATOLOGÍA", "REUMATÓLOGO INFANTIL", "TERAPIA INTENSIVA", "TERAPISTA INTENSIVO INFANTIL", "TOCOGINECOLOGÍA", "TOXICOLOGÍA", "UROLOGÍA"};

    private String[] ARSList = new String[]
            {"No tengo seguro", "ARS Universal", "ARS Palic Salud"};

    private String[] clinicaList = new String[]
            {"Hospital", "Hospital Local Boca Chica", "Dentistica Enmanuel"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medico);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

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
        spSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexo = sexoList[position] ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spEspecialidad = findViewById(R.id.spEspecialidad);
        spEspecialidad.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, especialidadList));
        spEspecialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                especialidad = especialidadList[position] ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spARS = findViewById(R.id.spARS);
        spARS.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ARSList));
        spARS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ars = ARSList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spClinica = findViewById(R.id.spClinica);
        spClinica.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, clinicaList));
        spClinica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hospital = clinicaList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnRegistrar:
                cargarServer() ;
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
               if(dayOfMonth < 10 && month < 10 ){
                   btnFecha.setText(year + "-0" +  (month + 1) +  "-0"+ dayOfMonth);
               }

              else if( month < 10 ){
                    btnFecha.setText(year + "-0" +  (month + 1) + "-" + dayOfMonth);
                }

              else if (dayOfMonth < 10  ){
                   btnFecha.setText(year + "-" +  (month + 1) + "-0" + dayOfMonth);
               }

               else{
                   btnFecha.setText(year + "-"+ dayOfMonth +"-" + (month + 1) );
               }
            }
        } , dia, mes, ano);
        datepicker.getDatePicker().setMinDate(System.currentTimeMillis());
        datepicker.show();
    }


    @Override
    public void onResponse(JSONObject response) {
                 progressDialog.hide();
                 Toast.makeText(getApplicationContext(), "Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(RegistroMedico.this, MedicoLogin.class));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();

        AlertDialog.Builder alerta = new AlertDialog.Builder(this) ;
        alerta.setTitle("Aviso!");
        alerta.setMessage("Acaba de ocurrir un error");
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
        progressDialog.setMessage("Registrando usuario...");

        nombre = edNombre.getText().toString();
        apellido = edApellido.getText().toString();
        telefono = edTelefono.getText().toString();
        email = edEmail.getText().toString();
        contraseña = edContraseña.getText().toString();
        repcontraseña = edRepContra.getText().toString();
        fecha = btnFecha.getText().toString();
        exequatur = edExequatur.getText().toString();


        //   Toast.makeText(getApplicationContext(), nombre + " " + apellido +  " "   + fecha + " " + sexo, Toast.LENGTH_LONG).show();

        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || email.isEmpty() || contraseña.isEmpty() || fecha.isEmpty() || exequatur.isEmpty() || sexo.isEmpty() || hospital.isEmpty() || ars.isEmpty() || fecha.equalsIgnoreCase("Fecha de nacimiento")) {
            Toast.makeText(getApplicationContext(), "Algunos campos estan vacios", Toast.LENGTH_SHORT).show();

        }


        else {

            if(contraseña.length() < 6){
                Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();

            }


            else if(!contraseña.equals(repcontraseña)){
                Toast.makeText(getApplicationContext(), "La contraseña no considen", Toast.LENGTH_SHORT).show();
            }

            else {
                progressDialog.show();

                String url = "https://proyectofinalprog2.000webhostapp.com/registroMedico.php?nombre=%22"+nombre+"%22&&apellido=%22"+apellido+"%22&&telefono="+telefono+"&&correo=%22"+email+"%22&&pass=%22"+contraseña+"%22&&fecha=%27"+fecha+"%27&&sexo=%22"+sexo+"%22&&exequatur="+exequatur+"&&especialida=%22"+especialidad+"%22&&hospital=%22"+hospital+"%22&&seguro=%22"+ars+"%22";
                url =  url.replace(" ", "%20");
                request = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                requestQueue.add(request);
            }

        }

        }

    }

   //https://proyectofinalprog2.000webhostapp.com/registroMedico.php?nombre=%22gabriel%22&&apellido=%22gil%22&&telefono=809203929301&&correo=%22gab@gmil.com%22&&pass=%22gabriel%22&&area=%22doctor%22&&fecha=%272018-02-1%27&&sexo=%22mascullino%22&&exequatur=32340&&especialida=%22no%20tiene%22&&hospital=%22ninguno%22&&seguro=%22no%22
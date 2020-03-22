package com.example.SeccionMedico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.Medico;
import com.example.Paciente;
import com.example.SeccionPaciente.PacienteLogin;
import com.example.SeccionPaciente.RegistroPaciente;
import com.example.proyectofinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegistroMedico extends AppCompatActivity implements View.OnClickListener{

    private Spinner spSexo, spEspecialidad, spARS, spClinica;
    private Button btnRegistrar, btnFecha;
    private EditText edNombre, edApellido, edTelefono, edEmail, edContraseña, edRepContra, edExequatur;
    private String nombre, apellido, telefono, email, contraseña, repcontraseña, fecha, sexo, exequatur, especialidad, ars, hospital;

    private String[] sexoList = new String[]{"Masculino", "Femenino"};

    private String[] especialidadList = new String[]
            {"Especialidad", "Anestesiología", "Alergología", "Cardiología", "Gastroenterología", "Geriatría", "Endocrinología"};

    private String[] ARSList = new String[]
            {"No tengo seguro", "ARS Universal", "ARS Palic Salud"};

    private String[] clinicaList = new String[]
            {"Hospital", "Hospital Local Boca Chica", "Dentistica Enmanuel"};

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medico);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
                nombre = edNombre.getText().toString();
                apellido = edApellido.getText().toString();
                telefono = edTelefono.getText().toString();
                email = edEmail.getText().toString();
                contraseña = edContraseña.getText().toString();
                repcontraseña = edRepContra.getText().toString();
                fecha = btnFecha.getText().toString();
                sexo = spSexo.getSelectedItem().toString();
                exequatur = edExequatur.getText().toString();
                especialidad = spEspecialidad.getSelectedItem().toString();
                ars = spARS.getSelectedItem().toString();
                hospital = spClinica.getSelectedItem().toString();

                if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || email.isEmpty() || contraseña.isEmpty() || fecha.equalsIgnoreCase("FECHA DE NACIMIENTO") || especialidad.equalsIgnoreCase("especialidad") || hospital.equalsIgnoreCase("hospital")){
                    Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (contraseña.length() < 6){
                        Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (contraseña.equals(repcontraseña)){
                            Medico.Registrar(this, email, contraseña, nombre, apellido, telefono, fecha, sexo, exequatur, especialidad, ars, hospital);
                            finish();
                        }
                        else {
                            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
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
}

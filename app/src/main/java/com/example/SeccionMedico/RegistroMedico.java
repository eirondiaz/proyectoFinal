package com.example.SeccionMedico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.Usuarios.Medico;
import com.example.proyectofinal.R;

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
}

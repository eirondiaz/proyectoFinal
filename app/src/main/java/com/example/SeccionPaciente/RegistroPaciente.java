package com.example.SeccionPaciente;

import android.app.DatePickerDialog;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Paciente;
import com.example.Usuario;
import com.example.proyectofinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistroPaciente extends AppCompatActivity implements View.OnClickListener{

    private Spinner spSexo;
    private Button btnFecha, btnRegistrar;
    private EditText edNombre, edApellido, edTelefono, edEmail, edContraseña, edRepContra;
    private String[] sexoList = new String[]{"Masculino", "Femenino"};
    private String nombre, apellido, telefono, email, contraseña, repcontraseña, fecha, sexo;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        edNombre = findViewById(R.id.edNombre);
        edApellido = findViewById(R.id.edApellido);
        edTelefono = findViewById(R.id.edTelefono);
        edEmail = findViewById(R.id.edEmail);
        edContraseña = findViewById(R.id.edContraseña);
        edRepContra = findViewById(R.id.edRepContra);

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

                    if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || email.isEmpty() || contraseña.isEmpty() || fecha.equalsIgnoreCase("FECHA DE NACIMIENTO")){
                        Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (contraseña.length() < 6){
                            Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if (contraseña.equals(repcontraseña)){
                                Paciente.Registrar(this, email, contraseña, nombre, apellido, telefono, fecha, sexo);
                                finish();
                            }
                            else {
                                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
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
                btnFecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        } , dia, mes, ano);
        datepicker.getDatePicker().setMinDate(System.currentTimeMillis());
        datepicker.show();
    }
}

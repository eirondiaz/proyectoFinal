package com.example;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.R;

public class RegistroPaciente extends AppCompatActivity {

    private Spinner spSexo;
    private String[] sexo = new String[]{"Masculino", "Femenino"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        spSexo = findViewById(R.id.spSexo);
        spSexo.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sexo));
    }
}

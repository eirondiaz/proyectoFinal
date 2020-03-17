package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.SeccionMedico.MedicoLogin;
import com.example.SeccionPaciente.PacienteLogin;
import com.example.proyectofinal.R;

public class PreLogin extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout lyPaciente, lyMedico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);

        lyPaciente = findViewById(R.id.lyPaciente);
        lyPaciente.setOnClickListener(this);

        lyMedico = findViewById(R.id.lyMedico);
        lyMedico.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.lyPaciente:
                startActivity(new Intent(PreLogin.this, PacienteLogin.class));
                break;
            case R.id.lyMedico:
                startActivity(new Intent(PreLogin.this, MedicoLogin.class));
                break;
        }
    }
}

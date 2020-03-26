package com.example;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.SeccionMedico.DashboardMedico;
import com.example.SeccionMedico.MedicoLogin;
import com.example.SeccionPaciente.PacienteLogin;
import com.example.proyectofinal.R;

public class PreLogin extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout lyPaciente, lyMedico;
    String tipo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);

        lyPaciente = findViewById(R.id.lyPaciente);
        lyPaciente.setOnClickListener(this);

        lyMedico = findViewById(R.id.lyMedico);
        lyMedico.setOnClickListener(this);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        tipo = preferences.getString("tipo", "");
        if (tipo.equals("0")){
            startActivity(new Intent(PreLogin.this, MainActivity.class));
        }
        else if(tipo.equals("1")){
            startActivity(new Intent(PreLogin.this, DashboardMedico.class));
        }
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

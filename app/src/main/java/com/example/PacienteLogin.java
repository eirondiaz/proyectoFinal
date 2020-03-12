package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.proyectofinal.MainActivity;
import com.example.proyectofinal.PreLogin;
import com.example.proyectofinal.R;

public class PacienteLogin extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgBack;
    private Button btnLogIn, btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_login);

        imgBack = findViewById(R.id.imgBack   );
        imgBack.setOnClickListener(this  );

        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);

        btnRegistro = findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.imgBack:
                startActivity(new Intent(PacienteLogin.this, PreLogin.class));
                break;
            case R.id.btnLogIn:
                startActivity(new Intent(PacienteLogin.this, MainActivity.class));
                break;
            case R.id.btnRegistro:
                startActivity(new Intent(PacienteLogin.this, RegistroPaciente.class));
                break;
        }
    }
}

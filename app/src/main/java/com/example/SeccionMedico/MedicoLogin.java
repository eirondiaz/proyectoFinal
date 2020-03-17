package com.example.SeccionMedico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.MainActivity;
import com.example.PreLogin;
import com.example.SeccionPaciente.PacienteLogin;
import com.example.SeccionPaciente.RegistroPaciente;
import com.example.proyectofinal.R;

public class MedicoLogin extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgBack;
    private Button btnLogIn, btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_login);

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
                startActivity(new Intent(MedicoLogin.this, PreLogin.class));
                break;
            case R.id.btnLogIn:
                startActivity(new Intent(MedicoLogin.this, DashboardMedico.class));
                break;
            case R.id.btnRegistro:
                startActivity(new Intent(MedicoLogin.this, RegistroMedico.class));
                break;
        }
    }
}

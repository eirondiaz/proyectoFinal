package com.example.SeccionPaciente;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.MainActivity;
import com.example.Usuarios.Paciente;
import com.example.PreLogin;
import com.example.proyectofinal.R;

public class PacienteLogin extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgBack;
    private Button btnLogIn, btnRegistro;
    private EditText edEmail, edContraseña;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_login);

        dialog = new ProgressDialog(this);

        edEmail = findViewById(R.id.edEmail);
        edContraseña = findViewById(R.id.edContraseña);

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

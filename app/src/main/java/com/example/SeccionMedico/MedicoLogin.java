package com.example.SeccionMedico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Medico;
import com.example.Paciente;
import com.example.PreLogin;
import com.example.proyectofinal.R;

public class MedicoLogin extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgBack;
    private Button btnLogIn, btnRegistro;
    private EditText edEmail, edContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_login);

        edEmail = findViewById(R.id.edEmail);
        edContraseña = findViewById(R.id.edContraseña);

        imgBack = findViewById(R.id.imgBack   );
        imgBack.setOnClickListener(this);

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
                String email = edEmail.getText().toString();
                String password = edContraseña.getText().toString();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    Medico.IniciarSesion(this, email, password);
                }
                break;
            case R.id.btnRegistro:
                startActivity(new Intent(MedicoLogin.this, RegistroMedico.class));
                break;
        }
    }
}

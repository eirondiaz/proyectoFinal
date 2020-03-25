package com.example.SeccionMedico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.Usuarios.Medico;
import com.example.PreLogin;
import com.example.proyectofinal.R;

import org.json.JSONObject;

public class MedicoLogin extends AppCompatActivity implements View.OnClickListener , Response.Listener<JSONObject>, Response.ErrorListener {

    private ImageView imgBack;
    private Button btnLogIn, btnRegistro;
    private EditText edEmail, edContraseña;
    private  Medico medico ;
    private  JSONObject jsonObject;
    private RequestQueue requestQueue;
    private String email ;
    private String password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_login);

         requestQueue = Volley.newRequestQueue(getApplication()) ;

        edEmail = findViewById(R.id.edEmail);
        edContraseña = findViewById(R.id.edContraseña);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);

        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);

        btnRegistro = findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgBack:
                startActivity(new Intent(MedicoLogin.this, PreLogin.class));
                break;
            case R.id.btnLogIn:
                cargarServer();

                break;
            case R.id.btnRegistro:
                startActivity(new Intent(MedicoLogin.this, RegistroMedico.class));
                break;
        }
    }


    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    public void cargarServer() {


    }

    public void medicoHome(){
        Intent home = new Intent(MedicoLogin.this, DashboardMedico.class);
        startActivity(home);
    }
}

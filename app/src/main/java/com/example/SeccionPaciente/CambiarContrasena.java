package com.example.SeccionPaciente;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.MainActivity;
import com.example.proyectofinal.R;

import org.json.JSONObject;

public class CambiarContrasena extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

    private EditText edPassActual, edPassNew, edPassNewConfirm;
    String correo, passActual, passNew, passNewConfirm, passActualReal;
    Button btnCambiarPass;
    ProgressDialog dialog;
    private RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);

        edPassActual = findViewById(R.id.editText_pass_actual);
        edPassNew = findViewById(R.id.editText_pass_new);
        edPassNewConfirm = findViewById(R.id.editText_pass_confirm);
        btnCambiarPass = findViewById(R.id.btn_cambiar_pass);
        btnCambiarPass.setOnClickListener(this);

        dialog = new ProgressDialog(this);

        request = Volley.newRequestQueue(this);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        correo = preferences.getString("email", "");
        passActualReal = preferences.getString("pass", "");
        
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_cambiar_pass:

                cargarServer();
                break;
        }
    }

    public void cargarServer(){

        passActual = edPassActual.getText().toString();
        passNew = edPassNew.getText().toString();
        passNewConfirm = edPassNewConfirm.getText().toString();

        if(passActual.isEmpty() || passNew.isEmpty() || passNewConfirm.isEmpty()){
            alerta("Error!", "Debe llenar todos los campos");

        }else{
            if (passNew.equalsIgnoreCase(passNewConfirm)){

                if(passActual.equalsIgnoreCase(passActualReal)){

                    dialog = new ProgressDialog(this);
                    dialog.setMessage("Actualizando contraseña");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                    String url = "https://proyectofinalprog2.000webhostapp.com/wsJSONCambiarContrase.php/?correo="+correo+"&pass="+passNew;

                    url = url.replace(" ", "%20");

                    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                    request.add(jsonObjectRequest);

                }else{
                    alerta("Error!", "Esta no es su contraseña actual");
                }

            }else{
                alerta("Error!", "Las contraseña no coinciden");
            }

        }

    }

    @Override
    public void onResponse(JSONObject response) {

        dialog.dismiss();
        Toast.makeText(this,"La contraseña se ha actualizado correctamente", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        dialog.dismiss();
        Toast.makeText(this,"Ha ocurrido un error al actualizar la contraseña" +error.getMessage(), Toast.LENGTH_SHORT).show();

    }

    public void alerta(String titulo, String mensaje){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

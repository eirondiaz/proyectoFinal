package com.example.SeccionMedico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Medico;
import com.example.Paciente;
import com.example.proyectofinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoPersonalMedico extends AppCompatActivity implements View.OnClickListener{

    private EditText edNombre, edApellido, edTelefono, edCorreo;
    private TextView tvMainNombre;
    private Button btnLogOut, btnEditar;
    boolean editMode = true;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_personal_medico);

        edNombre = findViewById(R.id.edNombre);
        edApellido = findViewById(R.id.edApellido);
        edTelefono = findViewById(R.id.edTelefono);
        edCorreo = findViewById(R.id.edCorreo);
        tvMainNombre = findViewById(R.id.tvMainNombre);

        btnEditar = findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String id = mAuth.getCurrentUser().getUid();

        mDatabase.child("Users").child("Medicos").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String nombre = dataSnapshot.child("nombre").getValue().toString();
                    edNombre.setText(nombre);
                    String apellido = dataSnapshot.child("apellido").getValue().toString();
                    edApellido.setText(apellido);
                    String telefono = dataSnapshot.child("telefono").getValue().toString();
                    edTelefono.setText(telefono);
                    String correo = dataSnapshot.child("email").getValue().toString();
                    edCorreo.setText(correo);

                    tvMainNombre.setText(nombre + " " + apellido);
                }
                catch (Exception e){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnLogOut:
                Medico.LogOut(this);
                finish();
                break;
            case R.id.btnEditar:
                editProcess();
                break;
        }
    }

    public void editProcess(){
        if (editMode){
            btnEditar.setText("GUARDAR");
            edNombre.setEnabled(true);
            edApellido.setEnabled(true);
            edTelefono.setEnabled(true);
            edCorreo.setEnabled(true);

            edNombre.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            edApellido.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            edTelefono.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            edCorreo.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            edNombre.requestFocus();
            edNombre.setSelection(edNombre.getText().length());

            btnEditar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_save_black_24dp, 0, 0, 0);

            editMode = false;
        }
        else{
            btnEditar.setText("editar");

            edNombre.setEnabled(false);
            edApellido.setEnabled(false);
            edTelefono.setEnabled(false);
            edCorreo.setEnabled(false);

            edNombre.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            edApellido.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            edTelefono.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            edCorreo.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

            btnEditar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edit_black_24dp, 0, 0, 0);

            String nombre = edNombre.getText().toString();
            String apellido = edApellido.getText().toString();
            String telefono = edTelefono.getText().toString();

            if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()){
                Toast.makeText(this, "No puedes dejar campos vacios", Toast.LENGTH_SHORT).show();
            }
            else{
                //Medico.Actualizar(this, nombre, apellido, telefono);
                editMode = true;
            }
        }
    }
}

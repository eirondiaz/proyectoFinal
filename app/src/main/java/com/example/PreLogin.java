package com.example;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.SeccionMedico.DashboardMedico;
import com.example.SeccionMedico.MedicoLogin;
import com.example.SeccionPaciente.PacienteLogin;
import com.example.proyectofinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PreLogin extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout lyPaciente, lyMedico;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        lyPaciente = findViewById(R.id.lyPaciente);
        lyPaciente.setOnClickListener(this);

        lyMedico = findViewById(R.id.lyMedico);
        lyMedico.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){

            String id = mAuth.getCurrentUser().getUid();

            mDatabase.child("Users").child("Medicos").child(id)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            try {
                                String nombre = dataSnapshot.child("exequatur").getValue().toString();
                                startActivity(new Intent(PreLogin.this, DashboardMedico.class));
                            }
                            catch (Exception e){
                                startActivity(new Intent(PreLogin.this, MainActivity.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //startActivity(new Intent(PreLogin.this, DashboardMedico.class));
                        }
                    });

            //startActivity(new Intent(PreLogin.this, MainActivity.class));
            finish();
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

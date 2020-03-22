package com.example.FragmentsMedico;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.SeccionMedico.InfoPersonalMedico;
import com.example.SeccionPaciente.CambiarContrasena;
import com.example.SeccionPaciente.HistorialCitas;
import com.example.SeccionPaciente.InformacionPersonal;
import com.example.proyectofinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MedicoPerfilFragment extends Fragment implements View.OnClickListener {

    private LinearLayout lyInfPer, lyCamContra;
    private TextView tvNombre, tvCargo;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_medico, container, false);

        tvNombre = view.findViewById(R.id.tvNombre);
        tvCargo = view.findViewById(R.id.tvCargo);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String id = mAuth.getCurrentUser().getUid();

        mDatabase.child("Users").child("Medicos").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String nombre = dataSnapshot.child("nombre").getValue().toString();
                    String apellido = dataSnapshot.child("apellido").getValue().toString();
                    String especialidad = dataSnapshot.child("especialidad").getValue().toString();
                    nombre = Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1,nombre.length());
                    apellido = Character.toUpperCase(apellido.charAt(0)) + apellido.substring(1,apellido.length());
                    especialidad = Character.toUpperCase(especialidad.charAt(0)) + especialidad.substring(1,especialidad.length());
                    tvNombre.setText(nombre + " " + apellido);
                    tvCargo.setText(especialidad);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lyInfPer = view.findViewById(R.id.lyInfPer);
        lyInfPer.setOnClickListener(this);

        lyCamContra = view.findViewById(R.id.lyCamContra);
        lyCamContra.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.lyInfPer:
                startActivity(new Intent(getContext(), InfoPersonalMedico.class));
                break;
        }
    }
}

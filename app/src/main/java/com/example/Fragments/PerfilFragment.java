package com.example.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.SeccionPaciente.CambiarContrasena;
import com.example.SeccionPaciente.HistorialCitas;
import com.example.SeccionPaciente.InformacionPersonal;
import com.example.proyectofinal.R;

public class PerfilFragment extends Fragment implements View.OnClickListener {

    private LinearLayout lyHistCitas, lyInfPer, lyCamContra;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        lyHistCitas = view.findViewById(R.id.lyHistCitas);
        lyHistCitas.setOnClickListener(this);

        lyInfPer = view.findViewById(R.id.lyInfPer);
        lyInfPer.setOnClickListener(this);

        lyCamContra = view.findViewById(R.id.lyCamContra);
        lyCamContra.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.lyHistCitas:
                startActivity(new Intent(getContext(), HistorialCitas.class));
                break;
            case R.id.lyInfPer:
                startActivity(new Intent(getContext(), InformacionPersonal.class));
                break;
            case R.id.lyCamContra:
                startActivity(new Intent(getContext(), CambiarContrasena.class));
                break;
        }
    }
}

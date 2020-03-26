package com.example.FragmentsPaciente;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.SeccionPaciente.CambiarContrasena;
import com.example.SeccionPaciente.HistorialCitas;
import com.example.SeccionPaciente.InformacionPersonal;
import com.example.proyectofinal.R;

public class PerfilFragment extends Fragment implements View.OnClickListener {

    private LinearLayout lyHistCitas, lyInfPer, lyCamContra;
    private TextView tvNombre, tvEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        tvNombre = view.findViewById(R.id.tvNombre);
        tvEmail = view.findViewById(R.id.tvEmail);

        getPreferences();

        lyHistCitas = view.findViewById(R.id.lyHistCitas);
        lyHistCitas.setOnClickListener(this);

        lyInfPer = view.findViewById(R.id.lyInfPer);
        lyInfPer.setOnClickListener(this);

        lyCamContra = view.findViewById(R.id.lyCamContra);
        lyCamContra.setOnClickListener(this);

        return view;
    }

    private void getPreferences(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        tvNombre.setText(preferences.getString("nombre", "") + " " + preferences.getString("apellido", ""));
        tvEmail.setText(preferences.getString("email", ""));
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

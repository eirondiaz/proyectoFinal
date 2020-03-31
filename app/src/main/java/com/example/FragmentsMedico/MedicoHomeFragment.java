package com.example.FragmentsMedico;

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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.AcercaDe;
import com.example.SeccionMedico.CitasAceptadas;
import com.example.SeccionMedico.CitasAgendadasMed;
import com.example.proyectofinal.R;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

public class MedicoHomeFragment extends Fragment implements View.OnClickListener{

    private TextView tvNombre;
    private CardView cardViewTexto;
    private LinearLayout lyAcercaDe, lyAcepCitas, lyAceptadas;
    String sexo;
    //private  int idUsuario ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home_medico, container, false);

        //String Item = getActivity().getIntent().getExtras().getString("idUsuario");
        tvNombre = view.findViewById(R.id.tvNombre);

        cardViewTexto = view.findViewById(R.id.cardViewTexto);
        cardViewTexto.setOnClickListener(this);

        lyAcercaDe = view.findViewById(R.id.lyAcercaDe);
        lyAcercaDe.setOnClickListener(this);

        lyAcepCitas = view.findViewById(R.id.lyAcepCitas);
        lyAcepCitas.setOnClickListener(this);

        lyAceptadas = view.findViewById(R.id.lyAceptadas);
        lyAceptadas.setOnClickListener(this);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        sexo = preferences.getString("sexo", "");
        if(sexo.equalsIgnoreCase("masculino")){
            tvNombre.setText("Hi, Dr. " + preferences.getString("nombre", "") + "!");
        }
        else{
            tvNombre.setText("Hi, Dra. " + preferences.getString("nombre", "") + "!");
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.lyAcercaDe:
                startActivity(new Intent(getContext(), AcercaDe.class));
                break;
            case R.id.lyAcepCitas:
                startActivity(new Intent(getContext(), CitasAgendadasMed.class));
                break;
            case R.id.lyAceptadas:
                startActivity(new Intent(getContext(), CitasAceptadas.class));
                break;
        }
    }
}

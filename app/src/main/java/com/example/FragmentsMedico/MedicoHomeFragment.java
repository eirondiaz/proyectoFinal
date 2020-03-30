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
import com.example.proyectofinal.R;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

public class MedicoHomeFragment extends Fragment {

    private TextView tvNombre;
    private CardView cardViewTexto;
    private LinearLayout lyAcercaDe;
    String sexo;
    //private  int idUsuario ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home_medico, container, false);

        //String Item = getActivity().getIntent().getExtras().getString("idUsuario");
        tvNombre = view.findViewById(R.id.tvNombre);
        cardViewTexto = view.findViewById(R.id.cardViewTexto);
        cardViewTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        lyAcercaDe = view.findViewById(R.id.lyAcercaDe);
        lyAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AcercaDe.class));
            }
        });

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
}

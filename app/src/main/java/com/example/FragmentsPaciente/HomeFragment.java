package com.example.FragmentsPaciente;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.AcercaDe;
import com.example.AgendarCita;
import com.example.MainActivity;
import com.example.SeccionPaciente.CitasAgendadas;
import com.example.SeccionPaciente.InformacionPersonal;
import com.example.proyectofinal.R;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private TextView tvNombre;
    private CardView cardViewTexto;
    private LinearLayout lvAcercaDe, lyAgendar, lyAgendadas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvNombre = view.findViewById(R.id.tvNombre);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        tvNombre.setText("Hi, " + preferences.getString("nombre", "") + "!");

        cardViewTexto = view.findViewById(R.id.cardViewTexto);
        cardViewTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hola mundo", Toast.LENGTH_SHORT).show();

            }
        });

        lvAcercaDe = view.findViewById(R.id.lvAcercaDe);
        lvAcercaDe.setOnClickListener(this);

        lyAgendar = view.findViewById(R.id.lyAgendar);
        lyAgendar.setOnClickListener(this);

        lyAgendadas = view.findViewById(R.id.lyAgendadas);
        lyAgendadas.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.lvAcercaDe:
                startActivity(new Intent(getContext(), AcercaDe.class));
                break;
            case R.id.lyAgendar:
                startActivity(new Intent(getContext(), AgendarCita.class));
                break;
            case R.id.lyAgendadas:
                startActivity(new Intent(getContext(), CitasAgendadas.class));
                break;
        }
    }
}

package com.example.FragmentsMedico;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.proyectofinal.R;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

public class MedicoHomeFragment extends Fragment {

    private TextView tvNombre;
    private CardView cardViewTexto;
    private  int idUsuario ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home_medico, container, false);

        String Item = getActivity().getIntent().getExtras().getString("idUsuario");
        tvNombre = view.findViewById(R.id.tvNombre);
        cardViewTexto = view.findViewById(R.id.cardViewTexto);
        cardViewTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }
}

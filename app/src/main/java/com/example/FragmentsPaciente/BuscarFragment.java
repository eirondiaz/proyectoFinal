package com.example.FragmentsPaciente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SeccionCitaMedica.RecylclerViewAdapter;
import com.example.Usuarios.Medico;
import com.example.proyectofinal.R;

import java.util.ArrayList;

public class BuscarFragment extends Fragment {
    RecyclerView recyclerViewMedicos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        //Solo de ejemplo
        getListMedicos();
        recyclerViewMedicos = view.findViewById(R.id.RecylerListMedicos);
        RecylclerViewAdapter adapter = new RecylclerViewAdapter(getListMedicos());
        recyclerViewMedicos.setAdapter(adapter);
        recyclerViewMedicos.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
    //Ejemplo de como se llenara la lista de los medicos
    private ArrayList<Medico>getListMedicos(){
        ArrayList<Medico> medicos = new ArrayList<>();
        //las imagens son de prueba, las pueden borrar
        medicos.add(new Medico("Daniel Tejada Montero","Medico General","El Morgan",R.drawable.medico1));
        medicos.add(new Medico("Andres Guzman","Medico General","Hospital San Antonio",R.drawable.medico2));
        return medicos;
    }
}

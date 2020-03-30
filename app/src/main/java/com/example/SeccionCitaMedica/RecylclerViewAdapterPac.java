package com.example.SeccionCitaMedica;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SeccionMedico.PerfilPaciente;
import com.example.SeccionPaciente.PerfilMedico;
import com.example.Usuarios.Medico;
import com.example.Usuarios.Paciente;
import com.example.proyectofinal.R;

import java.util.ArrayList;

public class RecylclerViewAdapterPac extends RecyclerView.Adapter<RecylclerViewAdapterPac.ViewHolder>{
    private static final String TAG = "RecylclerViewAdapter";
    ArrayList<Paciente>listPaciente;
    String id;

    public RecylclerViewAdapterPac(ArrayList<Paciente> listPaciente) {
        this.listPaciente = listPaciente;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pacientes_buscar,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageViewMedico.setImageResource(listPaciente.get(position).getFoto());
        holder.textViewNombre.setText(listPaciente.get(position).getNombre());
        holder.textViewSexo.setText(listPaciente.get(position).getSexo());
        holder.textViewcCorreo.setText(listPaciente.get(position).getEmail());
        holder.tvId.setText(listPaciente.get(position).getIdPaciente());
        //id = String.valueOf(lisMedico.get(position).getIdMedico());

        //Aqui se pondra a la escucha el recycler
    }

    @Override
    public int getItemCount() {
        return listPaciente.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMedico;
        TextView textViewNombre , textViewSexo, textViewcCorreo, tvId;
        LinearLayout parentLayoutMedico;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), PerfilPaciente.class);
                    Bundle b = new Bundle();
                    b.putString("id", tvId.getText().toString());
                    i.putExtras(b);
                    v.getContext().startActivity(i);
                    //Toast.makeText(v.getContext(), tvId.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            
            imageViewMedico = itemView.findViewById(R.id.ImageMedico);
            textViewNombre = itemView.findViewById(R.id.textvNombre);
            tvId = itemView.findViewById(R.id.tvId);
            textViewSexo = itemView.findViewById(R.id.textvSexo);
            textViewcCorreo = itemView.findViewById(R.id.textvCorreo);
            parentLayoutMedico = itemView.findViewById(R.id.parent_layout_medico);
        }
    }
}

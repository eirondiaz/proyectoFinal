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

import com.example.SeccionPaciente.PerfilMedico;
import com.example.Usuarios.Medico;
import com.example.proyectofinal.R;
import java.util.ArrayList;

public class RecylclerViewAdapter extends RecyclerView.Adapter<RecylclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecylclerViewAdapter";
    ArrayList<Medico>lisMedico;
    String id;

    public RecylclerViewAdapter(ArrayList<Medico> lisMedico) {
        this.lisMedico = lisMedico;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_medicos_buscar,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageViewMedico.setImageResource(lisMedico.get(position).getFoto());
        holder.textViewNombre.setText(lisMedico.get(position).getNombre());
        holder.textViewEspecialidades.setText(lisMedico.get(position).getEspecialidad());
        holder.textViewcCentroMedico.setText(lisMedico.get(position).getHospital());
        holder.tvId.setText(lisMedico.get(position).getIdMedico());
        //id = String.valueOf(lisMedico.get(position).getIdMedico());

        //Aqui se pondra a la escucha el recycler
    }

    @Override
    public int getItemCount() {
        return lisMedico.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMedico;
        TextView textViewNombre , textViewEspecialidades, textViewcCentroMedico, tvId;
        LinearLayout parentLayoutMedico;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), PerfilMedico.class);
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
            textViewEspecialidades = itemView.findViewById(R.id.textvEspecialidad);
            textViewcCentroMedico = itemView.findViewById(R.id.textvCentroMedico);
            parentLayoutMedico = itemView.findViewById(R.id.parent_layout_medico);
        }
    }
}

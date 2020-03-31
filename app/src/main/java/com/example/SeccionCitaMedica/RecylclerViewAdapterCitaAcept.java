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

import com.example.PerfilCitas;
import com.example.SeccionMedico.PerfilCitasMed;
import com.example.Usuarios.Cita;
import com.example.proyectofinal.R;

import java.util.ArrayList;

public class RecylclerViewAdapterCitaAcept extends RecyclerView.Adapter<RecylclerViewAdapterCitaAcept.ViewHolder>{
    private static final String TAG = "RecylclerViewAdapter";
    ArrayList<Cita>listCita;
    String id;

    public RecylclerViewAdapterCitaAcept(ArrayList<Cita> listCita) {
        this.listCita = listCita;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_citas_buscar,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageViewMedico.setImageResource(listCita.get(position).getFoto());
        holder.tvNombre.setText(listCita.get(position).getNombrePac());
        holder.tvStatus.setText(listCita.get(position).getStatus());
        holder.tvHospital.setText(listCita.get(position).getHospitalMed());
        holder.tvFecha.setText(listCita.get(position).getFecha());
        holder.tvId.setText(listCita.get(position).getIdCita());
        //id = String.valueOf(lisMedico.get(position).getIdMedico());

        //Aqui se pondra a la escucha el recycler
    }

    @Override
    public int getItemCount() {
        return listCita.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMedico;
        TextView tvNombre , tvStatus, tvHospital, tvFecha, tvId;
        LinearLayout parentLayoutMedico;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), PerfilCitasMed.class);
                    Bundle b = new Bundle();
                    b.putString("id", tvId.getText().toString());
                    i.putExtras(b);
                    v.getContext().startActivity(i);
                    //Toast.makeText(v.getContext(), tvId.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            
            imageViewMedico = itemView.findViewById(R.id.ImageMedico);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvId = itemView.findViewById(R.id.tvId);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvHospital = itemView.findViewById(R.id.tvHospital);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            parentLayoutMedico = itemView.findViewById(R.id.parent_layout_medico);
        }
    }
}

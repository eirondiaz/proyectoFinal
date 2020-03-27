package com.example.SeccionCitaMedica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Usuarios.Medico;
import com.example.proyectofinal.R;
import java.util.ArrayList;

public class RecylclerViewAdapter extends RecyclerView.Adapter<RecylclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecylclerViewAdapter";
    ArrayList<Medico>lisMedico;

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

        //Aqui se pondra a la escucha el recycler
    }

    @Override
    public int getItemCount() {
        return lisMedico.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMedico;
        TextView textViewNombre , textViewEspecialidades, textViewcCentroMedico;
        LinearLayout parentLayoutMedico;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMedico = itemView.findViewById(R.id.ImageMedico);
            textViewNombre = itemView.findViewById(R.id.textvNombre);
            textViewEspecialidades = itemView.findViewById(R.id.textvEspecialidad);
            textViewcCentroMedico = itemView.findViewById(R.id.textvCentroMedico);
            parentLayoutMedico = itemView.findViewById(R.id.parent_layout_medico);
        }
    }
}

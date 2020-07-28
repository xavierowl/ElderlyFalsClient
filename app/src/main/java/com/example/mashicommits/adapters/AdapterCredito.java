package com.example.mashicommits.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashicommits.R;
import com.example.mashicommits.models.Credito;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class AdapterCredito extends RecyclerView.Adapter<AdapterCredito.ViewHolder> {
    private List<Credito> creditos;
    private Context context;
    private OnCreditoListener objetoOnCreditoListener;

    public AdapterCredito(OnCreditoListener onCreditoListener){
        creditos = new ArrayList<Credito>();
        this.objetoOnCreditoListener = onCreditoListener;
    }

    public void setCreditos(List<Credito> creditos){
        this.creditos = creditos;
    };

    public List<Credito> getCreditos(){
        return creditos;
    };

    public void addCredito(Credito credito){
        creditos.add(credito);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credito, parent,
                false);
        context = parent.getContext();
        return new ViewHolder(view, objetoOnCreditoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCredito.ViewHolder holder, int position) {
        holder.tvPagado.setText(String.valueOf(creditos.get(position).getMonto()));
        holder.tvTipo.setText(String.valueOf(creditos.get(position).getTipo()));
        holder.tvFechaVencimiento.setText(String.valueOf(creditos.get(position).getFechaVencimiento()));
    }

    @Override
    public int getItemCount() {
        return this.creditos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvPagado;
        TextView tvTipo;
        TextView tvFechaVencimiento;
        OnCreditoListener onCreditoListener;

        ViewHolder(@NonNull View view, OnCreditoListener onCreditoListener) {
            super(view);
            tvPagado = itemView.findViewById(R.id.tvPagado);
            tvTipo = itemView.findViewById(R.id.tvTipoCredito);
            tvFechaVencimiento = itemView.findViewById(R.id.tvFechaVencimiento);
            this.onCreditoListener = onCreditoListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCreditoListener.onCreditoClick(creditos.get(getAdapterPosition()).getId());
        }
    }

    public interface OnCreditoListener{
        void onCreditoClick(int credito);
    }
}

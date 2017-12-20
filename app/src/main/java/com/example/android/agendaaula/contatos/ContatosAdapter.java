package com.example.android.agendaaula.contatos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.agendaaula.R;
import com.example.android.agendaaula.entidades.Contato;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.ViewHolder> {

    private List<Contato> contatoList;
    private OnRecyclerViewSelected onRecyclerViewSelected;
    private Context context;

    //Construtor que recebe a lista
    ContatosAdapter(List<Contato> contatoList, Context context) {
        this.contatoList = contatoList;
        this.context = context;
    }

    //infla o componente view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contato, parent, false);
        return new ViewHolder(v);
    }

    //seta os dados nas views
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contato contato = contatoList.get(position);

        if (contato.getCaminhoFoto() != null)
            Picasso.with(context)
                    .load("file://"+contato.getCaminhoFoto())
                    .centerCrop()
                    .fit()
                    .into(holder.imgFoto);

        holder.txNome.setText(contato.getNome());
        holder.txTelefone.setText(contato.getTelefone());
    }

    //retorna o tamanho da lista
    @Override
    public int getItemCount() {
        return contatoList.size();
    }

    //mapeamento dos componentes da view
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_nome)
        TextView txNome;

        @BindView(R.id.item_telefone)
        TextView txTelefone;

        @BindView(R.id.item_foto)
        ImageView imgFoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        //seta o clique rápido
        @OnClick(R.id.container)
        void onItemClick(View view){
            if(onRecyclerViewSelected != null)
                onRecyclerViewSelected.onClick(view, getAdapterPosition());

        }

    }

    public void setOnRecyclerViewSelected(OnRecyclerViewSelected onRecyclerViewSelected){
        this.onRecyclerViewSelected = onRecyclerViewSelected;
    }
}

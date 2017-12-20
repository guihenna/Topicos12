package com.example.android.agendaaula.contatos;

import com.example.android.agendaaula.BancoDeDados;
import com.example.android.agendaaula.entidades.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatosPresenter {

    private ContatosView view;
    private List<Contato> contatoList = new ArrayList<>();

    public ContatosPresenter(ContatosView view) {
        this.view = view;
    }

    public void adicionaNaLista(Contato contato) {
        contatoList.add(contato);
        view.atualizaLista(contatoList);
    }
}

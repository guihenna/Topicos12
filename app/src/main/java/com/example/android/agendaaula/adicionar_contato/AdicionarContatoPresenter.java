package com.example.android.agendaaula.adicionar_contato;

import com.example.android.agendaaula.entidades.Contato;

public class AdicionarContatoPresenter {

    private AdicionarContatoView view;

    public AdicionarContatoPresenter(AdicionarContatoView view) {
        this.view = view;
    }

    public Contato getContato(String nome, String telefone, String caminhoFoto) {
        Contato contato = new Contato(nome, telefone, caminhoFoto);

        if(!nome.isEmpty() && !telefone.isEmpty()) {
            return contato;
        }

        return null;
    }

    public void mostraContato(Contato contato) {
        if(contato != null)
            view.exibeInformacoes(contato);
    }

}

package com.example.android.agendaaula.entidades;

import com.example.android.agendaaula.BancoDeDados;

import java.io.Serializable;

public class Contato implements Serializable{
    private long id;
    private String nome;
    private String telefone;
    private String caminhoFoto;

    public Contato(long id, String nome, String telefone, String caminhoFoto) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.caminhoFoto = caminhoFoto;
    }

    public Contato(String nome, String telefone, String caminhoFoto) {
        this.id = 0;
        this.nome = nome;
        this.telefone = telefone;
        this.caminhoFoto = caminhoFoto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }
}

package com.example.android.agendaaula.contatos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.agendaaula.BancoDeDados;
import com.example.android.agendaaula.R;
import com.example.android.agendaaula.adicionar_contato.AdicionarContatoActivity;
import com.example.android.agendaaula.entidades.Contato;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContatosActivity extends AppCompatActivity implements  ContatosView {

    @BindView(R.id.rv_contatos)
    RecyclerView rvContatos;

    private ContatosPresenter presenter;
    private final int CODIGO = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        ButterKnife.bind(this);
        presenter = new ContatosPresenter(this);
        inicializaLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contatos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_adicionar_contato:
                Intent adicionarContato = new Intent(ContatosActivity.this, AdicionarContatoActivity.class);
                startActivityForResult(adicionarContato, CODIGO);

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO && resultCode == Activity.RESULT_OK) {
            Contato contato = (Contato) data.getSerializableExtra("contato");
            if (contato != null) {
                BancoDeDados bd = new BancoDeDados(this);
                contato.setId(bd.addContato(contato));
                presenter.adicionaNaLista(contato);
            }
        }
    }

    @Override
    public void atualizaLista(final List<Contato> contatoList) {

        //seta o adapter
        ContatosAdapter contatosAdapter = new ContatosAdapter(contatoList, this);
        //seta o clique do adapter
        contatosAdapter.setOnRecyclerViewSelected(new OnRecyclerViewSelected() {
            @Override
            public void onClick(View view, int position) {
                Intent exibirContato = new Intent(ContatosActivity.this, AdicionarContatoActivity.class);
                exibirContato.putExtra("exibir_contato", contatoList.get(position));
                startActivity(exibirContato);
            }
        });

        rvContatos.setAdapter(contatosAdapter);

        // criação do gerenciador de layouts
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvContatos.setLayoutManager(layoutManager);

        //inserindo separador de itens
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        rvContatos.addItemDecoration(dividerItemDecoration);

    }

    public void inicializaLista() {
        BancoDeDados bd = new BancoDeDados(this);
        List<Contato> l = bd.getLista();
        for (Contato c : l) {
            presenter.adicionaNaLista(c);
        }
    }
}

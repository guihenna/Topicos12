package com.example.android.agendaaula.adicionar_contato;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.android.agendaaula.R;
import com.example.android.agendaaula.entidades.Contato;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdicionarContatoActivity extends AppCompatActivity implements AdicionarContatoView {

    /*
        Bindings
     */
    @BindView(R.id.formulario_nome)
    public EditText campoNome;
    @BindView(R.id.formulario_telefone)
    public EditText campoTelefone;
    @BindView(R.id.formulario_foto)
    public ImageView campoFoto;

    File file;
    Uri fileUri;

    private AdicionarContatoPresenter presenter;

    private static final int CODIGO_CAMERA = 123;
    public String caminhoFoto;

    private Bitmap picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        setContentView(R.layout.activity_adicionar_contato);

        ButterKnife.bind(this);
        presenter = new AdicionarContatoPresenter(this);

        presenter.mostraContato((Contato) getIntent().getSerializableExtra("exibir_contato"));

        List<String> lista = new ArrayList<String>();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            lista.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            System.out.println(1 + "\n");
        }

        permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            lista.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            System.out.println(2 + "\n");
        }

        permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            lista.add(Manifest.permission.CAMERA);
            System.out.println(3 + "\n");
        }
        String[] listaArr = new String[lista.size()];
        listaArr = lista.toArray(listaArr);
        if(lista.size() > 0)
            ActivityCompat.requestPermissions(this, listaArr, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_contato, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_salvar:

                Intent resultado = new Intent().putExtra("contato",
                        presenter.getContato(campoNome.getText().toString(),
                                campoTelefone.getText().toString(),
                                caminhoFoto));

                setResult(Activity.RESULT_OK, resultado);
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void exibeInformacoes(Contato contato) {
        campoNome.setText(contato.getNome());
        campoTelefone.setText(contato.getTelefone());
        caminhoFoto = contato.getCaminhoFoto();
        exibeFoto();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {

                String filename = String.valueOf(System.currentTimeMillis()) + ".jpg";
                File sd = Environment.getExternalStorageDirectory();
                File dest = new File(sd, filename);

                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                try {
                    FileOutputStream out = new FileOutputStream(dest.getPath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    caminhoFoto = dest.getPath();
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                exibeFoto();
                //campoFoto.setImageBitmap(picture); //for example I put bmp in an ImageView
            }
        }
    }

    @OnClick(R.id.formulario_botao_foto)
    public void tiraFoto() {


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //file = new File(getExternalCacheDir(), String.valueOf(System.currentTimeMillis()) + ".jpg");
        //fileUri = Uri.fromFile(file);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, file.getAbsolutePath());
        startActivityForResult(intent, CODIGO_CAMERA);
        //exibeFoto();
    }

    private void exibeFoto(){
        System.out.println("TESTE:\n" + caminhoFoto);
        //File f = new File(caminhoFoto);
        Picasso.with(this)
                .load("file://" + caminhoFoto)
                .fit()
                .centerCrop()
                .into(campoFoto);
    }

}

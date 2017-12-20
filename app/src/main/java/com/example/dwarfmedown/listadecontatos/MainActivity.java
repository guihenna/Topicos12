package com.example.dwarfmedown.listadecontatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_input_layout_username) TextInputLayout usernameTextInputLayout;
    @BindView(R.id.edit_text_username) TextInputEditText usernameEditText;
    @BindView(R.id.text_input_layout_password) TextInputLayout passwordTextInputLayout;
    @BindView(R.id.edit_text_password) TextInputEditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnTextChanged(R.id.edit_text_username)
    public void validaUsuario(){
        usernameTextInputLayout.setErrorEnabled(false);
        usernameTextInputLayout.setError("");

    }

    @OnTextChanged(R.id.edit_text_password)
    public void validaSenha(){
        passwordTextInputLayout.setErrorEnabled(false);
        passwordTextInputLayout.setError("");

    }

    @OnClick(R.id.button_login)
    public void fazLogin(){
        if (TextUtils.isEmpty(usernameEditText.getText().toString())) {
            usernameTextInputLayout.setErrorEnabled(true);
            usernameTextInputLayout.setError(getString(R.string.invalid_username));
            return;
        }

        if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
            passwordTextInputLayout.setErrorEnabled(true);
            passwordTextInputLayout.setError(getString(R.string.invalid_password));
            return;
        }

        Intent abrirAgendaActivity = new Intent(MainActivity.this, Agenda.class);
        startActivity(abrirAgendaActivity);

    }

}

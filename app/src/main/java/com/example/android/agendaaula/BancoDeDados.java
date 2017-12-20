package com.example.android.agendaaula;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.agendaaula.entidades.Contato;

import java.util.ArrayList;

import com.example.android.agendaaula.entidades.Contato;

/*
    Classe para controlar o Banco de Dados
    Documentacao: https://goo.gl/4mXuBS
 */
public class BancoDeDados extends SQLiteOpenHelper{

    /*
        Constantes para o Banco de Dados
     */
    public static final String BD_Name = "bd";
    public static final String Table_Name = "contatos";
    public static final String Column1 = "id";
    public static final String Column2 = "nome";
    public static final String Column3 = "telefone";
    public static final String Column4 = "imagem";

    /*
        Construtor principal
        ctx vai representar o contexto atual do sistema
     */
    public BancoDeDados(Context ctx) {
        // (Contexto, Nome do BD, Factory, Version)
        super(ctx, BD_Name, null, 1);
    }

    /*
        Construtores Padroes (Gerados automaticamente)
     */
    public BancoDeDados(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public BancoDeDados(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*
            CREATE TABLE acoes (id INTEGER PRIMARY KEY NOT NULL, name TEXT NOT NULL,
            image TEXT NOT NULL, description TEXT NOT NULL, site TEXT NOT NULL)
         */
        sqLiteDatabase.execSQL("CREATE TABLE " + Table_Name + " (" +
                Column1 + " INTEGER PRIMARY KEY NOT NULL, " + Column2 + " TEXT NOT NULL, " +
                Column3 + " TEXT NOT NULL, " + Column4 + " TEXT NOT NULL)");
    }

    /*
        Ao atualizar o BD, apaga a tabela existente e cria uma nova, com as novas informacoes
        Referencia: https://goo.gl/cKKU56
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Apaga a tabela
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        // Recria a tabela
        //onCreate(sqLiteDatabase);
    }

    /*
        Adicionar uma Acao Social no BD
        Retorna: - true se deu tudo certo
                 - false se nao foi possivel inserir
     */
    public long addContato(Contato contato) {

        /*
            Cria um objeto BD para escrita
         */
        SQLiteDatabase sqlBD = getWritableDatabase();
        ContentValues cv = new ContentValues();

        /*
            Nao precisa inserir a primeira coluna, pois a chave vai ser gerada sequencialmente
         */
        cv.put(Column2, contato.getNome());
        cv.put(Column3, contato.getTelefone());
        cv.put(Column4, contato.getCaminhoFoto());

        long id = sqlBD.insert(Table_Name, null, cv);
        sqlBD.close();

        return id;
    }

    public ArrayList<Contato> getLista() {
        ArrayList<Contato> lista = new ArrayList<Contato>();

        /*
            Seleciona todos os registro ordenados pelos nomes
         */
        String sql = "SELECT * FROM " + Table_Name + " ORDER BY " + Column2;

        /*
            Cria um objeto BD para leitura
         */
        SQLiteDatabase sqlBD = getReadableDatabase();

        /*
            Cria um cursor para passar por todos os registros
         */
        Cursor cursor = sqlBD.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Column1));
                String name = cursor.getString(cursor.getColumnIndex(Column2));
                String telefone = cursor.getString(cursor.getColumnIndex(Column3));
                String imagem = cursor.getString(cursor.getColumnIndex(Column4));

                Contato novo = new Contato(id, name, telefone, imagem);
                lista.add(novo);
            } while(cursor.moveToNext());
        }
        cursor.close();

        /*System.out.println("Teste BD");
        for(AcaoSocial acao: lista) {
            System.out.println(acao.getDescription());
        }*/
        return lista;
    }

    /*
        Apaga todos os dados do BD
     */
    public void limparBD() {
        SQLiteDatabase sqlBD = getWritableDatabase();
        String del = "DELETE FROM " + Table_Name + " ;";
        sqlBD.execSQL(del);
    }
}
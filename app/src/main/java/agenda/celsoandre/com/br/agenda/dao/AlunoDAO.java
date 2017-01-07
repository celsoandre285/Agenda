package agenda.celsoandre.com.br.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import agenda.celsoandre.com.br.agenda.modelo.Aluno;

/**
 * Created by Celso André on 05/01/2017.
 */

public class AlunoDAO extends SQLiteOpenHelper{
    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos(id INTEGER PRIMARY KEY, nome TEXT NOT NULL,endereco TEXT, telefone TEXT, site TEXT, email TEXT, nota REAL, caminhofoto TEXT); ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";

        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE Alunos ADD COLUMN caminhofoto TEXT";
                db.execSQL(sql);
        }

    }
    //Insere Aluno
    public void insere(Aluno aluno) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues dados = pegaDadosDoAluno(aluno);
        sqLiteDatabase.insert("Alunos", null, dados );
    }

    @NonNull
    private ContentValues pegaDadosDoAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("site", aluno.getSite());
        dados.put("email", aluno.getEmail());
        dados.put("nota", aluno.getNota());
        dados.put("telefone", aluno.getTelefone());
        dados.put("caminhofoto", aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> buscaAlunos() {
        String sql = "SELECT * FROM Alunos";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Aluno> alunos = new ArrayList<Aluno>();

        while(cursor.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId_aluno(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhofoto")));
            alunos.add(aluno);
        }
        cursor.close();
        return  alunos;
    }

    public void deletar(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params  = {aluno.getId_aluno().toString()};
        db.delete("Alunos", "id = ?", params);
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoAluno(aluno);

        String[] params = {aluno.getId_aluno().toString()};
        db.update("Alunos",dados, "id = ?" , params);
    }

    public boolean ehAluno(String telefone){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM alunos WHERE telefone = ?", new String[]{telefone});
        int resultado = c.getCount();
        c.close();
        return resultado>0;
    }
}

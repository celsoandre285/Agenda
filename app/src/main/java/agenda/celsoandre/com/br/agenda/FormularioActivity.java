package agenda.celsoandre.com.br.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import agenda.celsoandre.com.br.agenda.dao.AlunoDAO;
import agenda.celsoandre.com.br.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {
    private Intent itListaAluno;
    private FormularioHelper fHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        // instanciando o Helper de formulario
        fHelper = new FormularioHelper(this);

        Intent intente = getIntent();
        Aluno aluno = (Aluno) intente.getSerializableExtra("aluno");
        if(aluno != null){
            fHelper.preencerFomurlario(aluno);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = fHelper.pegaAluno();
                AlunoDAO dao  = new AlunoDAO(this);
                if(aluno.getId_aluno() != null){
                    dao.altera(aluno);
                }else{
                    dao.insere(aluno);
                }
                dao.close();
                Toast.makeText(FormularioActivity.this, aluno.getNome() + "cadastrado", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

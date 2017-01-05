package agenda.celsoandre.com.br.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import agenda.celsoandre.com.br.agenda.dao.AlunoDAO;
import agenda.celsoandre.com.br.agenda.modelo.Aluno;

public class ListaAlunoActivity extends AppCompatActivity {
    private ListView list_item;
    private ArrayAdapter adapter;
    private Button btnNovo;
    Intent itFormulario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        //Vinculando Views
        list_item = (ListView) findViewById(R.id.lista_alunos);
        list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long l) {
                Aluno aluno = (Aluno) list_item.getItemAtPosition(position);
                itFormulario = new Intent(ListaAlunoActivity.this, FormularioActivity.class);
                itFormulario.putExtra("aluno", aluno);
                //Toast.makeText(ListaAlunoActivity.this, aluno.getNome(), Toast.LENGTH_SHORT).show();
                startActivity(itFormulario);

            }
        });

        btnNovo = (Button) findViewById(R.id.novo_aluno);
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itFormulario = new Intent(ListaAlunoActivity.this, FormularioActivity.class);
                startActivity(itFormulario);
            }
        });

        registerForContextMenu(list_item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Carregando Lista
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deseja deletar?");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info  = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno)list_item.getItemAtPosition(info.position);
                AlunoDAO dao = new AlunoDAO(ListaAlunoActivity.this);
                dao.deletar(aluno);
                dao.close();
                carregaLista();
                return false;
            }
        });
    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        //Criando Adapter
        adapter = new ArrayAdapter<Aluno>(this,     //contexto
                android.R.layout.simple_list_item_1, //Layout
                alunos);                             //Array
        //Setando o Adapter na listView
        list_item.setAdapter(adapter);
    }


}

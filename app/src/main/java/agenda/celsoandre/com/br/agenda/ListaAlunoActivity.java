package agenda.celsoandre.com.br.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaAlunoActivity extends AppCompatActivity {
    private ListView list_item;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        String[] alunos = {"celso", "andre", "renan", "caio"};
        //Vinculando Views
        list_item = (ListView) findViewById(R.id.lista_alunos);
        //Criando Adapter
        adapter = new ArrayAdapter<String>(this,     //contexto
                android.R.layout.simple_list_item_1, //Layout
                alunos);                             //Array
        //Setando o Adapter na listView
        list_item.setAdapter(adapter);

    }
}

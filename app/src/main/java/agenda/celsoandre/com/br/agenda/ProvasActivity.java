package agenda.celsoandre.com.br.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import agenda.celsoandre.com.br.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {
    private ListView listaCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        List<String> topicPort = Arrays.asList("Adjetivos", "Substantivis", "Adjetivos", "Obj. Direto", "Obj Indireto");
        Prova provaPortugues = new Prova("Portugues", "07/01/2017", topicPort);

        List<String> topicMatem = Arrays.asList("Equação de primeiro Grau", "Equação de segundo Grau", "Sistemas");
        Prova provaMatematica = new Prova("Matematica", "07/01/2017", topicMatem);

        List<Prova>provas =  Arrays.asList(provaMatematica, provaPortugues);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(this, android.R.layout.simple_list_item_1,provas );

        listaCard = (ListView) findViewById(R.id.provasLista);
        listaCard.setAdapter(adapter);

        listaCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(ProvasActivity.this, "Clicou na prova de  "+ prova , Toast.LENGTH_SHORT).show();
                Intent itDetalhesProva = new Intent(ProvasActivity.this, DetalhesProvaActivity.class);
                itDetalhesProva.putExtra("prova", prova);
                startActivity(itDetalhesProva);
            }
        });
    }
}

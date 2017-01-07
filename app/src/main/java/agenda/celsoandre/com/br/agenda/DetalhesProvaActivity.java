package agenda.celsoandre.com.br.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import agenda.celsoandre.com.br.agenda.modelo.Prova;

public class DetalhesProvaActivity extends AppCompatActivity {

    private TextView txtMateria;
    private TextView txtData;
    private ListView litaTopicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);
        Intent itPtova = getIntent();
        Prova prova = (Prova) itPtova.getSerializableExtra("prova");

        Log.i("Test" , prova.getMateria());

        txtMateria = (TextView) findViewById(R.id.detalhes_prova_materia);
        txtData = (TextView) findViewById(R.id.detalhes_prova_data);
        litaTopicos = (ListView) findViewById(R.id.detalhes_prova_topicos);

        txtMateria.setText(prova.getMateria());
        txtData.setText(prova.getData());


        ArrayAdapter<String>adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prova.getTopicos());
        litaTopicos.setAdapter(adapter);


    }
}

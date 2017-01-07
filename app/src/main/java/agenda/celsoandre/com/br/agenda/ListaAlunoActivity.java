package agenda.celsoandre.com.br.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import agenda.celsoandre.com.br.agenda.adapter.AlunosAdapter;
import agenda.celsoandre.com.br.agenda.converter.AlunoConverter;
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

        if(ActivityCompat.checkSelfPermission(ListaAlunoActivity.this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ListaAlunoActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, 690);
        }


        //Vinculando Views
        list_item = (ListView) findViewById(R.id.lista_alunos);
        list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long l) {
                Aluno aluno = (Aluno) list_item.getItemAtPosition(position);
                // Aluno aluno = (Aluno) lista.getItemAtPosition(position);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_aluno, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_enviar_notas:
                new EnviarAlunosTask(this).execute();
                break;
            case R.id.menu_baixar_provas:
                startActivity(new Intent(ListaAlunoActivity.this, ProvasActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info  = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno)list_item.getItemAtPosition(info.position);
        // Itens do menu
        MenuItem email = menu.add("Mandar Email");
        MenuItem ligar = menu.add("Ligar");
        MenuItem mapa = menu.add("Ver no Mapa");
        MenuItem sms = menu.add("Enviar SMS");
        MenuItem abrirSite = menu.add("Abrir Site");
        MenuItem deletar = menu.add("Deseja deletar?");

        //Enviar E-mail
        Intent itMail = new Intent(Intent.ACTION_SENDTO);
        itMail.setData(Uri.parse("mailto:" + aluno.getEmail()));
        sms.setIntent(itMail);

        //Ligar
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //Pedindo permissão
                if(ActivityCompat.checkSelfPermission(ListaAlunoActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaAlunoActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 691);
                }else {
                    Intent itFone = new Intent(Intent.ACTION_CALL);
                    itFone.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(itFone);
                }
                return false;
            }
        });




        //Enviar sms
        Intent itSMS  = new Intent(Intent.ACTION_VIEW);
        itSMS.setData(Uri.parse("sms:"+ aluno.getTelefone()));
        sms.setIntent(itSMS);

        //Abrir Mapa
        Intent itMAPA = new Intent(Intent.ACTION_VIEW);
        itMAPA.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        Log.i("endereco", aluno.getEndereco());
        mapa.setIntent(itMAPA);

        //Abrir Site
        String site =  aluno.getSite();
        if(!site.startsWith("http://") || !site.startsWith("https://")  ){
            site  = "http://" + site;
        }
        Intent itSite = new Intent(Intent.ACTION_VIEW);
        itSite.setData(Uri.parse(site));
        abrirSite.setIntent(itSite);

        //Deletar Item do menu de contexto
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
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
        //adapter = new ArrayAdapter<Aluno>(this,     //contexto
        //        android.R.layout.simple_list_item_1, //Layout
        //        alunos);                             //Array


        //Criando Adapeter
        AlunosAdapter adapter = new AlunosAdapter(this, alunos);
        //Setando o Adapter na listView
        list_item.setAdapter(adapter);
    }


}

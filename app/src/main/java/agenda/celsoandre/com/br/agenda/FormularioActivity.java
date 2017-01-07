package agenda.celsoandre.com.br.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;

import agenda.celsoandre.com.br.agenda.dao.AlunoDAO;
import agenda.celsoandre.com.br.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {
    private static final int CODIGO_CAMERA = 567;
    private Intent itListaAluno;
    private FormularioHelper fHelper;
    private Button btnFoto;
    private String caminhoFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        //Vinculando Views
        btnFoto = (Button) findViewById(R.id.formulario_btnfoto);

        // instanciando o Helper de formulario
        fHelper = new FormularioHelper(this);

        //Preencher Fomulario para atualização
        Intent intente = getIntent();
        Aluno aluno = (Aluno) intente.getSerializableExtra("aluno");
        if(aluno != null){
            fHelper.preencerFomurlario(aluno);
        }

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent de camera
                Intent itCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Caminho da foto
                caminhoFoto = getExternalFilesDir(null) + "/foto"+System.currentTimeMillis()+".jpg";
                Log.i("foto", caminhoFoto);
                // Criando objeto do tipo File
                File arquivoFoto = new File(caminhoFoto);
                // Paddando o Arquivo da foto por put Extra
                itCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                //
                startActivityForResult(itCamera, CODIGO_CAMERA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if (CODIGO_CAMERA == 567) {
                fHelper.carregaFoto(caminhoFoto);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

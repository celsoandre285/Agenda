package agenda.celsoandre.com.br.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import agenda.celsoandre.com.br.agenda.converter.AlunoConverter;
import agenda.celsoandre.com.br.agenda.dao.AlunoDAO;
import agenda.celsoandre.com.br.agenda.modelo.Aluno;

/**
 * Created by Celso André on 07/01/2017.
 */

public class EnviarAlunosTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private ProgressDialog dialog;

    public EnviarAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void[] objects) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converterParaJson(alunos);

        WebClient webClient = new WebClient();
        String resposta = webClient.post(json);

        //Toast.makeText(this, resposta, Toast.LENGTH_SHORT).show();
        return resposta;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde Porr@", "Enviando Alunos...", true, true);
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_SHORT).show();
    }
}

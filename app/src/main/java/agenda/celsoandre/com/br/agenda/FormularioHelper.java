package agenda.celsoandre.com.br.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import agenda.celsoandre.com.br.agenda.modelo.Aluno;

/**
 * Created by Celso André on 05/01/2017.
 */

public class FormularioHelper {
    private final EditText campoNome;
    private final EditText campoEnd;
    private final EditText campoEmail;
    private final EditText campoFone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;
    private Aluno aluno;

    //Construtor da Classe
    public FormularioHelper(FormularioActivity activity){
        campoNome = (EditText) activity.findViewById(R.id.edtNome);
        campoEnd = (EditText) activity.findViewById(R.id.edtEndereco);
        campoEmail = (EditText) activity.findViewById(R.id.edtEmail);
        campoFone = (EditText) activity.findViewById(R.id.edtTelefone);
        campoSite = (EditText) activity.findViewById(R.id.edtSite);
        campoNota = (RatingBar) activity.findViewById(R.id.rtbNota);
        campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
        aluno = new Aluno();
    }
    //Metodo Pega Aluno
    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEmail(campoEmail.getText().toString());
        aluno.setEndereco(campoEnd.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setTelefone(campoFone.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String) campoFoto.getTag());

        return aluno;
    }

    public void preencerFomurlario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEmail.setText(aluno.getEmail());
        campoEnd.setText(aluno.getEndereco());
        campoSite.setText(aluno.getSite());
        campoFone.setText(aluno.getTelefone());
        campoNota.setProgress(aluno.getNota().intValue());
        carregaFoto(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    public void carregaFoto(String caminhoFoto) {
        if(caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }
    }
}

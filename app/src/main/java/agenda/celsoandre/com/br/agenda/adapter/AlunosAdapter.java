package agenda.celsoandre.com.br.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import agenda.celsoandre.com.br.agenda.ListaAlunoActivity;
import agenda.celsoandre.com.br.agenda.R;
import agenda.celsoandre.com.br.agenda.modelo.Aluno;

/**
 * Created by Celso André on 06/01/2017.
 */

public class AlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.alunos = alunos;
        this.context = context;
    }

    @Override
    public int getCount() {
        //quantos itens existem nos array
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId_aluno();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);
        LayoutInflater inflater =  LayoutInflater.from(context);
        View v = convertView;

        if (v == null) {
            v = inflater.inflate(R.layout.list_item, parent, false);
        }


        TextView campoNome  = (TextView) v.findViewById(R.id.item_nome);
        campoNome.setText(aluno.getNome());
        TextView campoEmail  = (TextView) v.findViewById(R.id.item_telefone);
        campoEmail.setText(aluno.getTelefone());

        TextView campoEndereco = (TextView) v.findViewById(R.id.item_endereco);
        if(campoEndereco!=null){
            campoEndereco.setText(aluno.getEndereco());
        }


        TextView campoSite = (TextView) v.findViewById(R.id.item_site);
        if(campoSite != null){
            campoSite.setText(aluno.getSite());
        }


        ImageView campoFoto = (ImageView) v.findViewById(R.id.item_foto);
        String caminhoFoto = aluno.getCaminhoFoto();
        if(caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }

        return v;
    }
}

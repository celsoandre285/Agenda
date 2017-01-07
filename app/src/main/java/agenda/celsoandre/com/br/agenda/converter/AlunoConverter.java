package agenda.celsoandre.com.br.agenda.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import agenda.celsoandre.com.br.agenda.modelo.Aluno;

/**
 * Created by Celso André on 07/01/2017.
 */
public class AlunoConverter {
    public String converterParaJson(List<Aluno> alunos) {
        JSONStringer js  =  new JSONStringer();
        try {
            js.object()
                    .key("list")
                    .array()
                    .object()
                    .key("aluno")
                    .array();
            for(Aluno aluno : alunos){
                js.object();
                    js.key("nome").value(aluno.getNome());
                    js.key("nota").value(aluno.getNota());
                    js.endObject();
            }
            js.endArray()
                    .endObject()
                    .endArray()
                    .endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }

}

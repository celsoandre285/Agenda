package agenda.celsoandre.com.br.agenda.modelo;

import java.io.Serializable;

/**
 * Created by Celso André on 05/01/2017.
 */
public class Aluno implements Serializable{
    private Long id_aluno;
    private String nome;
    private String endereco;
    private String email;
    private String site;
    private String Telefone;
    private Double nota;


    public Long getId_aluno() {
        return id_aluno;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public void setId_aluno(Long id_aluno) {
        this.id_aluno = id_aluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return getId_aluno() +" "+getNome();
    }
}

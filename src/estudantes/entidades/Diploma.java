package estudantes.entidades;

import java.util.Objects;
import professor.entidades.CodigoCurso;


public class Diploma extends Certificado{
    private String habilitacao;

    //construtor
    public Diploma(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String estudante, long matricula, String descricao, String habilitacao) {
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula, descricao);
        this.habilitacao = habilitacao;
    }

    public String getHabilitacao() {
        return habilitacao;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o))
            return false; // verifica se os atributos herdados da superclassse sao iguais
        Diploma diploma = (Diploma) o;

        // verifica se os atributos especificos da subclasse sao iguais:
        return habilitacao.equals(diploma.habilitacao);
}

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), habilitacao); // repassa a responsabilidade p a superclasse com atributos locais
    }

}
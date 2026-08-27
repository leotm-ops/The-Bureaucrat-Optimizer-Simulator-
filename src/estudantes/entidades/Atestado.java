package estudantes.entidades;

import java.util.Objects;
import professor.entidades.CodigoCurso;


public class Atestado extends Plano {
    private String descricao;
    private String categoria;

    //construtores
    public Atestado(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String responsavel, String[] planejamento, String descricao, String categoria) {
        super(criador, codigoCurso, paginas, autenticacao, responsavel, planejamento);
        this.descricao = descricao;
        this.categoria = categoria;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o))
            return false; // verifica se os atributos herdados da superclassse sao iguais
        Atestado atestado = (Atestado) o;

        // verifica se os atributos especificos da subclasse sao iguais:
        return descricao.equals(atestado.descricao) && categoria.equals(atestado.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), descricao, categoria); // repassa a responsabilidade p a superclasse com atributos locais
    }

}
package estudantes.entidades;

import java.util.Objects;
import professor.entidades.CodigoCurso;

public class Plano extends DocumentoAcademico {
    private String responsavel;
    private String[] planejamento;

    // contrutores
    public Plano(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String responsavel, String[] planejamento){
    super(criador, codigoCurso, paginas, autenticacao);
    this.responsavel = responsavel;
    this.planejamento = planejamento;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public String[] getPlanejamento() {
        return planejamento;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o))
            return false; // verifica se os atributos herdados da superclassse sao iguais
        Plano plano = (Plano) o;

        // verifica se os atributos especificos da subclasse sao iguais:
        return responsavel.equals(plano.responsavel) && Objects.equals(planejamento, plano.planejamento); 
        
   
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), responsavel, planejamento); // repassa a responsabilidade p a superclasse com atributos locais
    }

}

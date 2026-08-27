package estudantes.entidades;

import java.util.Objects;
import professor.entidades.CodigoCurso;

public class Certificado extends Registro{
    private String descricao;

    //construtores
    public Certificado(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String estudante, long matricula, String descricao){
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula);
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object o){
        if (!super.equals(o))
            return false; // verifica se os atributos herdados da superclassse sao iguais
        Certificado certificado = (Certificado) o;

        // verifica se os atributos especificos da subclasse sao iguais:
        return Objects.equals(descricao, certificado.descricao);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), descricao); // repassa a responsabilidade p a superclasse com atributos locais
    }

    
}
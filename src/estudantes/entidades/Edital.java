package estudantes.entidades;

import java.util.Arrays;
import java.util.Objects;
import professor.entidades.CodigoCurso;

public class Edital extends Norma{
    private String[] responsaveis;

    //construtor:
    public Edital(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto, String[] responsaveis){
        super(criador, codigoCurso, paginas, numero, valido, texto);
        this.responsaveis = responsaveis;
    }

    public String[] getResponsaveis(){
        return responsaveis;
    }

    @Override
    public boolean equals(Object o){
        if (!super.equals(o))
            return false; // verifica se os atributos herdados da superclassse sao iguais
        Edital edital = (Edital) o;

        // verifica se os atributos especificos da subclasse sao iguais:
        return Arrays.equals(responsaveis, edital.responsaveis);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), Arrays.hashCode(responsaveis));
    }
}


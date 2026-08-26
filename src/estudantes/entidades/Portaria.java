package estudantes.entidades;

import java.util.Objects;
import professor.entidades.CodigoCurso;

public class Portaria extends Norma{
    private int anoInicio;

    // construtor:
    public Portaria(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto, int anoInicio){
        super(criador, codigoCurso, paginas, numero, valido, texto);
        this.anoInicio = anoInicio;
    }

    public int getAnoInicio(){
        return anoInicio;
    }

    @Override
    public boolean equals(Object o){
        if (!super.equals(o))
            return false; // verifica se os atributos herdados da superclassse sao iguais
        Portaria portaria = (Portaria) o;

        // verifica se os atributos especificos da subclasse sao iguais:
        return anoInicio ==  portaria.anoInicio; // verifica se os atributos específicos de portaria sao iguais
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), anoInicio); // repassa a responsabilidade p a superclasse com atributos locais
    }
}
package estudantes.entidades;

import java.util.Arrays;
import java.util.Objects;
import professor.entidades.CodigoCurso;

public class Circular extends Deliberacao{
    private String[] destinatarios;

    // construtor:
    public Circular(String criador, CodigoCurso codigoCurso, int paginas, String texto, String[] destinatarios){
        super(criador, codigoCurso, paginas, texto);
        this.destinatarios = destinatarios;
    }

    public String[] getDestinatarios(){
        return destinatarios;
    }

    @Override
    public boolean equals(Object o){
        if (!super.equals(o))
            return false; // verifica se os atributos herdados da superclassse sao iguais
        Circular circular = (Circular) o;

        // verifica se os atributos especificos da subclasse sao iguais:
        return Arrays.equals(destinatarios, circular.destinatarios);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), Arrays.hashCode(destinatarios));
    }
}
package estudantes.entidades;

import java.util.Objects;
import professor.entidades.CodigoCurso;

public class Oficio extends Deliberacao{
    private String destinatario;

    // construtor:
    public Oficio(String criador, CodigoCurso codigoCurso, int paginas, String texto, String destinatario){
        super(criador, codigoCurso, paginas, texto);
        this.destinatario = destinatario;
    }

    public String getDestinatario(){
        return destinatario;
    }

    @Override
    public boolean equals(Object o){
        if (!super.equals(o))
            return false; // verifica se os atributos herdados da superclassse sao iguais
        Oficio oficio = (Oficio) o;

        // verifica se os atributos especificos da subclasse sao iguais:
        return Objects.equals(destinatario, oficio.destinatario);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), destinatario);
    }
}
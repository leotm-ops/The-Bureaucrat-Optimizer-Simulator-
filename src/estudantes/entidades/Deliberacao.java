package estudantes.entidades;

import java.util.Objects;
import professor.entidades.CodigoCurso;

public abstract class Deliberacao extends DocumentoAdministrativo{
    private String texto;

    // construtor
    public Deliberacao(String criador, CodigoCurso codigoCurso, int paginas, String texto){
        super(criador, codigoCurso, paginas);
        this.texto = texto;
    }

    public String getTexto(){
        return texto;
    }

    @Override
    public boolean equals(Object o){
        if (!super.equals(o))
            return false; // verifica se os atributos herdados da superclassse sao iguais
        Deliberacao delib = (Deliberacao) o;

        // compara o conteudo das strings:
        return Objects.equals(texto, delib.texto); // verifica se os atributos específicos de Deliberacao sao iguais
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), texto); // repassa responsabilidade para a superclasse com atributo local
    }
}
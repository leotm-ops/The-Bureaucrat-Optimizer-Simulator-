package estudantes.entidades;

import java.util.Objects;
import professor.entidades.CodigoCurso;

public abstract class DocumentoAcademico extends Documento {
    private long autenticacao;

    // constutor
    public DocumentoAcademico(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao){
        super(criador, codigoCurso, paginas);
        this.autenticacao = autenticacao;
    }

    public long getAutenticacao() {
        return autenticacao;
    }

    @Override
    public boolean equals(Object o){
        if (!super.equals(o))
            return false; // verifica se os atributos herdados da superclassse são iguais
        DocumentoAcademico that = (DocumentoAcademico) o;

        return autenticacao == that.autenticacao; // verifica se os atributos específicos de DocumentoAcademivo são iguais
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), autenticacao);
    }
}
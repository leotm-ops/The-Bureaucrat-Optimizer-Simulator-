package estudantes.entidades;

import professor.entidades.CodigoCurso;

public abstract class DocumentoAdministrativo extends Documento {
    // construtor
    public DocumentoAdministrativo(String criador, CodigoCurso codigoCurso, int paginas){
        super(criador, codigoCurso, paginas);
    }

    @Override
    public boolean equals(Object o){
        return super.equals(o); // verifica se os atributos herdados da superclasse são iguais
    }

    @Override
    public int hashCode(){
        return super.hashCode();
    }
}

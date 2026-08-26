package estudantes.entidades;

import java.util.Objects;
import professor.entidades.CodigoCurso;s

public class Norma extends DocumentoAdministrativo{
    private int numero;
    private boolean valido;
    private String texto;

    // construtor:
    public Norma(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto){
        super(criador, codigoCurso, paginas);
        this.numero = numero;
        this.valido = valido;
        this.texto = texto;
    }

    public int getNumero(){
        return numero;
    }

    public boolean getValido(){
        return valido;
    }

    public String getTexto(){
        return texto;
    }

    @Override
    public boolean equals(Object o){
        if (!super.equals(o))
            return false; // verifica se os atributos herdados da superclassse sao iguais
        Norma norma = (Norma) o;

        // verifica se os atributos especificos da subclasse sao iguais:
        return numero == norma.numero && valido == norma.valido && Objects.equals(texto, norma.texto); // verifica se os atributos específicos de Deliberacao sao iguais
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), numero, valido, texto); // repassa a responsabilidade p a superclasse com atributos locais
    }

}
package estudantes.entidades;

import java.util.Arrays;
import java.util.Objects;
import professor.entidades.CodigoCurso;

public class Ata extends Documento{
    private int numero;
    private String texto;
    private String[] presentes;

    // construtor
    public Ata(String criador, CodigoCurso codigoCurso, int paginas, int numero, String texto, String[] presentes){
        super(criador, codigoCurso, paginas);
        this.numero = numero;
        this.texto = texto;
        this.presentes = presentes;
    }

    public int getNumero(){
        return numero;
    }

    public String getTexto(){
        return texto;
    }

    public String[] getPresentes(){
        return presentes;
    }

    @Override
    public boolean equals(Object o){
        if(!super.equals(o))
            return false; // verifica se os atributos herdados da superclasse são iguais

        Ata ata = (Ata) o; // verifica se os atributos específicos da ata são iguais

        return numero == ata.numero && Objects.equals(texto, ata.texto) && Arrays.equals(presentes, ata.presentes);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), numero, texto, Arrays.hashCode(presentes));
    }
}
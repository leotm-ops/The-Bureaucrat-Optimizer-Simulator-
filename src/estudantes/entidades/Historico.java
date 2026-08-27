package estudantes.entidades;

import java.util.Arrays;
import java.util.Objects;
import professor.entidades.CodigoCurso;

public class Historico extends Registro{
    private double coeficiente;
    private String[] componentes;

    //construtor
    public Historico(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String estudante, long matricula, double coeficiente, String[] componentes) {
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula);
        this.coeficiente = coeficiente;
        this.componentes = componentes;
    }

    public double getCoeficiente() {
        return coeficiente;
    }

    public String[] getComponentes() {
        return componentes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Historico)) return false;
        if (!super.equals(o)) return false;
        Historico historico = (Historico) o;
        return Double.compare(historico.coeficiente, coeficiente) == 0 && Arrays.equals(componentes, historico.componentes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), coeficiente, Arrays.hashCode(componentes));
    }
}
package basicFunction;

import java.util.List;

import static java.util.Objects.isNull;

public class ConsultaOrdinaria extends Consulta{
    private boolean activa;
    public ConsultaOrdinaria(String titulo, int numPreguntas) {
        super(titulo, numPreguntas);
        activa = false;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    @Override
    public boolean isActiva() {
        return activa;
    }

    @Override
    public boolean votar(int dni, List<Respuesta> listaRes) {
        if(isPreparada() && isActiva()
                &&  !isNull(electorAsociado(dni))
                && listaRes.size() == getNumPreguntas()){
            if (getVotos().isEmpty()){
                getVotos().put(dni, listaRes);
                return true;
            }else if (comprobarVoto(dni)){
                getVotos().remove(dni);
            }
            getVotos().put(dni, listaRes);
            return true;
        }
        return false;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "ConsultaOrdinaria[ " +
                "activa=" + activa +
                ']';
    }
}

package basicFunction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

public class ConsultaSelectiva extends Consulta{
    private LocalDate fechaUmbral;
    private Set<Integer> preguntasCondicionadas;
    private Set<Elector> censoAutorizado;
    private LocalDate fechaCelebracion;

    public ConsultaSelectiva(String titulo, int numPreguntas,
                             LocalDate fechaUmbral,
                             LocalDate fechaCelebracion) {
        super(titulo, numPreguntas);
        this.fechaUmbral = fechaUmbral;
        this.fechaCelebracion = fechaCelebracion;
        preguntasCondicionadas = new HashSet<>();
        censoAutorizado = new HashSet<>();
    }

    public LocalDate getFechaUmbral() {
        return fechaUmbral;
    }

    public Set<Integer> getPreguntasCondicionadas() {
        return preguntasCondicionadas;
    }

    public Set<Elector> getCensoAutorizado() {
        return censoAutorizado;
    }

    public LocalDate getFechaCelebracion() {
        return fechaCelebracion;
    }

    @Override
    public void añadirElectores(Elector... listaElectores) {
        for (Elector listaElec:listaElectores) {
            if (getFechaUmbral().isAfter(listaElec.getFecha())){
                censoAutorizado.add(listaElec);
            }
        }
    }

    @Override
    public boolean votar(int dni, List<Respuesta> listaRes) {
        for (Elector electores:getCenso()) {
            if (getFechaUmbral().isBefore(electores.getFecha())){
                listaRes.add(Respuesta.EN_BLANCO);
                getVotos().put(dni, listaRes);
                return false;
            }
            if(isPreparada() && isActiva()
                    &&  !isNull(electorAsociado(dni))
                    && listaRes.size() == getNumPreguntas()){
                if (getVotos().isEmpty()){
                    getVotos().put(dni, listaRes);
                    return true;
                }else if (!comprobarVoto(dni)){
                    getVotos().put(dni, listaRes);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean isActiva() {
        LocalDate fechaActual = LocalDate.now();
        return fechaCelebracion == fechaActual;
    }
    public boolean setFechaCelebracion(LocalDate fechaCelebracion) {
        if (fechaCelebracion.isAfter(this.fechaCelebracion)){
            this.fechaCelebracion = fechaCelebracion;
            return true;
        }
        return false;
    }

    public void addPreguntasCond(int... pregCond){
        for (Integer numeros : pregCond) {
            if (numeros >=0 || numeros <= getPreguntas().size()-1 ){
                preguntasCondicionadas.add(numeros);
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "ConsultaSelectiva[ " +
                "fechaUmbral=" + fechaUmbral +
                ", preguntasCondicionadas=" + preguntasCondicionadas +
                ", censoAutorizado=" + censoAutorizado +
                ", fechaCelebracion=" + fechaCelebracion +
                ']';
    }
}

package basicFunction;

import java.util.*;

import static java.util.Objects.isNull;

public abstract class Consulta implements Cloneable{
    private String titulo;
    private Set<Elector> censo;
    private int numPreguntas;
    private List<String> preguntas;
    private boolean preparada;
    private HashMap<Integer, List<Respuesta>> votos;
    private int numElectoresCenso;
    private int numVotosEmitidos;
    private float indexParticipacion;
    public Consulta(String titulo, int numPreguntas) {
        this.titulo = titulo;
        this.numPreguntas = numPreguntas;
        censo = new HashSet<>();
        preguntas = new ArrayList<>();
        votos = new HashMap<>();
    }

    public String getTitulo() {
        return titulo;
    }

    public Set<Elector> getCenso() {
        return censo;
    }

    public int getNumPreguntas() {
        return preguntas.size();
    }

    public List<String> getPreguntas() {
        return preguntas;
    }

    public boolean isPreparada() {
        return preguntas.size() == numPreguntas;
    }

    public HashMap<Integer, List<Respuesta>> getVotos() {
        return votos;
    }

    public int getNumElectoresCenso() {
        return censo.size();
    }

    public int getNumVotosEmitidos() {
        return votos.size();
    }

    public float getIndexParticipacion() {
        return numVotosEmitidos/numElectoresCenso;
    }
    public void añadirElectores(Elector... listaElectores){
        for (Elector listaElec:listaElectores) {
            censo.add(listaElec);
        }
    }
    public boolean añadirPregunta(int numero, String pregunta){

        if (preguntas.isEmpty()){
            preguntas.add(pregunta);
            return true;
        }
        if(numero >= 0 || numero <= numPreguntas-1){
            if(preguntas.contains(pregunta)){
                preguntas.set(numero, pregunta);
                return true;
            }
            preguntas.add(pregunta);
            return true;
        }
        return false;
    }
    public boolean deletePregunta(int numero){
        preguntas.remove(numero);
        return true;
    }
    public Elector electorAsociado(int dni){
        for (Elector elector : censo) {
            if (elector.getDni() == dni){
                return elector;
            }
        }
        return null;
    }
    public boolean comprobarCenso(int dni){
        Elector elector = electorAsociado(dni);
        return !isNull(elector);
    }
    public boolean comprobarVoto(int dni){
        return votos.containsKey(dni);
    }
    public HashMap<Respuesta, Integer> obtenerEscrutinio(int numPregunta){
        HashMap<Respuesta, Integer> escrutinio = new HashMap<>();
        int sies = 0, noes = 0, en_blanco = 0;
        for (List<Respuesta> resp : votos.values()) {
            if (numPregunta >= 0 && numPregunta <= resp.size()) {
                    if (Respuesta.NO == resp.get(numPregunta-1)) {
                        noes++;
                    }
                    if (Respuesta.SI == resp.get(numPregunta-1)) {
                        sies++;
                    }
                    if (Respuesta.EN_BLANCO == resp.get(numPregunta-1)) {
                        en_blanco++;
                    }

            }
            escrutinio.put(Respuesta.SI, sies);
            escrutinio.put(Respuesta.NO, noes);
            escrutinio.put(Respuesta.EN_BLANCO, en_blanco);
        }
        return escrutinio;
    }
    public abstract boolean isActiva();
    public boolean votar(int dni, List<Respuesta> listaRes){
        if(isPreparada() && isActiva()
                &&  !isNull(electorAsociado(dni))
                && listaRes.size() == numPreguntas){
            if (votos.isEmpty()){
                votos.put(dni, listaRes);
                return true;
            }else if (!comprobarVoto(dni)){
                votos.put(dni, listaRes);
                return true;
            }
            return false;
        }
        return false;
    }
    public boolean votar(int dni, Respuesta... listaRes){
        List<Respuesta> resp = new ArrayList<>();
        for (Respuesta res : listaRes) {
            resp.add(res);
        }
        for (Respuesta respuestas:listaRes) {
            if(isPreparada() && isActiva()
                    &&  !isNull(electorAsociado(dni))
                    && !comprobarVoto(dni)
                    && resp.size() == numPreguntas){
                if (votos.isEmpty()){
                    votos.put(dni, resp);
                    return true;
                }else if (!comprobarVoto(dni)){
                    votos.put(dni, resp);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    public boolean votar(int dni){
        List<Respuesta> resp = new ArrayList<>();
        for (String respu : preguntas) {
            resp.add(Respuesta.EN_BLANCO);
            if(isPreparada() && isActiva()
                    &&  !isNull(electorAsociado(dni))){
                if (votos.isEmpty()){
                    votos.put(dni, resp);
                    return true;
                }else if (!comprobarVoto(dni)){
                    votos.put(dni, resp);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "titulo='" + titulo + '\'' +
                ", numPreguntas=" + numPreguntas +
                '}';
    }
}

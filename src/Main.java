import basicFunction.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Elector elector1 = new Elector(17456789, "Juan Martínez", LocalDate.of(1998, 12, 16));
        Elector elector2 = new Elector(34567890, "Pedro López", LocalDate.of(1998, 3, 18));
        Elector elector3 = new Elector(23456812, "Ana Abenza", LocalDate.of(1995, 5, 1));
        Elector elector4 = new Elector(23754612, "María Gómez", LocalDate.of(1994, 9, 1));

        Consulta consulOrd = new ConsultaOrdinaria("Sobre los exámenes en la universidad", 2);
        consulOrd.añadirPregunta(1, "¿Debemos volver a la convocatoria de septiembre?");
        consulOrd.añadirPregunta(2, "¿Se deben hacer parciales en todas las cuatrimestrales?");

        ConsultaSelectiva consulSel = new ConsultaSelectiva("Sobre las fiestas patronales", 2,
                LocalDate.of(1997, 12, 31),
                LocalDate.now());
        consulSel.añadirPregunta(1, "¿Se debe cerrar el centro el día del patrón?");
        consulSel.añadirPregunta(2, "¿Se deben recuperar las clases que se pierden en las fiestas?");

        consulSel.addPreguntasCond(1);

        List<Consulta> listaConsulta = new ArrayList<>();
        listaConsulta.add(consulOrd);
        listaConsulta.add(consulSel);
        for (Consulta consultas : listaConsulta) {
            consultas.añadirElectores(elector1, elector2, elector3, elector4);
            if (consultas instanceof ConsultaOrdinaria){
                ConsultaOrdinaria cons = (ConsultaOrdinaria) consultas;
                cons.setActiva(true);
            }
            consultas.votar(17456789, Respuesta.SI, Respuesta.SI, Respuesta.SI);
            consultas.votar(34567890, Respuesta.EN_BLANCO, Respuesta.EN_BLANCO, Respuesta.EN_BLANCO);
            consultas.votar(23456812, Respuesta.SI, Respuesta.NO);
            consultas.votar(23754612, Respuesta.NO, Respuesta.NO, Respuesta.NO);

            System.out.println(consultas.getTitulo());
            if (consultas instanceof ConsultaSelectiva){
                ConsultaSelectiva cons = (ConsultaSelectiva) consultas;
                Set<Elector> censosAut = cons.getCensoAutorizado();
                for (Elector elec : censosAut) {
                    System.out.println("Electores " + elec);
                }
            }
            int numVotos = consultas.getNumVotosEmitidos();
            System.out.println("numero de votos "+ numVotos);

            HashMap<Respuesta, Integer> escrutin = consultas.obtenerEscrutinio(1);
            HashMap<Respuesta, Integer> escrutin2 = consultas.obtenerEscrutinio(2);
            escrutin.forEach((clave, valor) -> System.out.println(clave + " " + valor));
            escrutin2.forEach((clave, valor) -> System.out.println(clave + " " + valor));

        }
        List<Consulta> copias = new ArrayList<>();
        try {
            Consulta copia1 = (Consulta) consulSel.clone();
            Consulta copia2 = (Consulta) consulOrd.clone();

            copias.add(copia1);
            copias.add(copia2);

            for (Consulta consulta : copias) {
                System.out.println(consulta.toString());
            }

        }catch (Exception e){

        }



    }
}
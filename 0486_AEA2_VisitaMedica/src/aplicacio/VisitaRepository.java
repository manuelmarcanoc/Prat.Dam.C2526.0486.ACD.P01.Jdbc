package aplicacio;

import java.util.List;
import domini.VisitaMedica;

public interface VisitaRepository {
    boolean crearVisita(VisitaMedica visita);

    VisitaMedica obtenirVisita(int idVisita);

    List<VisitaMedica> obtenirTotesLesVisites();

    boolean actualitzarVisita(VisitaMedica visita);

    boolean esborrarVisita(int idVisita);
}

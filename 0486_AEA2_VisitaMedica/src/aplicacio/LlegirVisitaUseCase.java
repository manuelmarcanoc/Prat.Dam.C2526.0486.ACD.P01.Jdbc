package aplicacio;

import java.util.List;
import domini.VisitaMedica;

public class LlegirVisitaUseCase {
    private VisitaRepository repository;

    public LlegirVisitaUseCase(VisitaRepository repository) {
        this.repository = repository;
    }

    public List<VisitaMedica> obtenirTotesLesVisites() {
        return repository.obtenirTotesLesVisites();
    }

    public VisitaMedica obtenirVisitaPerId(int idVisita) {
        return repository.obtenirVisita(idVisita);
    }
}

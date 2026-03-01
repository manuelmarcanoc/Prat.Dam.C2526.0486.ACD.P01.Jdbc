package aplicacio;

import domini.VisitaMedica;

public class ActualitzarVisitaUseCase {
    private VisitaRepository repository;

    public ActualitzarVisitaUseCase(VisitaRepository repository) {
        this.repository = repository;
    }

    public boolean executar(VisitaMedica visita) {
        return repository.actualitzarVisita(visita);
    }
}

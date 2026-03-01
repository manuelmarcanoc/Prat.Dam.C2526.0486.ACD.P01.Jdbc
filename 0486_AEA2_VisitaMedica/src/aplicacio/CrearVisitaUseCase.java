package aplicacio;

import domini.VisitaMedica;

public class CrearVisitaUseCase {
    private VisitaRepository repository;

    public CrearVisitaUseCase(VisitaRepository repository) {
        this.repository = repository;
    }

    public boolean executar(VisitaMedica visita) {
        return repository.crearVisita(visita);
    }
}

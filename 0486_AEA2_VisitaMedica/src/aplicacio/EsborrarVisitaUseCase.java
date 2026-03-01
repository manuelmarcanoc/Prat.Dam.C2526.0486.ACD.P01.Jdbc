package aplicacio;

public class EsborrarVisitaUseCase {
    private VisitaRepository repository;

    public EsborrarVisitaUseCase(VisitaRepository repository) {
        this.repository = repository;
    }

    public boolean executar(int idVisita) {
        return repository.esborrarVisita(idVisita);
    }
}

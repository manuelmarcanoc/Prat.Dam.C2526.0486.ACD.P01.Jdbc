package inicio;

import aplicacio.ActualitzarVisitaUseCase;
import aplicacio.CrearVisitaUseCase;
import aplicacio.EsborrarVisitaUseCase;
import aplicacio.LlegirVisitaUseCase;
import aplicacio.VisitaRepository;
import infraestructura.VisitaRepositoryImpl;
import interficie.ConsolaUI;

public class Main {
    public static void main(String[] args) {
        // 1. Instanciem les dependències (Infraestructura)
        VisitaRepository repository = new VisitaRepositoryImpl();

        // 2. Instanciem els casos d'us (Aplicació)
        CrearVisitaUseCase crearUC = new CrearVisitaUseCase(repository);
        LlegirVisitaUseCase llegirUC = new LlegirVisitaUseCase(repository);
        ActualitzarVisitaUseCase actualitzarUC = new ActualitzarVisitaUseCase(repository);
        EsborrarVisitaUseCase esborrarUC = new EsborrarVisitaUseCase(repository);

        // 3. Instanciem la interfície d'usuari (Interficie)
        ConsolaUI ui = new ConsolaUI(crearUC, llegirUC, actualitzarUC, esborrarUC);

        // 4. Iniciem l'aplicació
        ui.iniciar();
    }
}

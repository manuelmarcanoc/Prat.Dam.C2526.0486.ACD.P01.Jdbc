package interficie;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import aplicacio.ActualitzarVisitaUseCase;
import aplicacio.CrearVisitaUseCase;
import aplicacio.EsborrarVisitaUseCase;
import aplicacio.LlegirVisitaUseCase;
import domini.VisitaMedica;

public class ConsolaUI {
    private CrearVisitaUseCase crearVisitaUseCase;
    private LlegirVisitaUseCase llegirVisitaUseCase;
    private ActualitzarVisitaUseCase actualitzarVisitaUseCase;
    private EsborrarVisitaUseCase esborrarVisitaUseCase;

    public ConsolaUI(CrearVisitaUseCase crear, LlegirVisitaUseCase llegir, ActualitzarVisitaUseCase actualitzar,
            EsborrarVisitaUseCase esborrar) {
        this.crearVisitaUseCase = crear;
        this.llegirVisitaUseCase = llegir;
        this.actualitzarVisitaUseCase = actualitzar;
        this.esborrarVisitaUseCase = esborrar;
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        int opcio;

        do {
            mostrarMenu();

            while (!scanner.hasNextInt()) {
                System.out.println("[X] Opción inválida. Vuelve a intentarlo.");
                System.out.print("> Elige una opción: ");
                scanner.next();
            }
            opcio = scanner.nextInt();
            scanner.nextLine();

            switch (opcio) {
                case 1:
                    llistarVisites();
                    break;
                case 2:
                    cercarVisita(scanner);
                    break;
                case 3:
                    crearVisita(scanner);
                    break;
                case 4:
                    actualitzarVisita(scanner);
                    break;
                case 5:
                    esborrarVisita(scanner);
                    break;
                case 0:
                    System.out.println("\n=============================================");
                    System.out.println("|| Saliendo del programa... ¡Hasta pronto! ||");
                    System.out.println("=============================================\n");
                    break;
                default:
                    System.out.println("[X] Opción inválida. Por favor, elige un número del 0 al 5.");
            }
        } while (opcio != 0);

        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("\n=============================================");
        System.out.println("||        GESTIÓN DE VISITAS MÉDICAS       ||");
        System.out.println("=============================================");
        System.out.println("||  1. Listar todas las visitas            ||");
        System.out.println("||  2. Buscar visita por ID                ||");
        System.out.println("||  3. Añadir nueva visita                 ||");
        System.out.println("||  4. Actualizar visita existente         ||");
        System.out.println("||  5. Borrar visita                       ||");
        System.out.println("||  0. Salir                               ||");
        System.out.println("=============================================");
        System.out.print("> Elige una opción: ");
    }

    private void llistarVisites() {
        System.out.println("\n---------------------------------------------");
        System.out.println("              LISTADO DE VISITAS");
        System.out.println("---------------------------------------------");
        List<VisitaMedica> visites = llegirVisitaUseCase.obtenirTotesLesVisites();
        if (visites.isEmpty()) {
            System.out.println("[!] No hay ninguna visita registrada en el sistema.");
        } else {
            for (VisitaMedica v : visites) {
                imprimirVisita(v);
            }
        }
        System.out.println("---------------------------------------------");
    }

    private void cercarVisita(Scanner scanner) {
        System.out.println("\n--- BUSCAR VISITA ---");
        System.out.print("> Introduce el ID de la visita: ");
        int id = llegirEnter(scanner);
        VisitaMedica visita = llegirVisitaUseCase.obtenirVisitaPerId(id);
        if (visita != null) {
            System.out.println("\n[OK] Visita encontrada: ");
            imprimirVisita(visita);
        } else {
            System.out.println("[X] No se ha encontrado ninguna visita con el ID: " + id);
        }
    }

    private void crearVisita(Scanner scanner) {
        System.out.println("\n--- AÑADIR NUEVA VISITA ---");
        System.out.print("Nombre del paciente: ");
        String nomPacient = scanner.nextLine();
        System.out.print("Nombre del médico: ");
        String nomMetge = scanner.nextLine();
        LocalDate data = llegirData(scanner);
        System.out.print("Diagnóstico: ");
        String diagnostic = scanner.nextLine();

        VisitaMedica novaVisita = new VisitaMedica(nomPacient, nomMetge, data, diagnostic);
        if (crearVisitaUseCase.executar(novaVisita)) {
            System.out.println("\n[OK] Visita añadida correctamente a la base de datos.");
        } else {
            System.out.println("\n[X] Error: No se ha podido añadir la visita.");
        }
    }

    private void actualitzarVisita(Scanner scanner) {
        System.out.println("\n--- ACTUALIZAR VISITA ---");
        System.out.print("> Introduce el ID de la visita a actualizar: ");
        int id = llegirEnter(scanner);

        VisitaMedica visita = llegirVisitaUseCase.obtenirVisitaPerId(id);
        if (visita == null) {
            System.out.println("[X] No se ha encontrado ninguna visita con el ID: " + id);
            return;
        }

        System.out.println("\nDatos actuales:");
        imprimirVisita(visita);
        System.out.println("---------------------------------------------");

        System.out.print("Nuevo nombre de paciente (pulsa ENTER para mantener '" + visita.getNomPacient() + "'): ");
        String nouPacient = scanner.nextLine();
        if (!nouPacient.trim().isEmpty())
            visita.setNomPacient(nouPacient);

        System.out.print("Nuevo nombre de médico (pulsa ENTER para mantener '" + visita.getNomMetge() + "'): ");
        String nouMetge = scanner.nextLine();
        if (!nouMetge.trim().isEmpty())
            visita.setNomMetge(nouMetge);

        System.out.print("Nueva fecha [AAAA-MM-DD] (pulsa ENTER para mantener '" + visita.getData() + "'): ");
        String novaDataStr = scanner.nextLine();
        if (!novaDataStr.trim().isEmpty()) {
            try {
                visita.setData(LocalDate.parse(novaDataStr, DateTimeFormatter.ISO_LOCAL_DATE));
            } catch (DateTimeParseException e) {
                System.out.println("[!] Formato de fecha incorrecto. Se mantiene la fecha anterior.");
            }
        }

        System.out.print("Nuevo diagnóstico (pulsa ENTER para mantener '" + visita.getDiagnostic() + "'): ");
        String nouDiag = scanner.nextLine();
        if (!nouDiag.trim().isEmpty())
            visita.setDiagnostic(nouDiag);

        if (actualitzarVisitaUseCase.executar(visita)) {
            System.out.println("\n[OK] Visita actualizada correctamente.");
        } else {
            System.out.println("\n[X] Error al actualizar la visita.");
        }
    }

    private void esborrarVisita(Scanner scanner) {
        System.out.println("\n--- BORRAR VISITA ---");
        System.out.print("> Introduce el ID de la visita a borrar: ");
        int id = llegirEnter(scanner);
        if (esborrarVisitaUseCase.executar(id)) {
            System.out.println("\n[OK] Visita [" + id + "] borrada correctamente.");
        } else {
            System.out.println("\n[X] Error: No se ha podido borrar. Comprueba que el ID existe.");
        }
    }

    private void imprimirVisita(VisitaMedica v) {
        System.out.println(String.format(" [ID: %04d] | Paciente: %-15s | Médico: %-15s | Fecha: %s | Diagnóstico: %s",
                v.getIdVisita(), v.getNomPacient(), v.getNomMetge(), v.getData().toString(), v.getDiagnostic()));
    }

    private int llegirEnter(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("[X] Formato incorrecto. Vuelve a introducir un número: ");
            scanner.next();
        }
        int num = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        return num;
    }

    private LocalDate llegirData(Scanner scanner) {
        LocalDate data = null;
        while (data == null) {
            System.out.print("Fecha (formato AAAA-MM-DD): ");
            String str = scanner.nextLine();
            try {
                data = LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("[X] Error: El formato debe ser AAAA-MM-DD (ejemplo: 2026-03-15)");
            }
        }
        return data;
    }
}

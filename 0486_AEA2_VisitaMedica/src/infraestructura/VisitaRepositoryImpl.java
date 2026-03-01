package infraestructura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import aplicacio.VisitaRepository;
import domini.VisitaMedica;

public class VisitaRepositoryImpl implements VisitaRepository {

    @Override
    public boolean crearVisita(VisitaMedica visita) {
        String query = "INSERT INTO VisitaMedica (nomPacient, nomMetge, data, diagnostic) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnexioBBDD.getConnexio();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, visita.getNomPacient());
            ps.setString(2, visita.getNomMetge());
            ps.setDate(3, Date.valueOf(visita.getData()));
            ps.setString(4, visita.getDiagnostic());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error creant la visita: " + e.getMessage());
            return false;
        }
    }

    @Override
    public VisitaMedica obtenirVisita(int idVisita) {
        String query = "SELECT * FROM VisitaMedica WHERE IdVisita = ?";
        VisitaMedica visita = null;
        try (Connection con = ConnexioBBDD.getConnexio();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idVisita);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                visita = new VisitaMedica(
                        rs.getInt("IdVisita"),
                        rs.getString("nomPacient"),
                        rs.getString("nomMetge"),
                        rs.getDate("data").toLocalDate(),
                        rs.getString("diagnostic"));
            }

        } catch (SQLException e) {
            System.err.println("Error llegint la visita: " + e.getMessage());
        }
        return visita;
    }

    @Override
    public List<VisitaMedica> obtenirTotesLesVisites() {
        String query = "SELECT * FROM VisitaMedica";
        List<VisitaMedica> visites = new ArrayList<>();
        try (Connection con = ConnexioBBDD.getConnexio();
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                VisitaMedica visita = new VisitaMedica(
                        rs.getInt("IdVisita"),
                        rs.getString("nomPacient"),
                        rs.getString("nomMetge"),
                        rs.getDate("data").toLocalDate(),
                        rs.getString("diagnostic"));
                visites.add(visita);
            }

        } catch (SQLException e) {
            System.err.println("Error llegint totes les visites: " + e.getMessage());
        }
        return visites;
    }

    @Override
    public boolean actualitzarVisita(VisitaMedica visita) {
        String query = "UPDATE VisitaMedica SET nomPacient = ?, nomMetge = ?, data = ?, diagnostic = ? WHERE IdVisita = ?";
        try (Connection con = ConnexioBBDD.getConnexio();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, visita.getNomPacient());
            ps.setString(2, visita.getNomMetge());
            ps.setDate(3, Date.valueOf(visita.getData()));
            ps.setString(4, visita.getDiagnostic());
            ps.setInt(5, visita.getIdVisita());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error actualitzant la visita: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean esborrarVisita(int idVisita) {
        String query = "DELETE FROM VisitaMedica WHERE IdVisita = ?";
        try (Connection con = ConnexioBBDD.getConnexio();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idVisita);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error esborrant la visita: " + e.getMessage());
            return false;
        }
    }
}

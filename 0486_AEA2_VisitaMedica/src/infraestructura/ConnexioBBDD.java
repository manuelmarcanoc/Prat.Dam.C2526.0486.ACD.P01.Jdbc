package infraestructura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexioBBDD {
    private static final String URL = "jdbc:mysql://localhost:3306/C2526_M0486_RA2";
    private static final String USUARI = "root";
    private static final String CONTRASENYA = "1234";

    public static Connection getConnexio() throws SQLException {
        return DriverManager.getConnection(URL, USUARI, CONTRASENYA);
    }
}

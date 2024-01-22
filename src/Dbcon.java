import java.sql.*;

public class Dbcon {
    private static final String URL = "jdbc:mysql://localhost:3306/vRental";
    private static final String USER = "root";
    private static final String PSW = "#Varun@2003&25";

    public static Connection getCon() throws SQLException {
        return DriverManager.getConnection(URL, USER, PSW);
    }
}

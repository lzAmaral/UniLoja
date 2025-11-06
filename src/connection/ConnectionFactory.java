package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=UniLoja;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "Robert.2024"; // mesma senha usada no Docker

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao conectar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Conexão bem-sucedida com o SQL Server!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


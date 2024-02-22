import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCutil {
    private JDBCutil() {
    }

    static {
        // Step1: loading and register the Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }
    }

    public static Connection getJdbcConnection() throws SQLException, IOException {

        // Take the data from properties file
        FileInputStream fis = new FileInputStream("D:\\Projects\\DataInsertionApp\\src\\Application\\application.properties");
        Properties properties = new Properties();
        properties.load(fis);

        // Step2. Establish the Connection
        Connection connection = DriverManager.getConnection(properties.getProperty("url"),
                properties.getProperty("user"), properties.getProperty("password"));
        System.out.println("connection object created...");
        return connection;
    }

    public static void cleanUp(Connection con, Statement statement, ResultSet resultSet) throws SQLException {
        // Step6. Close the resources
        if (con != null) {
            con.close();
        }

        if (statement != null) {
            statement.close();
        }
        if (resultSet != null) {
            resultSet.close();
        }
    }
}

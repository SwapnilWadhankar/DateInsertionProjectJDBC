import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CallableStatementsMySQL {
    private static final String storedProcedureCall = "{CALL newapp.get_IPLTeamsDetails_by_ID(?,?,?,?)}";
    public static void main(String[] args) {
        Connection connection = null;
        CallableStatement cstmt = null;
        Scanner scanner = null;
        int id =0;
        try {
            connection = JDBCutil.getJdbcConnection();
            if(connection != null){
                cstmt = connection.prepareCall(storedProcedureCall);
            }
            scanner = new Scanner(System.in);
            if (scanner != null) {
                    System.out.println("Please enter the value of user's ID ");
                    id = scanner.nextInt();
            }
            if(cstmt != null){
                cstmt.setInt(1,id);
            }
            if(cstmt !=null){
                cstmt.registerOutParameter(2, Types.VARCHAR);
                cstmt.registerOutParameter(3, Types.INTEGER);
                cstmt.registerOutParameter(4, Types.VARCHAR);
            }
            if(cstmt != null){
                cstmt.execute();
            }
            if(cstmt != null){
                System.out.println("The Name of the player is : "+cstmt.getString(2));
                System.out.println("The age of the player is : "+cstmt.getInt(3));
                System.out.println("The team of the player is : "+cstmt.getString(4));
            }
        }catch(IOException | SQLException ie) {
            ie.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCutil.cleanUp(connection, cstmt, null);

                System.out.println("Closing the resource...");
            } catch (SQLException e) {
                e.printStackTrace();
            } scanner.close();
        }

    }
}

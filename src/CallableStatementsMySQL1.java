import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class CallableStatementsMySQL1 {

    private static final String storedProcedureCall = "{CALL newapp.GET_IPL_TEAM_BY_NAME(?,?)}";
    public static void main(String[] args) {
        Connection connection = null;
        CallableStatement cstmt = null;
        ResultSet resultset = null;
        Scanner scanner = null;
        String player1 = null;
        String player2 = null;
        boolean flag = false;
        try {
            connection = JDBCutil.getJdbcConnection();
            if(connection != null){
                cstmt = connection.prepareCall(storedProcedureCall);
            }
            scanner = new Scanner(System.in);
            if (scanner != null) {
                System.out.println("Please enter the name of the player1:  ");
                player1 = scanner.next();
                System.out.println("Please enter the name of the player2:  ");
                player2 = scanner.next();
            }
            if (cstmt != null){
                cstmt.setString(1,player1);
                cstmt.setString(2,player2);
            }
            if(cstmt != null){
                cstmt.execute();
            }
            if(cstmt != null){
                resultset = cstmt.getResultSet();
            }
            if(cstmt != null){
                while(resultset.next()){
                    System.out.println("ID\tName\t\tage\tTeam");
                    System.out.println(resultset.getInt(1)+"\t"+resultset.getString(2)+"\t\t"+resultset.getInt(3)+"\t"+resultset.getString(4));
                    flag = true;
                }
            }
            if(flag){
                System.out.println("Records are available and displayed!");
            }else{
                System.out.println("Records unavailable for provided names!");
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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DateInsertionProject {

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        Scanner scanner = null;

        java.sql.Date sqlDob = null;
        java.sql.Date sqlDom = null;

        String name = null;
        String dob = null;
        String dom = null;
        int id = 0;

        try {
            connection = JDBCutil.getJdbcConnection();
            String sqlInsertQuery = "Insert into Users values(?,?,?,?)";
            if(connection != null){
                pstmt = connection.prepareStatement(sqlInsertQuery);
            }
            if(pstmt != null){
                scanner = new Scanner(System.in);
                if(scanner != null){
                    System.out.println("Please enter the value of user's ID ");
                    id = scanner.nextInt();
                    System.out.println("Please enter the name of the User : ");
                    name = scanner.next();
                    System.out.println("Please enter the DOB in MM-dd-yyyy format : ");
                    dob = scanner.next();
                    System.out.println("Please enter the DOM in yyyy-MM-dd format : ");
                    dom = scanner.next();
                }
                if(dob != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                    java.util.Date jul = sdf.parse(dob);
                    long value = jul.getTime();
                    sqlDob = new java.sql.Date(value);

                }
                if(dom != null){
                    sqlDom = java.sql.Date.valueOf(dom);
                }
                pstmt.setInt(1,id);
                pstmt.setString(2,name);
                pstmt.setDate(3,sqlDob);
                pstmt.setDate(4,sqlDom);

                //execute the query
                int rowCount = pstmt.executeUpdate();
                System.out.println("Rows updated :: "+rowCount);
            }

        }catch(IOException | SQLException ie) {
            ie.printStackTrace();
        }catch (ParseException pe){
            pe.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCutil.cleanUp(connection, pstmt, null);

                System.out.println("Closing the resource...");
            } catch (SQLException e) {
                e.printStackTrace();
            } scanner.close();
        }

    }
}


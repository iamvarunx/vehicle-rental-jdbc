import java.sql.*;
import java.util.Scanner;

public class signupDao {
    public static void userSignUp() throws SQLException {
        Scanner sc = ScannerClass.con();
        System.out.println();
        System.out.println("TO SIGNUP ");
        System.out.println();
        System.out.println("ENTER YOUR NAME: ");
        String name = sc.nextLine();
        System.out.println("ENTER YOUR EMAIL: ");
        String emailId = sc.nextLine();
        System.out.println("ENTER YOUR Mobile Number: ");
        String mobileNumber = sc.nextLine();
        System.out.println("ENTER YOUR NEW PASSWORD: ");
        String password = sc.nextLine();
        signupDao.signUp(name, emailId, password, mobileNumber);
    }

    public static void signUp(String name, String emailID, String password, String mobileNumber) throws SQLException {
        Connection con = Dbcon.getCon();
        String query = "INSERT INTO User_tbl (username,emailId,mobileNo,password,role_id)VALUES (?,?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, emailID);
        pst.setString(3, mobileNumber);
        pst.setString(4, password);
        pst.setInt(5, 2);
        pst.executeUpdate();
        String query1 = "Select User_id from User_tbl where emailId =? AND password =?";
        PreparedStatement pst1 = con.prepareStatement(query1);
            pst1.setString(1, emailID);
            pst1.setString(2, password);
            ResultSet rs;
            rs = pst1.executeQuery();
            rs.next();
            int UserId =rs.getInt(1);
            getterSetter.setName(name);
            getterSetter.setUserId(UserId);
        UserDao.userMenu();
    }
}

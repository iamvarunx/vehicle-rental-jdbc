import java.sql.*;
import java.util.Scanner;

public class loginDao {
    public static void signin() throws SQLException {
        Scanner sc = ScannerClass.con();
        System.out.println();
        System.out.println("TO LOGIN ");
        System.out.println();
        System.out.println("ENTER EMAIL: ");
        String emailId = sc.next();
        System.out.println("ENTER PASSWORD: ");
        String password = sc.next();
        loginDao.loginIn(emailId, password);
    }

    public static void loginIn(String emailId, String password) throws SQLException {
        String query = "Select role_id,username,User_id from User_tbl where emailId =? AND password =?";
        int Role = 0;
        int User_Id=0;
        String user_name = "";
        try {
            Connection con = Dbcon.getCon();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, emailId);
            pst.setString(2, password);
            ResultSet rs;
            rs = pst.executeQuery();
            rs.next();
            Role = rs.getInt(1);
            user_name = rs.getString(2);
            User_Id = rs.getInt(3);
        } catch (SQLException e) {
            System.out.println();
            System.out.println("INVALID EMAIL OR PASSWORD........!!");
            System.out.println();
            try {
                loginmenu.menu();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        if (Role == 1) {
            System.out.println();
            System.out.println("________Welcome " + user_name + "________");
            getterSetter.setName(user_name);
            getterSetter.setUserId(User_Id);
            AdminDao.adminMenu();
        } else {
            getterSetter.setName(user_name);
            getterSetter.setUserId(User_Id);
            UserDao.userMenu();
        }

    }
}

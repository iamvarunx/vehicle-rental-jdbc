
import java.sql.SQLException;

public class loginmenu extends App {

    public static void menu() throws SQLException {
        System.out.println("__________Vehicle Rental System__________");
        System.out.println("               1.LOGIN          ");
        System.out.println("               2.SIGNUP          ");
        System.out.println("               3.EXIT          ");
        System.out.println("ENTER CHOICE: ");
        int signChoice = sc.nextInt();
        if (signChoice == 1)
            loginDao.signin();
        else if (signChoice == 2)
            signupDao.userSignUp();
        else if (signChoice == 3)
            return;
        else {
            System.out.println("Please enter an valid Input ");
            System.out.println();
            loginmenu.menu();
        }
    }

}

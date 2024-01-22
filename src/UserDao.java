import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends App {

    public static void userMenu() throws SQLException {
        System.out.println();
        System.out.println("\n---------------Renting a Vehicle---------------");
        System.out.println("          1. View Available Vehicles");
        System.out.println("          2. Add Vehicle to Checkout Cart");
        System.out.println("          3. Remove Vehicle From Checkout Cart");
        System.out.println("          4. View Vehicle In YOUR Cart");
        System.out.println("          5. Complete Booking");
        System.out.println("          6. EXIT");
        System.out.print("\nEnter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                viewAvailableVehicles();
                break;
            case 2:
                addVehicleToCart();
                break;
            case 3:
                removeVehicleInCart();
                break;
            case 4:
                viewVehicleinCart();
                break;
            case 5:
                completeBooking();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
                userMenu();

        }

    }

    private static void viewAvailableVehicles() throws SQLException {
        System.out.println();
        System.out.println("\n-----------------Search By Category-----------------");
        System.out.println("                  1.To View Bikes");
        System.out.println("                  2.To View HatchBack Cars");
        System.out.println("                  3.To View SUV Cars");
        System.out.println("                  4.To View Sedan Cars");
        System.out.println("                  5.To View Jeep");
        System.out.println("                  6.Main Menu");
        System.out.println("\nEnter Your Choice:");
        int choice = sc.nextInt();
        String vehicleType = "";
        switch (choice) {
            case 1:
                vehicleType = "BIKE";
                break;
            case 2:
                vehicleType = "HATCHBACK";
                break;
            case 3:
                vehicleType = "SUV";
                break;
            case 4:
                vehicleType = "SEDAN";
                break;
            case 5:
                vehicleType = "JEEP";
                break;
            case 6:
                userMenu();
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
                viewAvailableVehicles();
        }
        try {
            Connection con = Dbcon.getCon();
            String Query = "Select vehicleName,vehicleBrand,numberPlate,vehicleType,No_of_passangers,last_service_date,distanceCovered,rentPerDay from vehicle_details join vehicle_modal using (vehicleId) Where vehicleType = ? AND isAvailable = ?;";
            PreparedStatement ps = con.prepareStatement(Query);
            ps.setString(1, vehicleType);
            ps.setString(2, "YES");
            ResultSet rs = ps.executeQuery();
            System.out.printf(
                    "--------------------------------------------------------------------------------------------------------------------------------------------%n");
            System.out.printf("| %-20s | %-15s | %-20s | %-12s| %-8s | %-20s| %-15s |%-15s|%n", "Name",
                    "Brand", "NumberPlate", "Type", "No.Seats", "Service_date",
                    "DistanceCovered",
                    "rentPerDay");
            System.out.printf(
                    "--------------------------------------------------------------------------------------------------------------------------------------------%n");
            while (rs.next()) {
                System.out.printf("| %-20s | %-15s | %-20s | %-12s |%-8d | %-20s | %-15d |%-15d|%n", rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7),
                        rs.getInt(8));
            }
            System.out.printf(
                    "-------------------------------------------------------------------------------------------------------------------------------------------%n");
            viewAvailableVehicles();
        } catch (Exception e) {
            System.out.println("\n--------Something Went Wrong Re_try.!!------");
            viewAvailableVehicles();
        }

    }

    private static void addVehicleToCart() throws SQLException {
        Connection con = Dbcon.getCon();
        try {
            String queryTocheckCart = "Select User_id from cartTbl where User_id =?;";
            PreparedStatement psCheck = con.prepareStatement(queryTocheckCart);
            psCheck.setInt(1, getterSetter.getUserId());
            ResultSet rsCheck = psCheck.executeQuery();
            int userAlreadyExist = 0;
            if (rsCheck.next())
                userAlreadyExist = rsCheck.getInt(1);
            if (userAlreadyExist != 0) {
                System.out.println("Your Cart Is Full.....Please Proceed to next process OR Remove vehicle from Cart");
                userMenu();
            } else {
                System.out.println();
                System.out.println("/nEnter Vehicle Register Number to Add to CART: ");
                String vReg = sc.nextLine();
                try {
                    String query = "Select isAvailable from vehicle_details where numberPlate = ?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, vReg);
                    ResultSet rs = ps.executeQuery();
                    String isAvailable = "";
                    if (rs.next())
                        isAvailable = rs.getString(1);
                    if (isAvailable.equals("YES")) {
                        String query1 = "Insert into cartTbl values(?,?)";
                        PreparedStatement pst = con.prepareStatement(query1);
                        pst.setInt(1, getterSetter.getUserId());
                        pst.setString(2, vReg);
                        pst.executeUpdate();
                    } else {
                        System.out.println("\n");
                        System.out.println("   The Entered Vehicle is Not Available...");
                        System.out.println("\nPlease Try Another Vehicle");

                    }
                } catch (Exception e) {
                    System.out.println("\nSomething Went Wrong-----------");
                    System.out.println("\nPlease Try Agian");
                    addVehicleToCart();
                }
                userMenu();
            }
        } catch (Exception e) {
            System.out.println("\nSomething Went Wrong-----------");
            System.out.println("\nPlease Try Agian");
            userMenu();
        }

    }

    private static void removeVehicleInCart() throws SQLException {
        Connection con = Dbcon.getCon();
        try {
            String queryTocheckCart = "Select User_id from cartTbl where User_id =?;";
            PreparedStatement psCheck = con.prepareStatement(queryTocheckCart);
            psCheck.setInt(1, getterSetter.getUserId());
            ResultSet rsCheck = psCheck.executeQuery();
            int userAlreadyExist = 0;
            if (rsCheck.next())
                userAlreadyExist = rsCheck.getInt(1);
            if (userAlreadyExist != 0) {
                String query1 = "delete from cartTbl where User_id = ?";
                PreparedStatement pst = con.prepareStatement(query1);
                pst.setInt(1, getterSetter.getUserId());
                pst.executeUpdate();
                System.out.println("Vehicle from Cart is Successfully Removed");
                userMenu();
            } else {
                System.out.println("\nYOUR CART IS EMPTY PLEASE ADD VEHICLE TO YOUR CART < <");
                userMenu();
            }
        } catch (Exception e) {
            System.out.println("\nSomething Went Wrong-----------");
            System.out.println("\nPlease Try Agian");
            userMenu();
        }

    }

    private static void viewVehicleinCart() throws SQLException {
        Connection con = Dbcon.getCon();
        try {
            String queryTocheckCart = "Select inCart from cartTbl where User_id =?;";
            PreparedStatement psCheck = con.prepareStatement(queryTocheckCart);
            psCheck.setInt(1, getterSetter.getUserId());
            ResultSet rsCheck = psCheck.executeQuery();
            String inCart = "";
            if (rsCheck.next()) {
                inCart = rsCheck.getString(1);
                String Query = "Select vehicleName,vehicleBrand,numberPlate,vehicleType,No_of_passangers,last_service_date,distanceCovered,rentPerDay from vehicle_details join vehicle_modal using (vehicleId) Where numberPlate =?;";
                PreparedStatement ps = con.prepareStatement(Query);
                ps.setString(1, inCart);
                ResultSet rs = ps.executeQuery();
                System.out.printf(
                        "--------------------------------------------------------------------------------------------------------------------------------------------%n");
                System.out.printf("| %-20s | %-15s | %-20s | %-12s| %-8s | %-20s| %-15s |%-15s|%n", "Name",
                        "Brand", "NumberPlate", "Type", "No.Seats", "Service_date",
                        "DistanceCovered",
                        "rentPerDay");
                System.out.printf(
                        "--------------------------------------------------------------------------------------------------------------------------------------------%n");
                while (rs.next()) {
                    System.out.printf("| %-20s | %-15s | %-20s | %-12s |%-8d | %-20s | %-15d |%-15d|%n",
                            rs.getString(1),
                            rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6),
                            rs.getInt(7),
                            rs.getInt(8));
                }
                System.out.printf(
                        "-------------------------------------------------------------------------------------------------------------------------------------------%n");
                userMenu();
            } else {
                System.out.println("\nYOUR CART IS EMPTY PLEASE ADD VEHICLE TO YOUR CART < <");
                userMenu();
            }
        } catch (Exception e) {
            System.out.println("\nSomething Went Wrong-----------");
            System.out.println("\nPlease Try Agian");
            userMenu();
        }
    }

    private static void completeBooking() throws SQLException {
        Connection con = Dbcon.getCon();
        try {
            String queryTocheckCart = "Select inCart from cartTbl where User_id =?;";
            PreparedStatement psCheck = con.prepareStatement(queryTocheckCart);
            psCheck.setInt(1, getterSetter.getUserId());
            ResultSet rsCheck = psCheck.executeQuery();
            String inCart = "";
            if (rsCheck.next()) {
                inCart = rsCheck.getString(1);
                String Query = "Select vehicleName,rentPerDay from vehicle_details join vehicle_modal using (vehicleId) Where numberPlate =?;";
                PreparedStatement ps = con.prepareStatement(Query);
                ps.setString(1, inCart);
                ResultSet rs = ps.executeQuery();
                rs.next();
                String vName = rs.getString(1);
                int rentPerDay = rs.getInt(2);
                bookingFinal(inCart, vName, rentPerDay);
            } else {
                System.out
                        .println("\nYOUR CART IS EMPTY PLEASE ADD VEHICLE TO YOUR CART ... BEFORE PROCEEDING BOOKING");
                userMenu();
            }
        } catch (Exception e) {
            System.out.println("\nSomething Went Wrong-----------");
            System.out.println("\nPlease Try Agian");
            userMenu();
        }
    }

    public static void bookingFinal(String vreg, String vname, int rentperday) throws SQLException {
        System.out.println("\n         BOOKING OF THE VEHICLE " + vname);
        System.out.println("\n           1.To PROCEED BOOKING");
        System.out.println("\n           2.To Main MENU");
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice != 1)
            userMenu();
        else {
            System.out.println("Enter The Total Number Of Days Of Renting The Vehicle : ");
            int tDays = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter The Renting Date (yyyy-mm-dd) : ");
            String rentingDate = sc.nextLine();
            System.out.println("Enter The Returning Date (yyyy-mm-dd) : ");
            String returningDate = sc.nextLine();
            System.out.println("The Cost Per Day For Renting The Vehicle is : Rs." + rentperday);
            System.out.println(
                    "The Total Amount That Should Be Paid After Returning Vehicle is : Rs." + rentperday * tDays);
            System.out.println();
            System.out.print("\nEach Borrower has deposited 30000 rupees Initially into the Rent Service as a Caution" +
            "\ndeposit . The amount will be refunded on returning the vehicle. The Amount will be" +
            "\n reduced if there is any damage or loss of vehicle..... ");
            System.out.println();

            String paymentID = paymentProcess();
            System.out.println(paymentID);

            if (!paymentID.equals("")) {
                Connection con = Dbcon.getCon();
                String query = "Insert into Booking (numberPlate, totalDays, rentingDate, returningDate, totalRent, User_id, Payment_ID) values(?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, vreg);
                pst.setInt(2, tDays);
                pst.setString(3, rentingDate);
                pst.setString(4, returningDate);
                pst.setInt(5, rentperday * tDays);
                pst.setInt(6, getterSetter.getUserId());
                pst.setString(7, paymentID);
                pst.executeUpdate();
                String UpdateisAvailable = "Update vehicle_details Set isAvailable = ? where numberPlate = ?";
                PreparedStatement pst1 = con.prepareStatement(UpdateisAvailable);
                pst1.setString(1, "NO");
                pst1.setString(2, vreg);
                pst1.executeUpdate();
                // removeVehicleInCart();
                System.out.println("Booking is SuccessFull.........!!!!!!!!!");
                userMenu();
            } else {
                System.out.println("PayMent Faild please try Again..!!");
                bookingFinal(vreg, vname, rentperday);
            }

        }

    }

    private static String paymentProcess() throws SQLException {
        System.out.println("\n              Payment Methods  ");
        System.out.println("\n          1.UPI PayMent");
        System.out.println("\n          2.CREDIT/DEBIT CARD PayMent");
        System.out.println("\n          3.Offline PayMent");
        System.out.println("Enter Your Choice: ");
        int Choice = sc.nextInt();
        sc.nextLine();
        String payMentMode = "";
        if (Choice == 1)
            payMentMode = "UPI";
        else if (Choice == 2)
            payMentMode = "CARD";
        else if (Choice == 4)
            payMentMode = "Offline";
        System.out.println("Your Mode Of PayMent " + payMentMode);
        System.out.println("Enter The Amount (Rs.30000): ");
        int amount = sc.nextInt();
        sc.nextLine();
        System.out.println("Press (Y) to Complete the payMent...");
        String yes = sc.nextLine();
        if (!yes.equals("Y"))
            return "";
        System.out.println("Enter the PayMent Referance ID");
        String paymentId = sc.nextLine();
        Connection con = Dbcon.getCon();
        try {
            String query = "Insert into paymentDetails values(?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, payMentMode);
            pst.setString(2, paymentId);
            pst.setInt(3, amount);
            pst.executeUpdate();
            System.out.println("The PayMent is Complete.......");
            return paymentId;
        } catch (Exception e) {
            System.out.println("\nSomething Went Wrong-----------");
            System.out.println("\nPlease Try Agian");
            completeBooking();
        }
        return "";
    }
}


import java.sql.*;

public class AdminDao extends App {
    public static void adminMenu() throws SQLException {
        System.out.println("\nVehicle Inventory Management");
        System.out.println("        1. Add a Vehicle");
        System.out.println("        2. Modify Vehicle Specifications");
        System.out.println("        3. Delete a Vehicle");
        System.out.println("        4. View List of All Vehicles");
        System.out.println("        5. Search for a Vehicle");
        System.out.println("        6. Exit");
        System.out.println("\nEnter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                addVehicle();
                break;
            case 2:
                modifyVehicleSpecifications();
                break;
            case 3:
                deleteVehicle();
                break;
            case 4:
                viewAllVehicles();
                break;
            case 5:
                searchVehicle();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
                adminMenu();
        }
    }

    private static void addVehicle() throws SQLException {

        System.out.println("\nEnter Vehicle Name:");
        String vname = sc.nextLine().toUpperCase();
        System.out.print("\nEnter Vehicle brand:");
        String vbrand = sc.nextLine().toUpperCase();
        System.out.println("\nEnter Vehicle Type:");
        String vType = sc.nextLine().toUpperCase();
        System.out.println("\nEnter Number of Passangers:");
        int vPassanger = sc.nextInt();
        sc.nextLine();
        System.out.println("\nEnter Vehicle Number Plate No. :");
        String vNumberPlate = sc.nextLine().toUpperCase();
        System.out.println("\nEnter Last Service Date (yyyy-mm-dd):");
        String vserviceDate = sc.nextLine();
        System.out.println("\nEnter Total Distance Covered:");
        int distacneCovered = sc.nextInt();
        System.out.println("\nEnter Vehciles Rent Per DAY:");
        int vrent = sc.nextInt();
        sc.nextLine();
        System.out.println("\nEnter Whether the vehicle isAvailable (YES/NO)");
        String isAvailable = sc.nextLine();
        Connection con = Dbcon.getCon();

        try {
            String queryTocheck = "Select vehicleId from vehicle_modal where vehicleName = ? And vehicleBrand =?;";

            PreparedStatement rs = con.prepareStatement(queryTocheck);
            rs.setString(1, vname);
            rs.setString(2, vbrand);
            ResultSet result = rs.executeQuery();
            result.next();
            int vehicleId = result.getInt(1);
            String query = "Insert into vehicle_details values(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, vehicleId);
            pst.setString(2, vNumberPlate);
            pst.setString(3, vserviceDate);
            pst.setInt(4, distacneCovered);
            pst.setString(5, isAvailable);
            pst.executeUpdate();

        } catch (Exception e) {
            try {
                String query1 = "Insert into vehicle_modal (vehicleName,vehicleBrand,vehicleType,rentPerDay,No_of_passangers)values(?,?,?,?,?)";
                PreparedStatement p1 = con.prepareStatement(query1);
                p1.setString(1, vname);
                p1.setString(2, vbrand);
                p1.setString(3, vType);
                p1.setInt(4, vrent);
                p1.setInt(5, vPassanger);
                p1.executeUpdate();

                String queryTocheck = "Select vehicleId from vehicle_modal where vehicleName = ? And vehicleBrand =?;";

                PreparedStatement rs = con.prepareStatement(queryTocheck);
                rs.setString(1, vname);
                rs.setString(2, vbrand);
                ResultSet result = rs.executeQuery();
                result.next();
                int vehicleId = result.getInt(1);

                String query = "Insert into vehicle_details values(?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, vehicleId);
                pst.setString(2, vNumberPlate);
                pst.setString(3, vserviceDate);
                pst.setInt(4, distacneCovered);
                pst.setString(5, isAvailable);
                pst.executeUpdate();
            } catch (Exception e1) {
                System.out.println("\n-------Re-Enter Data Something Went wrong !!!!-------");
                addVehicle();
            }

        }
        System.out.println("\n------Vehicle Data ADDED Successfully..!!------");
        adminMenu();

    }

    private static void modifyVehicleSpecifications() throws SQLException {
        Connection con = Dbcon.getCon();
        System.out.println("______________________________________________________");
        System.out.println("         1.To Change Deposit Amount");
        System.out.println("         2.To Change Rent of an vehicle");
        System.out.println("         3.To Change Last Service Date");
        System.out.println("         4.To Main Menu");
        System.out.println("Enter Your choice: ");
        int Choice = sc.nextInt();
        sc.nextLine();

        if (Choice == 1) {

            try {
                System.out.print("Enter Vehicle Name : ");
                String vname = sc.nextLine().toUpperCase();
                System.out.println("Enter the security deposit: ");
                int changeRent = sc.nextInt();
                String Upquery = "update vehicle_modal set rentPerDay = ? where vehicleName = ?";
                PreparedStatement pst = con.prepareStatement(Upquery);
                pst.setInt(1, changeRent);
                pst.setString(2, vname);
                pst.executeUpdate();
                System.out.println("UPDATED SUCCESSFULLY--------------------");

            } catch (Exception e) {
                System.out.println("ENTERED VEHICLE IS NOT EXIST--------------------");
                System.out.println("PLEASE ENTER THE PROPER VEHICLE NAME-------------------");
                modifyVehicleSpecifications();
            }
        } else if (Choice == 2) {
            try {
                System.out.print("Enter Vehicle Number plate : ");
                String plateNo = sc.nextLine().toUpperCase();
                System.out.print("Enter Distance Covered of the vehicle: ");
                int changeDistanceCovered = sc.nextInt();
                String UpQuery2 = "Update vehicle_details Set distanceCovered = ? where numberPlate = ? ;";
                PreparedStatement pst = con.prepareStatement(UpQuery2);
                pst.setInt(1, changeDistanceCovered);
                pst.setString(2, plateNo);
                pst.executeUpdate();
                System.out.println();
                System.out.println("\nUPDATED SUCCESSFULLY--------------------");
            } catch (Exception e) {
                System.out.println("\nSomething Went Wrong-----------");
                System.out.println("\nPlease Enter Valid Number");
                modifyVehicleSpecifications();
            }

        } else if (Choice == 3) {
            try {
                System.out.print("Enter Vehicle Number plate : ");
                String plateNo = sc.nextLine().toUpperCase();
                System.out.println("Enter the Recent Service Date (yyyy-mm-dd): ");
                String changeServiceDate = sc.nextLine();
                String UpQuery3 = "Update vehicle_details Set last_service_date = ? where numberPlate = ?";
                PreparedStatement pst = con.prepareStatement(UpQuery3);
                pst.setString(1, changeServiceDate);
                pst.setString(2, plateNo);
                pst.executeUpdate();
                System.out.println();
                System.out.println("\nUPDATED SUCCESSFULLY--------------------");
            } catch (Exception e) {
                System.out.println("\nSomething Went Wrong-----------");
                System.out.println("\nPlease Enter Valid Number");
                modifyVehicleSpecifications();
            }
        } else if (Choice == 4) {
            adminMenu();
        } else {
            System.out.println("Invalid Choice Retry !!");
            modifyVehicleSpecifications();
        }
        modifyVehicleSpecifications();
    }

    private static void deleteVehicle() throws SQLException {
        try {
            System.out.println("\nEnter Vehicle Number which is to Removed from Data: ");
            String vplateNumber = sc.nextLine().toUpperCase().trim();
            Connection con = Dbcon.getCon();
            String delQuery = "delete from vehicle_details where numberPlate = ?;";
            PreparedStatement pst = con.prepareStatement(delQuery);
            pst.setString(1, vplateNumber);
            pst.executeUpdate();
            System.out.println("\nDeleted Successfully..!!");
        } catch (Exception e) {
            System.out.println("\nSomething went wrong.... Enter proper Vehicle Number");
            deleteVehicle();
        }

    }

    private static void viewAllVehicles() throws SQLException {
        Connection con = Dbcon.getCon();
        String query = "Select vehicleName,vehicleBrand,numberPlate,vehicleType,No_of_passangers,last_service_date,distanceCovered,rentPerDay from vehicle_details join vehicle_modal using (vehicleId) ORDER BY vehicleName ASC;";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.printf(
                "-----------------------------------------------------------------------------------------------------------------------------------------%n");
        System.out.printf(
                "                                                                     VEHICLE INVENTORY          %n");
        System.out.printf("          %n");
        System.out.printf(
                "-----------------------------------------------------------------------------------------------------------------------------------------%n");
        System.out.printf(
                "-----------------------------------------------------------------------------------------------------------------------------------------%n");
        System.out.printf("| %-20s | %-15s | %-20s | %-12s| %-8s | %-15s| %-15s |%-15s |%n", "Name",
                "Brand", "NumberPlate", "Type", "No.Seats", "Service_date", "DistanceCovered",
                "RentPerDay");
        System.out.printf(
                "------------------------------------------------------------------------------------------------------------------------------------------%n");
        while (rs.next()) {

            System.out.printf("| %-20s | %-15s | %-20s | %-12s |%-8d | %-15s | %-15d |%-15d |%n", rs.getString(1),
                    rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7),
                    rs.getInt(8));

        }
        System.out.printf(
                "------------------------------------------------------------------------------------------------------------------------------------------%n");
        adminMenu();
    }

    private static void searchVehicle() throws SQLException {
        try {
            System.out.println("                  1.To Search By Vehicle Name");
            System.out.println("                  2.To Search By Vehicle Number Plate");
            System.out.println("Enter Your Choice: ");
            int Choice = sc.nextInt();
            sc.nextLine();
            Connection con = Dbcon.getCon();
            String query = "";
            String Searchby = "";
            if (Choice == 1) {
                System.out.println("Enter Vehicle Name");
                Searchby = sc.nextLine().toUpperCase();
                query = "Select vehicleName,vehicleBrand,numberPlate,vehicleType,No_of_passangers,last_service_date,distanceCovered,rentPerDay from vehicle_details join vehicle_modal using (vehicleId) Where vehicleName = ?;";
            } else if (Choice == 2) {
                System.out.println("Enter Vehicle Number Plate No. :");
                Searchby = sc.nextLine().toUpperCase();
                query = "Select vehicleName,vehicleBrand,numberPlate,vehicleType,No_of_passangers,last_service_date,distanceCovered,rentPerDay from vehicle_details join vehicle_modal using (vehicleId) Where numberPlate = ?;";
            } else {
                System.out.println("Something Went Wrong...Try Again!!");
                searchVehicle();
            }
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, Searchby);
            ResultSet rs = ps.executeQuery();
            System.out.printf(
                    "--------------------------------------------------------------------------------------------------------------------------------------------%n");
            System.out.printf("| %-20s | %-15s | %-20s | %-12s| %-8s | %-20s| %-15s |%-15s |%n", "Name",
                    "Brand", "NumberPlate", "Type", "No.Seats", "Service_date",
                    "DistanceCovered",
                    "rentPerDay");
            System.out.printf(
                    "--------------------------------------------------------------------------------------------------------------------------------------------%n");
            while (rs.next()) {

                System.out.printf("| %-20s | %-15s | %-20s | %-12s |%-8d | %-20s | %-15d |%-15d |%n", rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7),
                        rs.getInt(8));

            }
            System.out.printf(
                    "-------------------------------------------------------------------------------------------------------------------------------------------%n");
        } catch (Exception e) {
            System.out.println("\n--------Something Went Wrong Re_try.!!------");
            searchVehicle();
        }

        adminMenu();
    }
}

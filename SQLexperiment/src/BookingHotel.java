import org.omg.Messaging.SyncScopeHelper;

import java.sql.*;
import java.util.Scanner;

/**
 * @BelongsProject:SQLexperiment
 * @BelongsPackage:PACKAGE_NAME
 * @Author:Uestc_Xiye
 * @CreateTime:2020-12-08 12:37:11
 */
public class BookingHotel {
    public static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/dbexperiment?&useSSL=false&serverTimezone=UTC";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "12138";

    public static void main(String[] args) throws Exception {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }

        Scanner scanner=new Scanner(System.in);
        String StartDate=scanner.next();
        String EndDate=scanner.next();
        int RoomStyle=scanner.nextInt();
        int RoomNum=scanner.nextInt();
        String CreateDate=scanner.next();
        int CustomID=scanner.nextInt();
/*
        String StartDate="2020-11-14";
        String EndDate="2020-11-15";
        int RoomStyle=4;
        int RoomNum=4;
        String CreateDate="2020-12-08";
        int CustomID=201901;
*/
        try {
            Connection conn= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);

            PreparedStatement pst = conn.prepareStatement("INSERT INTO `order` VALUES (null,?,?,?,?,?*(SELECT SUM(price) FROM room_info WHERE room_id=? AND date BETWEEN ? AND ?),?,?);");
            pst.setInt(1,RoomStyle);
            pst.setString(2,StartDate);
            pst.setString(3,EndDate);
            pst.setInt(4,RoomNum);
            pst.setInt(5,RoomNum);
            pst.setInt(6,RoomStyle);
            pst.setString(7,StartDate);
            pst.setString(8,EndDate);
            pst.setString(9,CreateDate);
            pst.setInt(10,CustomID);

            pst.execute();

            PreparedStatement p = conn.prepareStatement("UPDATE room_info SET remain=remain-? WHERE room_id=? AND date BETWEEN ? AND ?;");
            p.setInt(1,RoomNum);
            p.setInt(2,RoomStyle);
            p.setString(3,StartDate);
            p.setString(4,EndDate);

            p.execute();

            pst.close();
            p.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

}

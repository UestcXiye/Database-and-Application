import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @BelongsProject:SQLexperiment
 * @BelongsPackage:PACKAGE_NAME
 * @Author:Uestc_Xiye
 * @CreateTime:2020-12-08 15:13:50
 */
public class ResetRoomInfo {
    public static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/dbexperiment?&useSSL=false&serverTimezone=UTC";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "12138";

    public static void main(String[] args) {
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
/*
        String StartDate="2020-11-14";
        String EndDate="2020-11-15";
        int RoomStyle=4;
        int RoomNum=4;
*/
        try {
            Connection conn= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);

            PreparedStatement p = conn.prepareStatement("UPDATE room_info SET remain=remain+? WHERE room_id=? AND date BETWEEN ? AND ?;");
            p.setInt(1,RoomNum);
            p.setInt(2,RoomStyle);
            p.setString(3,StartDate);
            p.setString(4,EndDate);

            p.execute();

            p.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

}

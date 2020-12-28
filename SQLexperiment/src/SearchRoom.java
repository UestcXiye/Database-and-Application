import sun.applet.Main;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @BelongsProject:SQLexperiment
 * @BelongsPackage:PACKAGE_NAME
 * @Author:Uestc_Xiye
 * @CreateTime:2020-12-07 19:56:16
 */
public class SearchRoom {
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
        String BeginDate=scanner.next();
        String EndDate=scanner.next();
        int RoomNum=scanner.nextInt();
/*
        String BeginDate="2020-11-14";
        String EndDate="2020-11-16";
        int RoomNum=4;
*/
        try {
            Connection conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
/*
            String procedureSQL="CREATE PROCEDURE `SearchRoom`(IN `BeginDate` date,IN `EndDate` date,IN `RoomNum` int)\n" +
                    "BEGIN" +
                    "\tSELECT hotel_name AS HotelName,room_name AS RoomName,AVG(price) AS AveragePrice\n" +
                    "\tFROM hotel NATURAL JOIN room_type NATURAL JOIN room_info\n" +
                    "\tWHERE date<=EndDate AND date>=BeginDate GROUP BY room_id HAVING min(remain)>=RoomNum ORDER BY AVG(price);\n" +
                    "END";
            PreparedStatement ps=conn.prepareStatement(procedureSQL);
            ps.execute();
*/
            CallableStatement cs=conn.prepareCall("{CALL SearchRoom(?,?,?)}");
            cs.setString(1,BeginDate);
            cs.setString(2,EndDate);
            cs.setInt(3,RoomNum);
            boolean hasResult=cs.execute();
            if(hasResult)
            {
                ResultSet rst=cs.getResultSet();
                while (rst.next()) {
                    System.out.println(
                            rst.getString(1) + "\t"
                            + rst.getString(2) + "\t"
                            + rst.getString(3)
                    );
                }
            }
            //ps.close();
            cs.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        }
}
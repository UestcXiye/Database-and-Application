import java.sql.*;

/**
 * @BelongsProject:SQLexperiment
 * @BelongsPackage:PACKAGE_NAME
 * @Author:Uestc_Xiye
 * @CreateTime:2020-12-08 14:34:15
 */
public class ShowRoomInfo {
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

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT * FROM room_info";
            Statement s = conn.createStatement();
            ResultSet rst = s.executeQuery(sql);
            while (rst.next()) {
                System.out.println(
                        rst.getString(1) + "\t"
                                + rst.getString(2) + "\t"
                                + rst.getString(3) + "\t"
                                + rst.getString(4) + "\t"
                                + rst.getString(5)
                );
            }

            s.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}

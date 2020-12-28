import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @BelongsProject:SQLexperiment
 * @BelongsPackage:PACKAGE_NAME
 * @Author:Uestc_Xiye
 * @CreateTime:2020-12-08 14:27:36
 */
public class DeleteOrder {
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
            String sql = "DELETE FROM `order` WHERE order_id>27";
            Statement s = conn.createStatement();
            s.execute(sql);

            s.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}

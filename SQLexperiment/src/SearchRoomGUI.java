import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * @BelongsProject:SQLexperiment
 * @BelongsPackage:PACKAGE_NAME
 * @Author:Uestc_Xiye
 * @CreateTime:2020-12-08 17:03:54
 */
public class SearchRoomGUI {
    public static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/dbexperiment?&useSSL=false&serverTimezone=UTC";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "12138";

    public static void main(String[] args) {
        JFrame f=new JFrame("SearchRoomGUI");
        f.setSize(400,250);

        JPanel pInput = new JPanel();
        pInput.setBounds(10, 10, 375, 120);
        pInput.setLayout(new GridLayout(3,2,10, 10));

        JLabel BeginDate = new JLabel("BeginDate:");
        JTextField BeginDateText = new JTextField();
        JLabel EndDate = new JLabel("EndDate:");
        JTextField EndDateText = new JTextField();
        JLabel RoomNum = new JLabel("RoomNum");
        JTextField RoomNumText = new JTextField();

        JButton jButton = new JButton("Search");

        pInput.add(BeginDate);
        pInput.add(BeginDateText);
        pInput.add(EndDate);
        pInput.add(EndDateText);
        pInput.add(RoomNum);
        pInput.add(RoomNumText);


        JTextArea jTextArea = new JTextArea();
        jTextArea.setLineWrap(true);
        jButton.setBounds(150, 120 + 30, 80, 30);
        jTextArea.setBounds(10, 150 + 60, 375, 120);

        f.add(pInput);
        f.add(jButton);
        f.add(jTextArea);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String BeginDate=BeginDateText.getText();
                String EndDate=EndDateText.getText();
                int RoomNum=Integer.parseInt(RoomNumText.getText());

                try {
                    Class.forName(DRIVER_CLASS);
                } catch (ClassNotFoundException cne) {
                    cne.printStackTrace();
                }
                try {
                    Connection conn= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
                    String sql = "\tSELECT hotel_name,room_name,AVG(price)\n" +
                            "\tFROM hotel NATURAL JOIN room_type NATURAL JOIN room_info\n" +
                            "\tWHERE date BETWEEN ? AND ? GROUP BY room_id HAVING min(remain)>=? ORDER BY AVG(price);";
                    PreparedStatement pst=conn.prepareStatement(sql);
                    pst.setString(1,BeginDate);
                    pst.setString(2,EndDate);
                    pst.setInt(3,RoomNum);

                    pst.execute();

                    ResultSet rst = pst.getResultSet();

                    String result = "";
                    while (rst.next()) {
                        /*
                        System.out.println(
                                rst.getString(1) + "\t"
                                + rst.getString(2) + "\t"
                                + rst.getString(3)
                        );
                        */

                        result += rst.getString(1) + "\t"
                                + rst.getString(2) + "\t"
                                + rst.getString(3) + "\n";
                    }
                    jTextArea.setText("");
                    jTextArea.append(result);

                    pst.close();
                    conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }

            }
        });

    }

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * @BelongsProject:SQLexperiment
 * @BelongsPackage:PACKAGE_NAME
 * @Author:Uestc_Xiye
 * @CreateTime:2020-12-08 19:33:36
 */
public class BookingHotelGUI {
    public static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/dbexperiment?&useSSL=false&serverTimezone=UTC";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "12138";

    public static void main(String[] args) {
        JFrame f=new JFrame("BookingHotelGUI");
        f.setSize(400,380);

        JPanel pInput = new JPanel();
        pInput.setBounds(10, 10, 375, 240);
        pInput.setLayout(new GridLayout(6,3,10, 10));

        JLabel BeginDate = new JLabel("BeginDate:");
        JTextField BeginDateText = new JTextField();
        JLabel EndDate = new JLabel("EndDate:");
        JTextField EndDateText = new JTextField();
        JLabel RoomStyle = new JLabel("RoomStyle:");
        JTextField RoomStyleText = new JTextField();
        JLabel RoomNum = new JLabel("RoomNum:");
        JTextField RoomNumText = new JTextField();
        JLabel CreateDate = new JLabel("CreateDate:");
        JTextField CreateDateText = new JTextField();
        JLabel CustomID = new JLabel("CustomID:");
        JTextField CustomIDText = new JTextField();

        JButton jButton = new JButton("Submit");

        pInput.add(BeginDate);
        pInput.add(BeginDateText);
        pInput.add(EndDate);
        pInput.add(EndDateText);
        pInput.add(RoomStyle);
        pInput.add(RoomStyleText);
        pInput.add(RoomNum);
        pInput.add(RoomNumText);
        pInput.add(CreateDate);
        pInput.add(CreateDateText);
        pInput.add(CustomID);
        pInput.add(CustomIDText);

        JTextArea jTextArea = new JTextArea();
        jTextArea.setLineWrap(true);
        jButton.setBounds(150, 240 + 30, 80, 30);
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
                int RoomStyle=Integer.parseInt(RoomStyleText.getText());
                int RoomNum=Integer.parseInt(RoomNumText.getText());
                String CreateDate=CreateDateText.getText();
                int CustomID=Integer.parseInt(CustomIDText.getText());

                try {
                    Class.forName(DRIVER_CLASS);
                } catch (ClassNotFoundException cne) {
                    cne.printStackTrace();
                }
                try {
                    Connection conn= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
                    String InsertSQL = "INSERT INTO `order` VALUES (null,?,?,?,?,?*(SELECT SUM(price) FROM room_info WHERE room_id=? AND date BETWEEN ? AND ?),?,?);";
                    PreparedStatement pst = conn.prepareStatement(InsertSQL);
                    pst.setInt(1,RoomStyle);
                    pst.setString(2,BeginDate);
                    pst.setString(3,EndDate);
                    pst.setInt(4,RoomNum);
                    pst.setInt(5,RoomNum);
                    pst.setInt(6,RoomStyle);
                    pst.setString(7,BeginDate);
                    pst.setString(8,EndDate);
                    pst.setString(9,CreateDate);
                    pst.setInt(10,CustomID);

                    pst.execute();

                    String UpdateSQL = "UPDATE room_info SET remain=remain-? WHERE room_id=? AND date BETWEEN ? AND ?;";
                    PreparedStatement p = conn.prepareStatement(UpdateSQL);
                    p.setInt(1,RoomNum);
                    p.setInt(2,RoomStyle);
                    p.setString(3,BeginDate);
                    p.setString(4,EndDate);

                    p.execute();

                    pst.close();
                    p.close();
                    conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }

            }
        });

    }

}

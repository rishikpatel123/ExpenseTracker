import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Calendar;
//https://github.com/rishikpatel123/Expense_Tracker
    public class modify_Exp_frame {
        private static final JFrame modf = new JFrame("Modify Expenses");
        private static final JPanel modpan = new JPanel(null);
        private static final JButton modbtn = new JButton("Modify");
        private static final JTextArea modTA = new JTextArea();
        private static final JDateChooser dateChooser = new JDateChooser();



        private void mod() {
            int a = JOptionPane.showConfirmDialog(modf, "Are You sure?");

            if (a == JOptionPane.YES_OPTION) {
                java.util.Date selectedDate = dateChooser.getDate();
                if (selectedDate == null) {
                    JOptionPane.showMessageDialog(modf, "Please select a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

                try {
                    // Establish the database connection (Replace with your actual database credentials)
                    String url = "";//your db url
                    String username = "";//your db username
                    String password = "";//your db password
                    Connection conn = DriverManager.getConnection(url, username, password);

                    // Prepare the SQL query for fetching entries for the selected date
                    String selectQuery = "SELECT * FROM expenses WHERE date = ?";
                    PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);

                    // Set the value for the prepared statement
                    preparedStatement.setDate(1, sqlDate);

                    // Execute the query
                    ResultSet resultSet = preparedStatement.executeQuery();

                    StringBuilder entries = new StringBuilder();
                    boolean foundEntries = false;

                    // Fetch entries for the selected date
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String description = resultSet.getString("description");

                        // Append the entry details to the 'entries' StringBuilder
                        entries.append("ID: ").append(id).append(", Description: ").append(description).append("\n");
                        foundEntries = true;
                    }

                    // Close the resources
                    resultSet.close();
                    preparedStatement.close();
                    conn.close();

                    if (foundEntries) {
                        // Show all the entries for the selected date to the user
                        String message = "Entries for selected date:\n" + entries.toString() + "\nEnter the ID of the entry you want to modify:";
                        String selectedEntryId = JOptionPane.showInputDialog(modf, message);
                        if (selectedEntryId != null) {
                            int entryId = Integer.parseInt(selectedEntryId);
                            // Now you can implement the modification logic based on the selected entry ID

                            // Get the updated values from the user
                            String newAmount = JOptionPane.showInputDialog(modf, "Enter the new amount:");
                            double amt;
                            if (newAmount == null == false) {
                                amt = Double.parseDouble(newAmount);

                                // Perform the modification by updating the database entry with the new values
                                try {
                                    Connection updateConn = DriverManager.getConnection(url, username, password);
                                    String updateQuery = "UPDATE expenses SET amount=" + amt + "  WHERE id=" + entryId + ";";

                                    PreparedStatement updateStatement = updateConn.prepareStatement(updateQuery);
                                    updateStatement.executeUpdate(updateQuery);


                                    int rowsUpdated = updateStatement.executeUpdate();
                                    updateStatement.close();
                                    updateConn.close();

                                    if (rowsUpdated > 0) {
                                        JOptionPane.showMessageDialog(modf, "Entry updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(modf, "Failed to update entry.", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(modf, "Failed to update entry.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(modf, "No entries found for the selected date.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                    } catch(SQLException ex){
                        // Handle any database-related errors here
                        ex.printStackTrace();
                    }



            } else if (a == JOptionPane.CANCEL_OPTION) {
                modf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        }

        modify_Exp_frame()
        {
            modf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            modf.setSize(500, 500);
            modf.setResizable(false);
            modf.setLocationRelativeTo(null);

            //panel
            modpan.setPreferredSize(modf.getPreferredSize());
            modpan.setBackground(new Color(65, 67, 106));
            modf.add(modpan);

            //date
            JLabel dateLabel = new JLabel("Select Date");
            dateLabel.setForeground(Color.WHITE);
            dateLabel.setFont(new Font("NimbusSanNovTLig", Font.PLAIN, 25));
            dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            dateLabel.setBounds(60, 70, 150, 30);
            modpan.add(dateLabel);

            dateChooser.setBounds(220, 70, 150, 30);
            dateChooser.setForeground(Color.black);
            dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 20));
            dateChooser.setBackground(modpan.getBackground());
            dateChooser.setDateFormatString("dd-MM-yyyy");
            dateChooser.setCalendar(Calendar.getInstance());
            modpan.add(dateChooser);

            //delete
            modbtn.setBorder(null);
            modbtn.setFont(new Font("Proxima Nova Th",Font.PLAIN,20));
            modbtn.setBounds(220,120,100,40);
            modbtn.setBackground(new Color(151,64,99));
            modbtn.setForeground(Color.white);
            modbtn.setFocusPainted(false);
            modpan.add(modbtn);
            modbtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    // Set the button color when the mouse hovers over it
                    modbtn.setBackground(new Color(190, 81, 125));
                    modbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // Set the button color back to normal when the mouse leaves
                    modbtn.setBackground(new Color(151, 64, 99));
                    modbtn.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });
            modbtn.addActionListener(e -> mod() );

            //desc
            modTA.setBounds(95,180,300,100);
            modTA.setFont(new Font("NimbusSanNovTLig", Font.PLAIN, 10));
            modTA.setBackground(modpan.getBackground());
            modTA.setBorder(new LineBorder(Color.black,1));
            modTA.setEditable(false);
            modTA.setForeground(Color.white);
            modpan.add(modTA);

            modf.setVisible(true);
        }

        public static void main(String[] args) {
            modify_Exp_frame obj=new modify_Exp_frame();
        }
    }

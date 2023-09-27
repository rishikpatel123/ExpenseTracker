import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Calendar;
//https://github.com/rishikpatel123/Expense_Tracker

public class delete_ExP_frame {
    private static final JFrame delf=new JFrame("Add Expense");
    private static final JPanel delpan=new JPanel(null);
    private static final JButton delbtn=new JButton("Delete");
    private static final JTextArea delTA=new JTextArea();
    private static final JDateChooser dateChooser=new JDateChooser();
    private void del() {
        int a = JOptionPane.showConfirmDialog(delf, "Delete Expense Permanently?");

        if (a == JOptionPane.YES_OPTION) {
            java.util.Date selectedDate = dateChooser.getDate();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(delf, "Please select a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

            try {
                // Establish the database connection (Replace with your actual database credentials)
                String url = "jdbc:mysql://db4free.net:3306/expenseeee";
                String username = "";
                String password = "";
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
                    double amount = resultSet.getDouble("amount");
                    String description = resultSet.getString("description");

                    // Append the entry details to the 'entries' StringBuilder
                    entries.append("ID: ").append(id).append(", Amount: ").append(amount).append(", Description: ").append(description).append("\n");
                    foundEntries = true;
                }

                // Close the resources
                resultSet.close();
                preparedStatement.close();
                conn.close();

                if (foundEntries) {
                    // Show all the entries for the selected date to the user
                    String message = "Entries to be deleted for selected date:\n" + entries.toString() + "\nAre you sure you want to delete these expenses?";
                    int confirmation = JOptionPane.showConfirmDialog(delf, message, "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        // Proceed with the deletion
                        Connection deleteConn = DriverManager.getConnection(url, username, password);

                        // Prepare the SQL query for deleting the expense data for the selected date
                        String deleteQuery = "DELETE FROM expenses WHERE date = ?";
                        PreparedStatement deleteStatement = deleteConn.prepareStatement(deleteQuery);

                        // Set the value for the prepared statement
                        deleteStatement.setDate(1, sqlDate);

                        // Execute the delete query
                        int rowsDeleted = deleteStatement.executeUpdate();

                        // Close the resources
                        deleteStatement.close();
                        deleteConn.close();

                        if (rowsDeleted > 0) {
                            // Show a success message
                            JOptionPane.showMessageDialog(delf, "Expenses deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(delf, "No expenses found for the selected date.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        }
                        delf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    }
                } else {
                    JOptionPane.showMessageDialog(delf, "No entries found for the selected date.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (SQLException ex) {
                // Handle any database-related errors here
                ex.printStackTrace();
            }

        } else if (a == JOptionPane.CANCEL_OPTION) {
            delf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }




    delete_ExP_frame()
    {
        delf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        delf.setSize(500, 500);
        delf.setResizable(false);
        delf.setLocationRelativeTo(null);

        //panel
        delpan.setPreferredSize(delf.getPreferredSize());
        delpan.setBackground(new Color(65, 67, 106));
        delf.add(delpan);

        //date
        JLabel dateLabel = new JLabel("Select Date");
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("NimbusSanNovTLig", Font.PLAIN, 25));
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dateLabel.setBounds(60, 70, 150, 30);
        delpan.add(dateLabel);


        dateChooser.setBounds(220, 70, 150, 30);
        dateChooser.setForeground(Color.black);
        dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 20));
        dateChooser.setBackground(delpan.getBackground());
        dateChooser.setDateFormatString("dd-MM-yyyy");
        dateChooser.setCalendar(Calendar.getInstance());
        delpan.add(dateChooser);
        //delete
        delbtn.setBorder(null);
        delbtn.setFont(new Font("Proxima Nova Th", Font.PLAIN, 20));
        delbtn.setBounds(220, 120, 100, 40);
        delbtn.setBackground(new Color(151, 64, 99));
        delbtn.setForeground(Color.white);
        delbtn.setFocusPainted(false);
        delpan.add(delbtn);
        delbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Set the button color when the mouse hovers over it
                delbtn.setBackground(new Color(190, 81, 125));
                delbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Set the button color back to normal when the mouse leaves
                delbtn.setBackground(new Color(151, 64, 99));
                delbtn.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        delbtn.addActionListener(e -> del());

        //desc
        delTA.setBounds(95, 180, 300, 100);
        delTA.setFont(new Font("NimbusSanNovTLig", Font.PLAIN, 20));
        delTA.setBackground(delpan.getBackground());
        delTA.setBorder(new LineBorder(Color.black, 1));
        delTA.setEditable(false);
        delTA.setForeground(Color.white);
        delTA.append("Note: All entries of the date will be\nDeleted.");
        delpan.add(delTA);


        delf.setVisible(true);
    }

    public static void main (String[]args){
        delete_ExP_frame obj = new delete_ExP_frame();
    }
}

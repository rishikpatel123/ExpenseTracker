import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
//https://github.com/rishikpatel123/Expense_Tracker
public class AddExpenseFrame {
    private  final String []category={null,"Housing","Transportation","Food","Entertainment","Travel","Shopping","Education","Utilities","Miscellaneous","Grocery"};
    private  final JFrame AddExpFrame=new JFrame("Add Expense");
    private  final JPanel pan=new JPanel(null);
    private final JPanel pan1=new JPanel(null);
    private  final JButton btn=new JButton("Add_");
    private  final JTextField amTF=new JTextField();
    private  final JTextArea descTA=new JTextArea();
    private final JDateChooser dateChooser = new JDateChooser();
    private final JComboBox<String> cmb=new JComboBox<>(category);
    private void add() {
        // Get user input values
        String amountStr = amTF.getText().trim();
        String categorySelected = (String) cmb.getSelectedItem();
        String description = descTA.getText().trim();
        int flag = 1;

        // Validate if any of the fields are empty
        if (amountStr.isEmpty() || categorySelected == null) {
            JOptionPane.showMessageDialog(AddExpFrame, "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
            flag = 0;
            return;
        }

        // Validate if the amount is a valid number
        double amount;
        try {
            amount = Double.parseDouble(amountStr);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(AddExpFrame, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            flag = 0;
            return;
        }

        // Validate if a date is selected
        java.util.Date selectedDate = dateChooser.getDate();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(AddExpFrame, "Please select a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
            flag = 0;
            return;
        }
        java.util.Date currentDate = new java.util.Date();

// Check if the selected date is in the future
        if (selectedDate.after(currentDate)) {
            JOptionPane.showMessageDialog(AddExpFrame, "Selected date cannot be in the future.", "Error", JOptionPane.ERROR_MESSAGE);
            flag = 0;
            return;
        }
        if(amount<1)
        {
            JOptionPane.showMessageDialog(AddExpFrame, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            flag=0;
            return;
        }

        if (flag == 1) {
            // Convert the date to SQL Date format
            java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

            try {
                // Establish the database connection (Replace with your actual database credentials)
                String url = "";//your db url
                String username = "";//your db username
                String password = "";//your db password
                Connection conn = DriverManager.getConnection(url, username, password);

                // Prepare the SQL query for inserting the expense data
                String insertQuery = "INSERT INTO expenses (date, amount, category, description) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);

                // Set the values for the prepared statement
                preparedStatement.setDate(1, sqlDate);
                preparedStatement.setDouble(2, amount);
                preparedStatement.setString(3, categorySelected);
                preparedStatement.setString(4, description);

                // Execute the insert query
                int rowsInserted = preparedStatement.executeUpdate();

                // Close the resources
                preparedStatement.close();
                conn.close();

                if (rowsInserted > 0) {
                    // Show a success message
                    JOptionPane.showMessageDialog(AddExpFrame, "Expense added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Clear the input fields for the next entry
                    amTF.setText(null);
                    dateChooser.setCalendar(Calendar.getInstance());
                    cmb.setSelectedIndex(0);
                    descTA.setText(null);
                } else {
                    JOptionPane.showMessageDialog(AddExpFrame, "Failed to add expense. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                // Handle any database-related errors here
                ex.printStackTrace();
            }
        }

    }




    private Border createBottomLineBorder(int thickness, Color color) {
        return BorderFactory.createMatteBorder(0, 0, thickness, 0, color);
    }

    AddExpenseFrame()
    {
        AddExpFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        AddExpFrame.setSize(500, 500);
        AddExpFrame.setResizable(false);
        AddExpFrame.setLocationRelativeTo(null);

        //panel
        pan.setPreferredSize(AddExpFrame.getPreferredSize());
        pan.setBackground(new Color(65, 67, 106));
        AddExpFrame.add(pan);

        pan1.setBounds(70,50,350,350);
        pan1.setBackground(new Color(66,103,178));
        pan.add(pan1);

        //label
        JLabel head = new JLabel("Add Your Daily Expenses");
        head.setForeground(Color.WHITE);
        head.setFont(new Font("NimbusSanNovTLig", Font.BOLD, 25));
        head.setHorizontalAlignment(SwingConstants.CENTER);
        head.setBounds(0, 2, 500, 30);
        pan.add(head);
        //btn
        btn.setBorder(null);
        btn.setFont(new Font("Proxima Nova Th",Font.PLAIN,20));
        btn.setBounds(190,410,100,40);
        btn.setBackground(new Color(151,64,99));
        btn.setForeground(Color.white);
        btn.setFocusPainted(false);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Set the button color when the mouse hovers over it
                btn.setBackground(new Color(190, 81, 125));
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Set the button color back to normal when the mouse leaves
                btn.setBackground(new Color(151, 64, 99));
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btn.addActionListener(e ->  add());
        pan.add(btn);

        //Amount
        JLabel amt=new JLabel("Amount");
        amt.setForeground(Color.WHITE);
        amt.setFont(new Font("NimbusSanNovTLig",Font.PLAIN, 25));
        amt.setSize(90,30);
        amt.setHorizontalAlignment(SwingConstants.RIGHT);
        amt.setLocation(50,20);
        pan1.add(amt);

        amTF.setBounds(160,22,150,30);
        amTF.setForeground(Color.white);
        amTF.setBackground(pan1.getBackground());
        amTF.setFont(new Font("Tahoma",Font.PLAIN,22));

        amTF.setBorder(createBottomLineBorder(1, Color.WHITE));
        pan1.add(amTF);

        //date
        JLabel dateLabel = new JLabel("Date");
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("NimbusSanNovTLig", Font.PLAIN, 25));
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dateLabel.setBounds(50, 70, 90, 30);
        pan1.add(dateLabel);


        dateChooser.setBounds(160, 70, 150, 30);
        dateChooser.setForeground(Color.black);
        dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 20));
        dateChooser.setBackground(pan1.getBackground());
        dateChooser.setDateFormatString("dd-MM-yyyy");
        dateChooser.setCalendar(Calendar.getInstance());
        pan1.add(dateChooser);


        //category
        JLabel cat = new JLabel();
        cat.setText("Category");
        cat.setForeground(Color.WHITE);
        cat.setFont(new Font("NimbusSanNovTLig", Font.PLAIN, 25));
        cat.setHorizontalAlignment(SwingConstants.RIGHT);
        cat.setBounds(40,110,100,30);
        pan1.add(cat);

        //category

        cmb.setBounds(160,110,150,30);
        cmb.setBackground(pan1.getBackground());
        cmb.setForeground(Color.white);
        cmb.setFont(new Font("Tahoma", Font.PLAIN, 20));
        pan1.add(cmb);

        //desc
        descTA.setBounds(25,180,300,100);
        descTA.setFont(new Font("NimbusSanNovTLig", Font.PLAIN, 20));
        descTA.setBorder(new TitledBorder("Description"));
        descTA.setBackground(pan1.getBackground());
        descTA.setForeground(Color.white);
        pan1.add(descTA);



        AddExpFrame.setVisible(true);
    }
    public static void main(String[] args) {
        AddExpenseFrame obj=new AddExpenseFrame();


    }
}

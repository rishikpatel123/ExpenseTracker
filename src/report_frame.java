import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
//https://github.com/rishikpatel123/Expense_Tracker
public class report_frame {
    private JFrame f = new JFrame("Report");
    private JTable t;
    private JPanel rptpan = new JPanel();
    private JScrollPane pane;

    private void rpt() {
        // Connect to the database and fetch data from the "expenses" table
        String url = "jdbc:mysql://db4free.net:3306/expenseeee";
        String username = "rishik";
        String password = "a512be21";
        String query = "SELECT id, date, amount, category, description FROM expenses";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Create a table model to hold the data from the result set
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 2) {
                        return Double.class;
                    } else if (columnIndex == 1) {
                        return Date.class;
                    }
                    return super.getColumnClass(columnIndex);
                }
            };
            model.setColumnIdentifiers(new String[]{"ID", "Date", "Amount", "Category", "Description"});

            // Populate the table model with data from the result set
            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("date");
                double amount = rs.getDouble("amount");
                String category = rs.getString("category");
                String description = rs.getString("description");
                model.addRow(new Object[]{id, date, amount, category, description});
            }

            // Create a JTable with the data from the table model
            t = new JTable(model);

            // Set font size for the table
            t.setFont(new Font("Arial", Font.PLAIN, 15));

            // Set auto-resize mode to adjust columns according to data
            t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

            // Set custom cell renderer to format the "Amount" column
            t.getColumnModel().getColumn(2).setCellRenderer(new CurrencyRenderer());

            // Add the JTable to the scroll pane
            pane = new JScrollPane(t);

            // Set preferred size for the scroll pane
            pane.setPreferredSize(new Dimension(1000, 500));

            // Add the scroll pane to the panel
            rptpan.add(pane);
            rptpan.revalidate();
            rptpan.repaint();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(f, "Failed to fetch data from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterByHighestExpense() {
        DefaultTableModel model = (DefaultTableModel) t.getModel();
        double highestAmount = Double.MIN_VALUE;
        int highestRow = -1;

        // Find the row with the highest amount
        for (int row = 0; row < model.getRowCount(); row++) {
            double amount = (double) model.getValueAt(row, 2);
            if (amount > highestAmount) {
                highestAmount = amount;
                highestRow = row;
            }
        }

        if (highestRow != -1) {
            // Get the data from the row with the highest amount
            int id = (int) model.getValueAt(highestRow, 0);
            Date date = (Date) model.getValueAt(highestRow, 1);
            String category = (String) model.getValueAt(highestRow, 3);
            String description = (String) model.getValueAt(highestRow, 4);

            // Create a string to display the data in the popup
            String popupMessage = "ID: " + id + "\n"
                    + "Date: " + date + "\n"
                    + "Amount: " + highestAmount + "\n"
                    + "Category: " + category + "\n"
                    + "Description: " + description;

            // Show the data in a popup dialog
            JOptionPane.showMessageDialog(f, popupMessage, "Highest Expense", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(f, "No data found.", "Highest Expense", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void filterByLowestExpense() {
        DefaultTableModel model = (DefaultTableModel) t.getModel();
        double lowestAmount = Double.MAX_VALUE;
        int lowestRow = -1;

        // Find the row with the lowest amount
        for (int row = 0; row < model.getRowCount(); row++) {
            double amount = (double) model.getValueAt(row, 2);
            if (amount < lowestAmount) {
                lowestAmount = amount;
                lowestRow = row;
            }
        }

        if (lowestRow != -1) {
            // Get the data from the row with the lowest amount
            int id = (int) model.getValueAt(lowestRow, 0);
            Date date = (Date) model.getValueAt(lowestRow, 1);
            String category = (String) model.getValueAt(lowestRow, 3);
            String description = (String) model.getValueAt(lowestRow, 4);

            // Create a string to display the data in the popup
            String popupMessage = "ID: " + id + "\n"
                    + "Date: " + date + "\n"
                    + "Amount: " + lowestAmount + "\n"
                    + "Category: " + category + "\n"
                    + "Description: " + description;

            // Show the data in a popup dialog
            JOptionPane.showMessageDialog(f, popupMessage, "Lowest Expense", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(f, "No data found.", "Lowest Expense", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void filterByCategory() {
        String category = JOptionPane.showInputDialog(f, "Enter the category:");
        if (category != null && !category.isEmpty()) {
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) t.getModel());
            t.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(category, 3));
        }
    }

    private void resetFilter() {
        t.setRowSorter(null);
    }

    report_frame() {
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setPreferredSize(new Dimension(1200, 600)); // Set preferred size for the frame
        f.setResizable(true);
        f.setLocation(200,200);



        f.pack();
        // Create buttons for filtering
        JButton highestExpenseBtn = new JButton("Highest Expense");
        highestExpenseBtn.setFont(new Font("Proxima Nova Th", Font.PLAIN, 20));
        highestExpenseBtn.setBackground(new Color(151, 64, 99));
        highestExpenseBtn.setForeground(Color.white);
        highestExpenseBtn.setFocusPainted(false);

        JButton lowestExpenseBtn = new JButton("Lowest Expense");
        lowestExpenseBtn.setFont(new Font("Proxima Nova Th", Font.PLAIN, 20));
        lowestExpenseBtn.setBackground(new Color(151, 64, 99));
        lowestExpenseBtn.setForeground(Color.white);
        lowestExpenseBtn.setFocusPainted(false);

        JButton filterByCategoryBtn = new JButton("Filter by Category");
        filterByCategoryBtn.setFont(new Font("Proxima Nova Th", Font.PLAIN, 20));
        filterByCategoryBtn.setBackground(new Color(151, 64, 99));
        filterByCategoryBtn.setForeground(Color.white);
        filterByCategoryBtn.setFocusPainted(false);

        JButton resetFilterBtn = new JButton("Reset Filter");
        resetFilterBtn.setFont(new Font("Proxima Nova Th", Font.PLAIN, 20));
        resetFilterBtn.setBackground(new Color(151, 64, 99));
        resetFilterBtn.setForeground(Color.white);
        resetFilterBtn.setFocusPainted(false);

        JButton avgSpendingBtn = new JButton("Average Spending (Current Month)");
        avgSpendingBtn.setFont(new Font("Proxima Nova Th", Font.PLAIN, 20));
        avgSpendingBtn.setBackground(new Color(151, 64, 99));
        avgSpendingBtn.setForeground(Color.white);
        avgSpendingBtn.setFocusPainted(false);

        // Add action listeners to the buttons
        highestExpenseBtn.addActionListener(e -> filterByHighestExpense());
        lowestExpenseBtn.addActionListener(e -> filterByLowestExpense());
        filterByCategoryBtn.addActionListener(e -> filterByCategory());
        resetFilterBtn.addActionListener(e -> resetFilter());
        avgSpendingBtn.addActionListener(e -> calculateAvgSpending());

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(42, 57, 80));
        buttonPanel.add(highestExpenseBtn);
        buttonPanel.add(lowestExpenseBtn);
        buttonPanel.add(filterByCategoryBtn);
        buttonPanel.add(resetFilterBtn);
        buttonPanel.add(avgSpendingBtn);

        // Add the button panel to the frame
        f.add(buttonPanel, BorderLayout.NORTH);

        // Add the panel to the frame
        rptpan.setBackground(new Color(42, 57, 80));
        f.add(rptpan);

        f.pack(); // Pack the frame to fit the preferred size
        f.setVisible(true);

        // Fetch and display data initially
        rpt();
    }

    // Custom cell renderer to format currency values
    private static class CurrencyRenderer extends DefaultTableCellRenderer {
        private final NumberFormat currencyFormat;

        CurrencyRenderer() {
            currencyFormat = new DecimalFormat("#0.00");
            currencyFormat.setGroupingUsed(true);
            ((DecimalFormat) currencyFormat).setGroupingSize(3);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Double) {
                value = currencyFormat.format(value);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    private void calculateAvgSpending() {
        DefaultTableModel model = (DefaultTableModel) t.getModel();
        double totalSpending = 0;
        int spendingCount = 0;

        // Get the current date to filter the expenses for the current month
        java.util.Date currentDate = new java.util.Date();
        java.sql.Date sqlCurrentDate = new java.sql.Date(currentDate.getTime());
        int currentMonth = sqlCurrentDate.toLocalDate().getMonthValue();
        int currentYear = sqlCurrentDate.toLocalDate().getYear();

        // Calculate the total spending and count of expenses for the current month
        for (int row = 0; row < model.getRowCount(); row++) {
            Date date = (Date) model.getValueAt(row, 1);
            int expenseMonth = date.toLocalDate().getMonthValue();
            int expenseYear = date.toLocalDate().getYear();

            if (currentMonth == expenseMonth && currentYear == expenseYear) {
                double amount = (double) model.getValueAt(row, 2);
                totalSpending += amount;
                spendingCount++;
            }
        }

        if (spendingCount > 0) {
            double avgSpending = totalSpending / spendingCount;

            // Create a string to display the average spending in the popup
            String popupMessage = "Average Spending in the Current Month: " + String.format("%.2f", avgSpending);

            // Show the average spending in a popup dialog
            JOptionPane.showMessageDialog(f, popupMessage, "Average Spending", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(f, "No data found for the current month.", "Average Spending", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new report_frame());
    }
}

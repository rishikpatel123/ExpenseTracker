import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class MainUI {
private static final String []category={null,"Housing","Transportation","Food","Entertainment","Travel","Shopping","Education","Utilities","Miscellaneous","Grocery"};
    public static void createUI() {
        JFrame f = new JFrame("Expense Tracker");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(700, 600);
        f.setResizable(false);
        f.setLocationRelativeTo(null);

        //pannel
        JPanel p = new JPanel(null);
        p.setBackground(new Color(42, 57, 80));
        p.setPreferredSize(f.getPreferredSize());
        f.add(p);

        //headlabel
        JLabel head = new JLabel();
        head.setText("Expense Tracker");
        head.setForeground(Color.WHITE);
        head.setFont(new Font("SmytheSansPro", Font.BOLD, 50));
        head.setHorizontalAlignment(SwingConstants.CENTER);
        head.setBounds(0,50,700,51);
        p.add(head);

        //Desclabel
        JLabel desc=new JLabel("Master Your Finances");
        desc.setForeground(new Color(170,170,170));
        desc.setFont(new Font("NimbusSanNovTLig",Font.PLAIN, 20));
        desc.setSize(700,22);
        desc.setHorizontalAlignment(SwingConstants.CENTER);
        desc.setLocation(0,110);
        p.add(desc);

        //AddExpenseBtn
        JButton btn=new JButton("Add Expense");
        btn.setBorder(null);
        btn.setFont(new Font("Proxima Nova Th",Font.PLAIN,20));
        btn.setBounds(250,200,200,40);
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
        btn.addActionListener(e -> System.out.println("Add"));
        p.add(btn);

        /*//category label
        JLabel cat = new JLabel();
        cat.setText("Category:");
        cat.setForeground(Color.WHITE);
        cat.setFont(new Font("NimbusSanNovTLig", Font.BOLD, 15));
        cat.setHorizontalAlignment(SwingConstants.CENTER);
        cat.setBounds(70,260,80,17);
        p.add(cat);

        //category
        JComboBox<String> cmb=new JComboBox<>(category);
        cmb.setEnabled(false);
        cmb.setBounds(150,250,200,40);
        cmb.setVisible(true);
        p.add(cmb);*/

        //deletion
        JButton delbtn=new JButton("Delete Expense");
        delbtn.setBorder(null);
        delbtn.setFont(new Font("Proxima Nova Th",Font.PLAIN,20));
        delbtn.setBounds(250,250,200,40);
        delbtn.setBackground(new Color(151,64,99));
        delbtn.setForeground(Color.white);
        delbtn.setFocusPainted(false);
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
        delbtn.addActionListener(e -> System.out.println("Delete"));
        p.add(delbtn);

        //modification
        JButton modbtn=new JButton("Modify");
        modbtn.setBorder(null);
        modbtn.setFont(new Font("Proxima Nova Th",Font.PLAIN,20));
        modbtn.setBounds(250,300,200,40);
        modbtn.setBackground(new Color(151,64,99));
        modbtn.setForeground(Color.white);
        modbtn.setFocusPainted(false);
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
        modbtn.addActionListener(e -> System.out.println("Modify"));
        p.add(modbtn);

        //report
        JButton report=new JButton("Report");
        report.setBorder(null);
        report.setFont(new Font("Proxima Nova Th",Font.PLAIN,20));
        report.setBounds(250,350,200,40);
        report.setBackground(new Color(151,64,99));
        report.setForeground(Color.white);
        report.setFocusPainted(false);
        report.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Set the button color when the mouse hovers over it
                report.setBackground(new Color(190, 81, 125));
                report.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Set the button color back to normal when the mouse leaves
                report.setBackground(new Color(151, 64, 99));
                report.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        report.addActionListener(e -> System.out.println("Report"));
        p.add(report);
        //total label
        JLabel total=new JLabel("Total Expenses");
        total.setForeground(Color.white);
        total.setFont(new Font("NimbusSanNovTLig",Font.PLAIN, 20));
        total.setSize(700,22);
        total.setLocation(80,420);
        p.add(total);

        Currency curr = Currency.getInstance("INR");
        String symbol = curr.getSymbol();
        JLabel ttl=new JLabel(symbol +" 5260");
        ttl.setForeground(new Color(255,150,119));
        ttl.setFont(new Font("NimbusSanNovTLig",Font.PLAIN, 20));
        ttl.setSize(700,22);
        ttl.setLocation(300,420);
        p.add(ttl);

        //Highest Label
        JLabel high=new JLabel("Highest Spending ");
        high.setForeground(Color.white);
        high.setFont(new Font("NimbusSanNovTLig",Font.PLAIN, 20));
        high.setSize(700,22);
        high.setLocation(80,450);
        p.add(high);

        JLabel high1=new JLabel(symbol +" 5260");
        high1.setForeground(new Color(255,150,119));
        high1.setFont(new Font("NimbusSanNovTLig",Font.PLAIN, 20));
        high1.setSize(700,22);
        high1.setLocation(300,450);
        p.add(high1);

        f.setVisible(true);
    }

    public static void main(String[] args) {
        createUI();
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class Mouse extends Frame implements MouseListener ,MouseMotionListener {
      Mouse()
      {

      }
    static JFrame j=new JFrame("Rishik");
    public static void main(String[] args) {

        j.setSize(600,600);
        j.setVisible(true);
        Mouse m=new Mouse();
        j.addMouseListener(m);
        j.addMouseMotionListener(m);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
    Label l = new Label();



      @Override
      public void mouseClicked(MouseEvent e) {

          l.setText("Mouse Clcked:");

      }

      @Override
      public void mousePressed(MouseEvent e) {
          l.setText("Mouse Pressed");
      }

      @Override
      public void mouseReleased(MouseEvent e) {
          l.setText("Mouse Released");
      }

      @Override
      public void mouseEntered(MouseEvent e) {
          j.add(l);
          l.setSize(500,20);
          l.setFont(new Font(l.getText(),Font.PLAIN,20));
          l.setLocation(10,10);
          l.setVisible(true);
          l.setEnabled(false);
          l.setForeground(Color.BLACK);
          l.setText("Mouse Entered");
      }

      @Override
      public void mouseExited(MouseEvent e) {
          l.setText("Mouse Exited");

      }


    @Override
    public void mouseDragged(MouseEvent e) {
        l.setText("Mouse Dragged");
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        l.setText("Mouse Location:"+e.getX()+","+e.getY());


    }
}

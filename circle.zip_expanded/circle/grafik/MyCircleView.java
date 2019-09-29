package grafik;
import javax.swing.*;
import java.awt.*;

public class MyCircleView extends JPanel
  {
  private double vinkel = 0.0+Math.random()*2.0*Math.PI;
  private Timer timer;

  public MyCircleView()
    {
    setBackground(Color.black);
    setPreferredSize(new Dimension(50,50));
    timer = new Timer(10, e -> {vinkel += 0.01; repaint();}); // Lambda expression
    timer.start();
    }

  // override - omdefiniera originalversionen i JPanel
  public void paintComponent(Graphics g)
    {
    super.paintComponent(g);
    int width = getWidth();
    int height = getHeight();
    int x = width / 2;
    int y = height / 2;
    g.setColor(Color.white);
    g.drawLine(0, y, width, y);
    g.drawLine(x, 0, x, height);
    g.drawOval(0, 0, width, height);
    double yp = Math.sin(vinkel);
    double xp = Math.cos(vinkel);
    g.drawLine(x, y, x + (int) (xp * x), y + (int) (yp * y));
    }
  }

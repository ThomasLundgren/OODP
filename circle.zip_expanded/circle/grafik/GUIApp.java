package grafik;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class GUIApp extends JFrame
  {
  public GUIApp()
    {
    super("Demo of graphics in JPanel added to JFrame");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    JPanel panel = new JPanel();
    panel.setLayout( new GridLayout(4,4) ); // GridLayout - 4 kolumner och 4 rader
    for(int i=0;i<16;i++)
      panel.add( new MyCircleView() );
    setContentPane( panel );
    setSize(400,400);
    setVisible(true);
    }

  public static void main(String[] args)
    {
    SwingUtilities.invokeLater( () -> new GUIApp()); // Lambda expression
    }
  }

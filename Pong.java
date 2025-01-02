import javax.swing.*;
import java.awt.*;


public class Pong
{
	
	
	public static void main(String[] args)
	{
		Pong gui = new Pong();
		gui.go();
	}
	
	public void go()
	{
		JFrame frame = new JFrame();
		
		PongPanel animationPanel = new PongPanel();
		
		//frame settings
		frame.getContentPane().add(animationPanel);
		frame.setSize(1200, 1200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		
	}
	
	class PongPanel extends JPanel
	{
		public void paintComponent(Graphics g)
		{
			
			// draw Ball
			g.setColor(Color.blue);
			g.fillOval(100,100,50,50);
			
			
		}
	} // end of inner class Panel
	
	
}














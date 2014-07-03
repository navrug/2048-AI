package graphics;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import grid.MainGrid;

public class GUIHelper
{
	public static void showOnFrame(JComponent component,
			String frameName,
			MainGrid g)
	{
		JFrame frame = new JFrame(frameName);
		WindowAdapter wa = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		frame.addKeyListener(g);
		frame.getContentPane().add(component);
		frame.pack();
		frame.setVisible(true);
	}
}
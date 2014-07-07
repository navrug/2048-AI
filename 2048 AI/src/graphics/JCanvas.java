package graphics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


public class JCanvas extends JPanel
{
	private static final long serialVersionUID = 1L;
	public List<IDrawable> drawables = new LinkedList<IDrawable>();

	public void paint(Graphics g)
	{	
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(
		    RenderingHints.KEY_ANTIALIASING,
		    RenderingHints.VALUE_ANTIALIAS_ON);

		// Anti-aliasing for text:

		g2d.setRenderingHint(
		    RenderingHints.KEY_TEXT_ANTIALIASING,
		    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		
		Color c = g.getColor();
		g.setColor(new Color(250,248,239));
		g.fillRect(0,0,FormDrawable.figureHeight,
				FormDrawable.headerHeight + FormDrawable.figureHeight);
		//Drawing the grid
		g.setColor(new Color(187,173,160));
		g.fillRoundRect(80, 180, 540, 540,20,20);
		//Drawing the title
		g.setColor(new Color(119,110,101));
		g.setFont(new Font("Verdana", Font.BOLD, 100));
		g.drawString("2048", 80, 110);
		
		g.setColor(c);

		for (Iterator<IDrawable> iter = drawables.iterator(); iter.hasNext();) {
			IDrawable d = (IDrawable) iter.next();
			d.draw(g);	
		}
	}

	public void addDrawable(IDrawable d)
	{
		drawables.add(d);
		repaint();
	}

	public void removeDrawable(IDrawable d)
	{
		drawables.remove(d);
		repaint();
	}
	
	public void clear()
	{
		drawables.clear();
		repaint();
	}
	
}
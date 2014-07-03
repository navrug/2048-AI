package graphics;

import java.awt.Dimension;

import grid.MainGrid;



public class Printer
{

	private final JCanvas jc;

	public Printer(MainGrid g, boolean graphic) {
		if (graphic) {
			jc = new JCanvas();
			jc.setPreferredSize(new Dimension(700,800));
			jc.addDrawable(g);
			GUIHelper.showOnFrame(jc,"2048", g);
		}
		else 
			jc = null;
	}

	public void refresh() 
	{
		jc.repaint();
	}
}


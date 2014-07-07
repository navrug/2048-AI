package grid;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import utilities.BestScores;
import utilities.Dir;
import graphics.IDrawable;
import graphics.Printer;
import ai.AIManager;
import ai.Conf;

public class MainGrid implements IDrawable, KeyListener {
	private Grid grid = new Grid();
	private Random r = new Random(System.currentTimeMillis());
	Printer p = new Printer(this, true);
	int best = BestScores.getBestScore("best_score.txt");
	AIManager manager;

	private static class MainGridHolder {
		private final static MainGrid grid = new MainGrid();
	}

	public static MainGrid getInstance() {
		return MainGridHolder.grid;
	}

	private MainGrid()
	{
		init();
		//				grid.cells[0][0]= 2;
		//				grid.cells[0][1]= 2;
		//				grid.cells[0][2]= 3;
		//				grid.cells[0][3]= 4;
		//				grid.cells[1][0]= 8;
		//				grid.cells[1][1]= 7;
		//				grid.cells[1][2]= 6;
		//				grid.cells[1][3]= 5;
		//				grid.cells[2][0]= 9;
		//				grid.cells[2][1]= 10;
		//				grid.cells[2][2]= 11;
		//				grid.cells[2][3]= 12;
		//				grid.cells[3][0]= 16;
		//				grid.cells[3][1]= 15;
		//				grid.cells[3][2]= 14;
		//				grid.cells[3][3]= 13;
		manager = new AIManager(grid);
	}
	void init() {
		int first = r.nextInt(Grid.size*Grid.size);
		int second = r.nextInt(Grid.size*Grid.size-1);
		if (second >= first) second++;
		int counter = 0;
		for (int i = 0; i<Grid.size; i++)
			for (int j = 0; j<Grid.size; j++) {
				if (counter == first || counter == second)
					if (r.nextInt(4)==0)
						grid.cells[i][j]=2;
					else
						grid.cells[i][j]=1;
				counter++;
			}
		grid.empty = grid.empty-2;
	}

	void newCell(Dir lastMove) {
		int counter = r.nextInt(grid.empty);
		for (int i = 0; i<Grid.size; i++)
			for (int j = 0; j<Grid.size; j++)
				if (grid.cells[i][j] == 0) {
					if (counter==0)
						if (r.nextInt(4)==0) {
							grid.cells[i][j]=2;
							manager.tellRound(lastMove, i, j, true);
						}
						else {
							grid.cells[i][j]=1;
							manager.tellRound(lastMove, i, j, false);
						}
					counter--;
				}
		grid.empty--;
	}

	void nextRound(Dir lastMove) {
		if (grid.moved)
			System.out.println("Moved "+lastMove);
		else {
			System.out.println("Impossible move.");
			return;
		}
		grid.moved = false;
		newCell(lastMove);
		if (grid.score > best) {
			BestScores.putBest("best_score.txt", grid.score);
			best = grid.score;
		}
		p.refresh();
		//Conf conf = new Conf(grid);
		//System.out.println("Fitness : "+conf.getFitness());
	}

	private void setFont(Graphics g, int i , int j)
	{
		switch (grid.cells[i][j]) {
		case 0 :
			return;
		case 1 : 
		case 2 : 
		case 3 : 
			g.setFont(new Font("Verdana", Font.BOLD, 55));
			return;
		case 4 :
		case 5 : 
		case 6 : 
			g.setFont(new Font("Verdana", Font.BOLD, 50));
			return;
		case 7 : 
		case 8 : 
		case 9 : 
			g.setFont(new Font("Verdana", Font.BOLD, 42));
			return;
		case 10 : 
		case 11 : 
		case 12 : 
		case 13 : 
			g.setFont(new Font("Verdana", Font.BOLD, 35));
			return;
		case 14 : 
		case 15 : 
		case 16 : 
			g.setFont(new Font("Verdana", Font.BOLD, 28));
			return;
		default :
			g.setFont(new Font("Verdana", Font.BOLD, 24));
			return;
		}
	}

	private void drawNumber(Graphics g, int i , int j)
	{
		switch (grid.cells[i][j]) {
		case 0 :
			return;
		case 1 : 
		case 2 : 
			g.setColor(new Color(119,110,101));
			g.drawString(String.valueOf((int)Math.pow(2, grid.cells[i][j])),
					j*130+136, i*130+276);
			return;
		case 3 : 
			g.setColor(new Color(249,246,242));
			g.drawString(String.valueOf((int)Math.pow(2, grid.cells[i][j])),
					j*130+136, i*130+276);
			return;
		case 4 :
		case 5 : 
		case 6 : 
			g.setColor(new Color(249,246,242));
			g.drawString(String.valueOf((int)Math.pow(2, grid.cells[i][j])),
					j*130+120, i*130+274);
			return;
		case 7 : 
		case 8 : 
		case 9 : 
			g.setColor(new Color(249,246,242));
			g.drawString(String.valueOf((int)Math.pow(2, grid.cells[i][j])),
					j*130+110, i*130+271);
			return;
		case 10 : 
		case 11 : 
		case 12 : 
		case 13 : 
			g.setColor(new Color(249,246,242));
			g.drawString(String.valueOf((int)Math.pow(2, grid.cells[i][j])),
					j*130+105, i*130+268);
			return;
		case 14 : 
		case 15 : 
		case 16 : 			
			g.setColor(new Color(249,246,242));
			g.drawString(String.valueOf((int)Math.pow(2, grid.cells[i][j])),
					j*130+105, i*130+265);
			return;
		default :
			g.setColor(new Color(249,246,242));
			g.drawString(String.valueOf((int)Math.pow(2, grid.cells[i][j])),
					j*130+105, i*130+263);
		}
	}

	private void drawRectangle(Graphics g, int i, int j)
	{
		switch (grid.cells[i][j]) {
		case 0 :
			g.setColor(new Color(204,192,179));
			break;
		case 1 : 
			g.setColor(new Color(238,228,218));
			break;
		case 2 : 
			g.setColor(new Color(237,224,200));
			break;
		case 3 : 
			g.setColor(new Color(242,177,121));
			break;
		case 4 : 
			g.setColor(new Color(245,149,99));
			break;
		case 5 : 
			g.setColor(new Color(246,124,95));
			break;
		case 6 : 
			g.setColor(new Color(246,93,58));
			break;
		case 7 : 
			g.setColor(new Color(237,207,114));
			break;
		case 8 : 
			g.setColor(new Color(237,204,97));
			break;
		case 9 : 
			g.setColor(new Color(237,200,80));
			break;
		case 10 : 
			g.setColor(new Color(237,197,63));
			break;
		case 11 : 
			g.setColor(new Color(238,194,46));
			break;
		default :
			g.setColor(new Color(61,58,51));
			break;
		}
		g.fillRoundRect(j*130+97, i*130+197, 116, 116,10,10);
	}

	private void drawCell(Graphics g, int i, int j)
	{
		drawRectangle(g, i, j);
		setFont(g, i, j);
		drawNumber(g, i, j);
	}

	private void drawScore(Graphics g)
	{
		g.setColor(new Color(187,173,160));
		int border = 20;
		int digit = (int)Math.log10((double)grid.score)+1;
		if (grid.score == 0) digit = 1;
		g.fillRoundRect(580-13*digit, 100, 2*border + 13*digit, 60,10,10);
		g.setFont(new Font("Verdana", Font.BOLD, 12));
		g.setColor(new Color(238,228,218));
		g.drawString("SCORE", border + 558-13*digit/2,120);
		g.setFont(new Font("Verdana", Font.BOLD, 20));
		g.setColor(new Color(255,255,255));
		g.drawString(String.valueOf(grid.score), border + 580-13*digit, 147);
	}

	private void drawBest(Graphics g)
	{
		g.setColor(new Color(187,173,160));
		int border = 20;
		int digit = (int)Math.log10((double)best)+1;
		if (best == 0) digit = 1;
		g.fillRoundRect(580-13*digit, 30, 2*border + 13*digit, 60,10,10);
		g.setFont(new Font("Verdana", Font.BOLD, 12));
		g.setColor(new Color(238,228,218));
		g.drawString("BEST", border + 563-13*digit/2,50);
		g.setFont(new Font("Verdana", Font.BOLD, 20));
		g.setColor(new Color(255,255,255));
		g.drawString(String.valueOf(best), border + 580-13*digit, 77);
	}

	public void draw(Graphics g)
	{
		Color c = g.getColor();
		drawScore(g);
		drawBest(g);
		//Tracé des cellules
		for (int i = 0; i<Grid.size; i++)
			for (int j = 0; j<Grid.size; j++)
				drawCell(g, i, j);
		g.setColor(c);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}



	@Override
	public void keyReleased(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_UP :
		case KeyEvent.VK_Z:
			grid.moveUp();
			if (grid.moved) System.out.println("Moved up.");
			else System.out.println("Impossible move.");
			nextRound(Dir.UP);
			break;
		case KeyEvent.VK_DOWN :
		case KeyEvent.VK_S :
			grid.moveDown();
			if (grid.moved) System.out.println("Moved down.");
			else System.out.println("Impossible move.");
			nextRound(Dir.DOWN);
			break;
		case KeyEvent.VK_LEFT :
		case KeyEvent.VK_Q :
			grid.moveLeft();
			if (grid.moved) System.out.println("Moved left.");
			else System.out.println("Impossible move.");
			nextRound(Dir.LEFT);
			break;
		case KeyEvent.VK_RIGHT :
		case KeyEvent.VK_D :
			grid.moveRight();
			if (grid.moved) System.out.println("Moved right.");
			else System.out.println("Impossible move.");
			nextRound(Dir.RIGHT);
			break;
		case KeyEvent.VK_SPACE :
			Dir nextMove = manager.askNextMove();
			grid.move(nextMove);

			nextRound(nextMove);
			break;
		default :
			return;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}

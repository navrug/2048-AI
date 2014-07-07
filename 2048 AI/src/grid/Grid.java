package grid;

import graphics.IDrawable;
import graphics.Printer;
import utilities.BestScores;
import utilities.Dir;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Grid
{
	public final static int size = 4;

	/*private*/ public final byte[][] cells = new byte[size][size];
	public boolean moved = false;
	int empty = size*size;
	int score = 0;

	public void display()
	{
		System.out.println("------");

		for (int i = 0; i<Grid.size; i++) {
			for (int j = 0; j<Grid.size; j++)
				System.out.print(cells[i][j]+" ");
			System.out.println();
		}
		System.out.println("------");

	}

	@Override
	public Grid clone()
	{
		Grid result = new Grid();
		for (int i = 0; i<Grid.size; i++)
			for (int j = 0; j<Grid.size; j++)
				result.cells[i][j] = cells[i][j];
		result.moved = moved;
		result.empty = empty;
		result.score = score;
		return result;
	}


	public void setCell(boolean isFour, int i, int j) 
	{
		if (cells[i][j] != 0) 
			throw new RuntimeException();
		if (isFour)
			cells[i][j] = 2;
		else
			cells[i][j] = 1;
		moved = false;
		empty--;
	}

	public int getEmpty()
	{
		return empty;
	}


	void mergeCellsUp(int j)
	{
		int first = 0;
		int next = 0;
		while (true) {
			while (first<size && cells[first][j] == 0)
				first++;
			if (first >= size-1) break;
			next = first+1;
			while (next<size && cells[next][j] == 0)
				next++;
			if (next == size) break;
			if (cells[first][j] == cells[next][j]) {
				cells[first][j]++;
				score += (int) Math.pow(2,cells[first][j]);
				cells[next][j] = 0;
				empty++;
				first = next + 1;
				moved = true;
			}
			else
				first = next;
		}
	}

	public void mergeCellsLeft(int i)
	{
		int first = 0;
		int next = 0;
		while (true) {
			while (first<size && cells[i][first] == 0)
				first++;
			if (first >= size-1) break;
			next = first+1;
			while (next<size && cells[i][next] == 0)
				next++;
			if (next == size) break;
			if (cells[i][first] == cells[i][next]) {
				cells[i][first]++;
				score += (int) Math.pow(2,cells[i][first]);
				cells[i][next] = 0;
				empty++;
				first = next + 1;
				moved = true;
			}
			else
				first = next;
		}
	}

	void mergeCellsDown(int j)
	{
		int first = 0;
		int next = 0;
		while (true) {
			while (first<size && cells[size-1-first][j] == 0)
				first++;
			if (first >= size-1) break;
			next = first+1;
			while (next<size && cells[size-1-next][j] == 0)
				next++;
			if (next == size) break;
			if (cells[size-1-first][j] == cells[size-1-next][j]) {
				cells[size-1-first][j]++;
				score += (int) Math.pow(2,cells[size-1-first][j]);
				cells[size-1-next][j] = 0;
				empty++;
				first = next + 1;
				moved = true;
			}
			else
				first = next;
		}
	}

	public void mergeCellsRight(int i)
	{
		int first = 0;
		int next = 0;
		while (true) {
			while (first<size && cells[i][size-1-first] == 0)
				first++;
			if (first >= size-1) break;
			next = first+1;
			while (next<size && cells[i][size-1-next] == 0)
				next++;
			if (next == size) break;
			if (cells[i][size-1-first] == cells[i][size-1-next]) {
				cells[i][size-1-first]++;
				score += (int) Math.pow(2,cells[i][size-1-first]);
				cells[i][size-1-next] = 0;
				empty++;
				first = next + 1;
				moved = true;
			}
			else
				first = next;
		}
	}

	public boolean moveCellsUp(int j)
	{
		short[] toShift = new short[size];
		short shift = 0;
		for (int i = 0; i<size; i++) {
			if(cells[i][j] == 0) {
				toShift[i] = -1;
				shift++;
			}
			else
				toShift[i] = shift;
		}
		for (int i = 0; i<size; i++) {
			if (toShift[i] > 0) {
				cells[i-toShift[i]][j] = cells[i][j];
				cells[i][j] = 0;
				moved = true;
			}
		}
		return moved;
	}

	public boolean moveCellsLeft(int i)
	{
		short[] toShift = new short[size];
		short shift = 0;
		for (int j = 0; j<size; j++) {
			if(cells[i][j] == 0) {
				toShift[j] = -1;
				shift++;
			}
			else
				toShift[j] = shift;
		}
		for (int j = 0; j<size; j++) {
			if (toShift[j] > 0) {
				cells[i][j-toShift[j]] =
						cells[i][j];
				cells[i][j] = 0;
				moved = true;
			}
		}
		return moved;
	}

	public boolean moveCellsDown(int j)
	{
		short[] toShift = new short[size];
		short shift = 0;
		for (int i = 0; i<size; i++) {
			if(cells[size-1-i][j] == 0) {
				toShift[i] = -1;
				shift++;
			}
			else
				toShift[i] = shift;
		}
		for (int i = 0; i<size; i++) {
			if (toShift[i] > 0) {
				cells[size-1-i+toShift[i]][j] = cells[size-1-i][j];
				cells[size-1-i][j] = 0;
				moved = true;
			}
		}
		return moved;
	}

	public boolean moveCellsRight(int i)
	{
		short[] toShift = new short[size];
		short shift = 0;
		for (int j = 0; j<size; j++) {
			if(cells[i][size-1-j] == 0) {
				toShift[j] = -1;
				shift++;
			}
			else
				toShift[j] = shift;
		}
		for (int j = 0; j<size; j++) {
			if (toShift[j] > 0) {
				cells[i][size-1-j+toShift[j]] =
						cells[i][size-1-j];
				cells[i][size-1-j] = 0;
				moved = true;
			}
		}
		return moved;
	}


	void moveUp()
	{
		for (int j = 0; j<size; j++) {
			mergeCellsUp(j);
			moveCellsUp(j);
		}
	}

	void moveLeft()
	{
		for (int i = 0; i<size; i++) {
			mergeCellsLeft(i);
			moveCellsLeft(i);
		}
	}

	void moveDown()
	{
		for (int j = 0; j<size; j++) {
			mergeCellsDown(j);
			moveCellsDown(j);
		}
	}

	void moveRight()
	{
		for (int i = 0; i<size; i++) {
			mergeCellsRight(i);
			moveCellsRight(i);
		}
	}

	public boolean move(Dir dir) {
		switch (dir) {
		case UP :
			moveUp();
			return moved;
		case DOWN :
			moveDown();
			return moved;
		case LEFT :
			moveLeft();
			return moved;
		case RIGHT :
			moveRight();
			return moved;
		}
		throw new RuntimeException();
	}


}

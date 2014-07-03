package utilities;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class BestScores {

	public static int getBestScore(String configPath) {
		Properties properties=new Properties();
		try {
			FileInputStream in =new FileInputStream(configPath);
			properties.load(in);
			in.close();
		} catch (IOException e) {
			System.out.println("Unable to load best score file.");
		}
		String bestScore = "0";
		bestScore = properties.getProperty("BestScore", "0");
		return Integer.parseInt(bestScore);
	}
	
	public static void putBest(String fileName, int score)
	{
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(
					new BufferedWriter (
							new FileWriter (fileName,false)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.println("#File meant to keep track of the best score.");
		pw.println("BestScore="+score);

		pw.close();
		return;
	}
}

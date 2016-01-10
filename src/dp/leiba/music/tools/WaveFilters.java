package dp.leiba.music.tools;

import java.util.ArrayList;

public class WaveFilters
{
	
	public static double[] band(double[] signal, int pointsPerSecond, double from, double to)
	{
		int point, i;
		double freq, j;
		boolean valid;
		double[] wave   = new double[signal.length];
		ArrayList<Integer> pass = new ArrayList<Integer>();
		
		for (j = from; j <= to; j += 0.01) {
			i = point = (int) (pointsPerSecond / j);

			System.out.println("here");
			do {
				if (!pass.contains(i)) pass.add(i);
				i += point;
			} while(i < pointsPerSecond);
		}
		
		for (i = 0; i < wave.length; i++) {			
			wave[i] = pass.contains(i % pointsPerSecond) ? signal[i] : 0;		
		}
		
		return wave;
	}

}

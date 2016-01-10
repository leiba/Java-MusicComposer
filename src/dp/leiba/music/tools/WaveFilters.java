package dp.leiba.music.tools;

import java.util.ArrayList;

/**
 * Wave Filters.
 * 
 * Low pass. Пропуск НЧ.
 * High pass. Пропуск ВЧ.
 * Band pass. Пропуск полосы.
 * Notch. Всех кроме полосы.
 * Peaking\Bell. Колоколообразный.
 * Low shelf. Полка НЧ.
 * High shelf. Полка ВЧ.
 */
public class WaveFilters
{
	
	public static final int FILTER_LOW 			= 0;
	public static final int FILTER_HIGH 		= 1;
	public static final int FILTER_BAND 		= 2;
	public static final int FILTER_NOTCH 		= 3;
	public static final int FILTER_PEAKING 		= 4;
	public static final int FILTER_LOW_SHELF 	= 5;
	public static final int FILTER_HIGH_SHELF	= 6;
	
	/**
	 * Equalizer.
	 * 
	 * @param type Type of filter.
	 * @param freq Frequency.
	 * @param q    Q factor. Frequencies width. 
	 * @param gain Gain frequencies.
	 */
	public static void eq(int type, double freq, double q, double gain)
	{
		
	}
	
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

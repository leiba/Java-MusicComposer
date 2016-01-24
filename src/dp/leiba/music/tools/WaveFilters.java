package dp.leiba.music.tools;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.filters.LowPassFS;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import dp.leiba.music.tools.Filter.PassType;

/**
 * Wave Filters.
 * http://www.cs.ubc.ca/~kvdoel/jass/doc/index.html
 * http://stackoverflow.com/questions/28291582/implementing-a-high-pass-filter-to-an-audio-signal
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
	 * @param signal    Signal.
	 * @param perSecond Points per second.
	 * @param type      Type of filter.
	 * @param freq      Frequency.
	 * @param q         Q factor. Frequencies width. 
	 * @param gain      Gain frequencies.
	 * 
	 * @return Equalized.
	 */
	public static double[] eq(double[] signal, int pointsPerSecond, int type, double freq, double q, double gain)
	{		
		int i, point;
		double amplitude, min, max;
		double[] wave 	= new double[signal.length];
		
		for (i = 0; i < wave.length; i++) {
			amplitude 	= signal[i];
			point 		= pointsPerSecond / i;
			
			if (type == FILTER_LOW) {
				min = 1; max = pointsPerSecond / freq;
			} else if (type == FILTER_HIGH) {
				//
			} else if (type == FILTER_BAND) {
				//
			} else if (type == FILTER_NOTCH) {
				//
			} else if (type == FILTER_PEAKING) {
				//
			} else if (type == FILTER_LOW_SHELF) {
				//
			} else if (type == FILTER_HIGH_SHELF) {
				//
			}
		}
		
		return wave;
	}
	
	/**
	 * Hi pass.
	 * 
	 * @param signal          Signal.
	 * @param pointsPerSecond Points per second.
	 * @param frequency       Frequency.
	 * 
	 * @return Filtered signal.
	 */
	public static double[] high(double[] signal, int pointsPerSecond, float frequency)
	{		
		return _filter(
			signal,
			pointsPerSecond,
			frequency,
			Filter.PassType.Highpass
		);
	}
	
	/**
	 * Low pass.
	 * 
	 * @param signal          Signal.
	 * @param pointsPerSecond Points per second.
	 * @param frequency       Frequency.
	 * 
	 * @return Filtered signal.
	 */
	public static double[] low(double[] signal, int pointsPerSecond, float frequency)
	{		
		return _filter(
			signal,
			pointsPerSecond,
			frequency,
			Filter.PassType.Lowpass
		);
	}
	
	/**
	 * Low pass.
	 * 
	 * @param signal          Signal.
	 * @param pointsPerSecond Points per second.
	 * @param frequency       Frequency.
	 * @param type            Filter type.
	 * 
	 * @return Filtered signal.
	 */
	private static double[] _filter(double[] signal, int pointsPerSecond, float frequency, PassType type)
	{
		float[] input	= _convert(signal);		
		Filter filter	= new Filter(frequency, pointsPerSecond, type, 1);
		
	    for (int i = 0; i < signal.length; i++) {
	        filter.update(input[i]);
	        input[i] = filter.getValue();
	    }
		
		return _convert(input);
	}
	
	/**
	 * Convert double[] to float[].
	 * 
	 * @param from Array.
	 * 
	 * @return Array.
	 */
	private static float[] _convert(double[] from)
	{
		int i		= 0;
		float[] to  = new float[from.length];
		
		for (i = 0; i < from.length; i++) {
			to[i] = (float) from[i];
		}
		
		return to;
	}
	
	/**
	 * Convert double[] to float[].
	 * 
	 * @param from Array.
	 * 
	 * @return Array.
	 */
	private static double[] _convert(float[] from)
	{
		int i		= 0;
		double[] to = new double[from.length];
		
		for (i = 0; i < from.length; i++) {
			to[i] = from[i];
		}
		
		return to;
	}
}



package dp.leiba.music.tools;

import dp.leiba.music.tools.Filter.PassType;

/**
 * Wave Filters.
 * http://www.cs.ubc.ca/~kvdoel/jass/doc/index.html
 * http://stackoverflow.com/questions/28291582/implementing-a-high-pass-filter-to-an-audio-signal
 * 
 * Low pass.
 * High pass.
 * Band pass.
 * Notch.
 * Peaking\Bell.
 * Low shelf.
 * High shelf.
 */
public class WaveFilters
{
	
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
			Filter.PassType.Low
		);
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
            Filter.PassType.High
        );
    }

    /**
     * Hi pass.
     *
     * @param signal          Signal.
     * @param pointsPerSecond Points per second.
     * @param frequency       Frequency.
     * @param width           Frequency width.
     *
     * @return Filtered signal.
     */
    public static double[] band(double[] signal, int pointsPerSecond, float frequency, float width)
    {
        signal = high(signal, pointsPerSecond, frequency - (width / 2));
        return low(signal, pointsPerSecond, frequency + (width / 2));
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
		
		for (; i < from.length; i++) {
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
		
		for (; i < from.length; i++) {
			to[i] = from[i];
		}
		
		return to;
	}
}



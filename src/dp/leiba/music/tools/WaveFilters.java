package dp.leiba.music.tools;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.filters.LowPassFS;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;

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
	
	public static double[] low(double[] signal, int pointsPerSecond, double from, double to)
	{
		float[] input	= _convert(signal);
		
		Filter fil = new Filter(10000, pointsPerSecond, Filter.PassType.Highpass, 1);
	    for (int i = 0; i < signal.length; i++)
	    {
	        fil.Update(input[i]);
	        input[i] = fil.getValue();
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

class Filter {


	/// <summary>
	/// rez amount, from sqrt(2) to ~ 0.1
	/// </summary>
	private float resonance;

	private float frequency;
	private int sampleRate;
	private PassType passType;


	public float value;

	private float c, a1, a2, a3, b1, b2;

	/// <summary>
	/// Array of input values, latest are in front
	/// </summary>
	private float[] inputHistory = new float[2];

	/// <summary>
	/// Array of output values, latest are in front
	/// </summary>
	private float[] outputHistory = new float[3];

	public Filter(float frequency, int sampleRate, PassType passType, float resonance)
	{
	    this.resonance = resonance;
	    this.frequency = frequency;
	    this.sampleRate = sampleRate;
	    this.passType = passType;

	    switch (passType)
	    {
	        case Lowpass:
	            c = 1.0f / (float)Math.tan(Math.PI * frequency / sampleRate);
	            a1 = 1.0f / (1.0f + resonance * c + c * c);
	            a2 = 2f * a1;
	            a3 = a1;
	            b1 = 2.0f * (1.0f - c * c) * a1;
	            b2 = (1.0f - resonance * c + c * c) * a1;
	            break;
	        case Highpass:
	            c = (float)Math.tan(Math.PI * frequency / sampleRate);
	            a1 = 1.0f / (1.0f + resonance * c + c * c);
	            a2 = -2f * a1;
	            a3 = a1;
	            b1 = 2.0f * (c * c - 1.0f) * a1;
	            b2 = (1.0f - resonance * c + c * c) * a1;
	            break;
	    }
	}

	public enum PassType
	{
	    Highpass,
	    Lowpass,
	}

	public void Update(float newInput)
	{
	    float newOutput = a1 * newInput + a2 * this.inputHistory[0] + a3 * this.inputHistory[1] - b1 * this.outputHistory[0] - b2 * this.outputHistory[1];

	    this.inputHistory[1] = this.inputHistory[0];
	    this.inputHistory[0] = newInput;

	    this.outputHistory[2] = this.outputHistory[1];
	    this.outputHistory[1] = this.outputHistory[0];
	    this.outputHistory[0] = newOutput;
	    //System.out.println("I:" + newInput + "; O:" + newOutput);
	}


	public float getValue()
	{
	    return this.outputHistory[0];
	}

}

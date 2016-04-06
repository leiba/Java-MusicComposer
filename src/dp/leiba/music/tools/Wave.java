package dp.leiba.music.tools;

import java.util.Arrays;

import javafx.scene.AmbientLight;
import sun.security.krb5.internal.ccache.CCacheInputStream;

/**
 * Wave effect.
 */
public class Wave
{
	public static final int SIDE_CHAIN  = 6000;
	public static final int BIT_8       = 8;
	public static final int BIT_16      = 16;
	
	/**
	 * Oscillation.
	 */
	public static class Oscillation
	{
		public double[] wave 	= new double[0];
		
		public double amplitude = 0;
	}

	/**
	 * Mix.
	 * 
	 * @param waves Waves.
	 * 
	 * @return Mix.
	 */
	public static double[] mix(double[][] waves)
	{
		int t, i     	= 0;
		double[] wave 	= Arrays.copyOfRange(waves[i++], 0, _length(waves));
		
		for (; i < waves.length; i++) {
			for (t = 0; t < waves[i].length; t++) {
				wave[t] += waves[i][t]; 
			}
		}
		
		return wave;
	}
	
	/**
	 * Limit peaks.
	 * 
	 * @param wave Wave.
	 * 
	 * @return Limited wave.
	 */
	public static double[] limit(double[] wave)
	{
		int i;
		double max = 0;
		
		for (i = 0; i < wave.length; i++) {
			max = Math.max(max, wave[i]);
		}
		
		max = max / Wav.AMPLITUDE;
		
		for (i = 0; i < wave.length; i++) {
			wave[i] /= max;
		}
		
		return wave;
	}
	
	/**
	 * Compress.
	 * 
	 * @param wave      Wave.
	 * @param threshold Threshold.
	 * @param ratio     Ratio.
	 * @param attack    Attack.
	 * @param release   Release.
	 * 
	 * @return Compressed wave.
	 */
    public static double[] compress(double[] wave, double threshold, double ratio, int attack, int release)
    {
    	double[] compress;
    	int cAttack = 0, cRelease = 0;
    	boolean isCompress 	= false, isSustain = false;
    	
    	Oscillation oscillation;
    	int offset = 0;
    	
    	do {
    		oscillation = oscillation(wave, offset);
    		
    		if (!isCompress && oscillation.amplitude > threshold) {
    			isCompress 	= true;
    			isSustain	= true;
    			cAttack 	= 0;
    			cRelease	= release;
    		}
    		
    		if (isCompress) {
    			if (cAttack < attack) {
    				compress = _compress(oscillation, threshold, ratio, cAttack * 100.0 / attack);
    				wave 	 = ToolArray.append(wave, compress, offset);
    				cAttack += oscillation.wave.length;
    			} else if(isSustain) {
    				wave = ToolArray.append(wave, _compress(oscillation, threshold, ratio, 100), offset);
    				
    				if (oscillation.amplitude < threshold) {
    					isSustain = false;
    				}
    			} else if (cRelease > 0) {
    				compress  = _compress(oscillation, threshold, ratio, cRelease * 100.0 / release);
    				wave 	  = ToolArray.append(wave, compress, offset);
    				cRelease -= oscillation.wave.length;
    			} else {
    				isCompress = false;
    			}  
    		}
    		
    	} while ((offset += oscillation.wave.length) < wave.length);
    	
        return wave;
    }
    
    /**
     * Detect one oscillation.
     * 
     * @param wave Wave.
     * @param from From.
     * 
     * @return Oscillation.
     */
    public static Oscillation oscillation(double[] wave, int from)
    {
    	int i = from, length 	= 0;
    	boolean isNegative 		= false;
    	Oscillation oscillation = new Oscillation();
    	
    	for (; i < wave.length; i++) {
    		if (!isNegative && wave[i] < 0) {
    			isNegative = true;
    		} else if (isNegative && wave[i] >= 0) {
    			break;
    		}
    		
    		if (Math.abs(wave[i]) > oscillation.amplitude) {
    			oscillation.amplitude = Math.abs(wave[i]);
    		}
    		
    		length++;
    	}
    	
    	if (length > 0) {
    		oscillation.wave = Arrays.copyOfRange(wave, from, from + length);
    	}
    	
    	return oscillation;
    }
	
	/**
	 * Side chain.
	 * 
	 * @param signal    Signal.
	 * @param amplitude Amplitude.
	 * @param step 		Step.
	 * 
	 * @return Wave.
	 */
	public static double[] sideChain(double[] signal, int amplitude, int step)
	{
		double level	= 0;
		int i 			= 0;
		double[] wave 	= new double[signal.length];
		
		for (; i < signal.length; i++) {
			if (i == 0 || i % step == 0) {
				level = SIDE_CHAIN;
			}
			
			wave[i] = signal[i] / 100.0 * (100 - (--level >= 0 ? level * 100 / SIDE_CHAIN : 0));
		}
		
		return wave;
	}

    /**
     * Reverberation.
     */
    public static void reverb()
    {

    }

    /**
     * Delay.
     */
    public static void delay()
    {

    }

    /**
     * Bit.
     *
     * @param signal Signal.
     * @param depth  Depth.
     *
     * @return Wave.
     */
    public static double[] bit(double[] signal, int depth)
    {
        int i;
        double bit;
        double[] wave   = new double[signal.length];

        for (i = 0; i < wave.length; i++) {
            bit = Math.abs(signal[i] % depth);
            bit = bit > (depth / 2) ? depth - bit : - bit;

            wave[i] = signal[i] > 0 ? signal[i] + bit : signal[i] - bit;
        }

        return wave;
    }
    
    /**
     * Max wave length.
     * 
     * @param waves Waves.
     * 
     * @return Length.
     */
    private static int _length(double[][] waves)
    {
    	int i;
    	int max = 0;
    	
    	for (i = 0; i < waves.length; i++) {
    		if (waves[i].length > max) {
    			max = waves[i].length;
    		}
    	}
    	
    	return max;
    }
    
    /**
     * Compress oscillation.
     * 
     * @param oscillation Oscillation.
     * @param threshold   Threshold.
     * @param ratio       Ratio.
     * @param percent     Percent.
     * 
     * @return Wave.
     */
    private static double[] _compress(Oscillation oscillation, double threshold, double ratio, double percent)
    {
    	int i;
    	double[] wave = new double[oscillation.wave.length];
    	
    	for (i = 0; i < wave.length; i++) {
    		
    	}
    	
    	return wave;    	
    }
    
    /**
     * Compress point.
     * 
     * @param amplitude Amplitude.
     * @param threshold Threshold.
     * @param ratio     Ratio.
     * @param percent   Percent.
     * 
     * @return Point.
     */
    private static double _compress(double amplitude, double threshold, double ratio, double percent)
    {
    	boolean isPositive 	= amplitude > 0;
    	double 	abs 		= Math.abs(amplitude);
    			threshold  += (abs - threshold) / ratio;
    	
    	if (abs < 0) {
    		abs = 0;
    	}
    	
    	return isPositive ? threshold : -threshold;    	
    }
}

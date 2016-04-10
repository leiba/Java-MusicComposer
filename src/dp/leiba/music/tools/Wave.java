package dp.leiba.music.tools;

import java.util.Arrays;

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
		return limit(wave, Wav.AMPLITUDE);
	}
	
	/**
	 * Limit peaks.
	 * 
	 * @param wave      Wave.
	 * @param amplitude Amplitude.
	 * 
	 * @return Limited wave.
	 */
	public static double[] limit(double[] wave, double amplitude)
	{
		int i;
		double max = 0;
		
		for (i = 0; i < wave.length; i++) {
			max = Math.max(max, wave[i]);
		}
		
		max = max / amplitude;
		
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
	 * @param signal  Signal.
	 * @param step    Step.
	 * 
	 * @return Wave.
	 */
	public static double[] sideChain(double[] signal, int step)
	{
		int i 			= 0;
		int chain 		= 0;
		int release		= 0;
		int releaseProc	= 0;
		double[] wave 	= new double[signal.length];
		
		
		for (; i < signal.length; i++) {
			if (i == 0 || i % step == 0) {
				chain 		= step / 3;
				releaseProc = release = step / 4;
			}
			
			if (chain-- > 0) {
				wave[i] = 0;

                if (chain == 0) {
                    i += oscillation(signal, i).wave.length;
                }
			} else if (releaseProc-- > 0) {
				wave[i] = signal[i] / 100.0 * (100 - (releaseProc * 100.0 / release));
			} else {
				wave[i] = signal[i];
			}
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
    public static double[] delay(double[] wave, int time)
    {
    	int i;
    	double max = _max(wave);
    	double[] mix 	= Arrays.copyOfRange(wave, 0, wave.length + (time * 2));
    	double[] delay1 = limit(Arrays.copyOfRange(wave, 0, wave.length), max / 2.0);
    	double[] delay2 = limit(Arrays.copyOfRange(wave, 0, wave.length), max / 4.0);
    	
    	for (i = 0; i < delay1.length; i++) {
    		mix[i + time] += delay1[i];
    	}
    	
    	for (i = 0; i < delay2.length; i++) {
    		mix[i + (time * 2)] += delay2[i];
    	}    	
    	
    	return mix;
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
     * Max amplitude.
     * 
     * @param wave Wave.
     * 
     * @return Amplitude.
     */
    private static double _max(double[] wave)
    {
    	int i;
    	double max = 0;
    	
    	for (i = 0; i < wave.length; i++) {
    		if (Math.abs(wave[i]) > max) {
    			max = Math.abs(wave[i]);
    		}
    	}
    	
    	return max;
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
    	double scale;
    	double diff		= Math.abs(oscillation.amplitude - _compress(oscillation.amplitude, threshold, ratio, percent));
    	double[] wave 	= Arrays.copyOfRange(oscillation.wave, 0, oscillation.wave.length);
    	
    	for (i = 0; i < wave.length; i++) {
    		scale 	 = wave[i] * 100.0 / oscillation.amplitude;
    		wave[i] -= diff / 100.0 * scale;
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

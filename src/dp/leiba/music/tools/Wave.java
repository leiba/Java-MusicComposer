package dp.leiba.music.tools;

import java.util.Arrays;

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
    	int i, cAttack = 0, cRelease = 0;
    	double abs, absNext;
    	boolean isCompress 	= false, isSustain = false;
    	
    	for (i = 0; i < wave.length; i++) {
    		abs 	= Math.abs(wave[i]);
    		absNext = i + 1 < wave.length ? Math.abs(wave[i + 1]) : 0;
    		
    		if (!isCompress && abs > threshold) {
    			isCompress 	= true;
    			isSustain	= true;
    			cAttack 	= 0;
    			cRelease	= release;
    		}
    		
    		if (isCompress) {
    			if (cAttack < attack) {
    				wave[i] = _compress(wave[i], threshold, ratio, cAttack * 100.0 / attack);
    				cAttack++;
    			} else if(isSustain) {
    				wave[i] = _compress(wave[i], threshold, ratio, 100);
    				
    				if (absNext < abs && absNext < threshold) {
    					isSustain = false;
    				}
    			} else if (cRelease > 0) {
    				wave[i] = _compress(wave[i], threshold, ratio, cRelease * 100.0 / release);
    				cRelease--;
    			} else {
    				isCompress = false;
    			}   			
    		}
    	}
    	
        return wave;
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

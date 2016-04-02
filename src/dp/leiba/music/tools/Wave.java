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
	 * Mix.
	 * A + B - A * B.
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
				wave[t] = wave[t] + waves[i][t] - (wave[t] * waves[i][t]); 
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
     * Compress.
     *
     * @param wave  		 Wave.
     * @param amplitude 	 Amplitude.
     * @param level Compress level.
     *
     * @return Compressed wave.
     */
    public static double[] compress(double[] wave, int amplitude, int level)
    {
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
    			max = waves.length;
    		}
    	}
    	
    	return max;
    }
}

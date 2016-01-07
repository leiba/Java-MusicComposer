package dp.leiba.music.tools;

/**
 * Wave effect.
 */
public class WaveEffect
{
	
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
				level = 6000;
			}
			
			wave[i] = signal[i] / 100.0 * (100 - (--level >= 0 ? level * 100 / 6000 : 0));			
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
}

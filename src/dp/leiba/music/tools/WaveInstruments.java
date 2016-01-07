package dp.leiba.music.tools;

public class WaveInstruments
{
	
	public static final double CONFIG_SAMPLE = 1333.33;
    
    /**
     * Generate kick.
     * 
     * @param amplitude Amplitude.
     * 
     * @return Wave.
     */
    public static double[] kick(int pointsPerSecond, double amplitude)
    {
    	int i 			= 0;
    	int points 		= (int) (pointsPerSecond / CONFIG_SAMPLE);
    	int steps		= 20;
    	int fade;
    	double[] part;
        double[] wave 	= new double[0];
        
        for (; i < steps; i++) {
        	fade	= (int) ((amplitude / 100.0) * ((steps - i) * 100 / steps));
        	part 	= WaveForms.sine(points * i, fade);
        	
        	wave = ArrayTool.concat(wave, part);
        }        
        
        return wave;
    }
    
    /**
     * Generate snare.
     * 
     * @param amplitude Amplitude.
     * 
     * @return Wave.
     */
    public static double[] snare(double amplitude)
    {
        double[] wave = new double[0];
        
        return wave;
    }
    
    /**
     * Generate hat.
     * 
     * @param amplitude Amplitude.
     * 
     * @return Wave.
     */
    public static double[] hat(double amplitude)
    {
        double[] wave = new double[0];
        
        return wave;
    }
}

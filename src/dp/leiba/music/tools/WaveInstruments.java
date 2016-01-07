package dp.leiba.music.tools;

import dp.leiba.music.creation.Rhythm;

public class WaveInstruments
{
	
	public static final int 	TYPE_KICK		= 0;
	public static final int 	TYPE_SNARE		= 1;
	public static final int 	TYPE_HAT		= 2;
	public static final int 	TYPE_STICK		= 3;
	
	public static final double 	CONFIG_SAMPLE 	= 1333.33;
    
	/**
	 * Factory.
	 * 
	 * @param pointsPerSecond Points per second.
	 * @param amplitude		  Amplitude.
	 * @param type 			  Type.
	 * 
	 * @return Wave.
	 */
	public static double[] factory(int pointsPerSecond, double amplitude, int type)
	{
		double wave[] = new double[0];
		
		switch (type) {
	    	case Rhythm.DRUMS_KICK :
	    		wave = kick(pointsPerSecond, amplitude);
	    		break;
	    		
	    	case Rhythm.DRUMS_SNARE :
	    		wave = snare(pointsPerSecond, amplitude);
	    		break;
	    		
	    	case Rhythm.DRUMS_HAT :
	    		wave = hat(pointsPerSecond, amplitude);
	    		break;
	    		
	    	case Rhythm.DRUMS_STICK :
	    		wave = stick(pointsPerSecond, amplitude);
	    		break;
		}
		
		return wave;
	}
	
    /**
     * Generate kick.
     * 
     * @param pointsPerSecond Points per second.
     * @param amplitude       Amplitude.
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
     * @param pointsPerSecond Points per second.
     * @param amplitude       Amplitude.
     * 
     * @return Wave.
     */
    public static double[] snare(int pointsPerSecond, double amplitude)
    {
    	int i 			= 0;
    	int points 		= (int) (pointsPerSecond / CONFIG_SAMPLE);
    	int steps		= 20;
    	int fade;
    	double[] part;
        double[] wave 	= new double[0];
        
        for (; i < steps; i++) {
        	fade	= (int) ((amplitude / 100.0) * ((steps - i) * 100 / steps));
        	part 	= WaveForms.noise(points * i, fade);
        	
        	wave = ArrayTool.concat(wave, part);
        }        
        
        return wave;
    }
    
    /**
     * Generate hat.
     * 
     * @param pointsPerSecond Points per second.
     * @param amplitude       Amplitude.
     * 
     * @return Wave.
     */
    public static double[] hat(int pointsPerSecond, double amplitude)
    {
        return click(pointsPerSecond, amplitude);
    }
    
    /**
     * Generate click.
     * 
     * @param pointsPerSecond Points per second.
     * @param amplitude       Amplitude.
     * 
     * @return Wave.
     */
    public static double[] click(int pointsPerSecond, double amplitude)
    {
    	int i 			= 0;
    	int steps		= 40;
    	double[] part;
        double[] wave 	= new double[] {amplitude};
        
        for (; i < steps; i++) {
        	part 	= WaveForms.noise(3, 100);        	
        	wave = ArrayTool.concat(wave, part);
        }        
        
        return wave;
    }
    
    /**
     * Generate stick.
     * 
     * @param pointsPerSecond Points per second.
     * @param amplitude       Amplitude.
     * 
     * @return Wave.
     */
    public static double[] stick(int pointsPerSecond, double amplitude)
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
}

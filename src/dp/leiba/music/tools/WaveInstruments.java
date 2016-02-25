package dp.leiba.music.tools;

import java.util.Arrays;

import dp.leiba.music.creation.Rhythm;

public class WaveInstruments
{
	
	public static final int 	TYPE_KICK		= 0;
	public static final int 	TYPE_SNARE		= 1;
	public static final int 	TYPE_HAT		= 2;
	public static final int 	TYPE_STICK		= 3;
	
	public static final double 	CONFIG_SAMPLE 	= 1000;
	public static final int  	CONFIG_ATTACK 	= 30;
    
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
        int i;
        double[] wave 	= new double[0];

        for (i = 0; i < 10; i++) {
            wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 1500, amplitude));
        }

        for (i = 0; i < 15; i++) {
            wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 50, amplitude));

            if (i > 5) {
                amplitude -= 10;
            }
        }

        return wave;

/*
    	int points 		= (int) (pointsPerSecond / CONFIG_SAMPLE);
    	int steps		= 20;
    	double fade		= amplitude;
    	int fadeFreq	= 0;
        double[] wave 	= new double[0];
        
        wave = ArrayTool.concat(wave, WaveForms.sine(10, 60));        
        
        for (i = 20; i <= 40; i += 10) {
        	wave = ArrayTool.concat(wave, WaveForms.sine(i, amplitude));
        	
        	if (i == 40) {
        		wave = ArrayTool.concat(wave, WaveForms.sine(i * 2, amplitude - 10));
        	}
        }
        
        for (i = 0; i < steps; i++) {
        	fadeFreq 	= i < 5 ? points * i * 4 : fadeFreq + 20;
        	fade 		-= i > 10 ? 8 : 1;       	
        	wave 		= ArrayTool.concat(wave, WaveForms.sine(fadeFreq, fade));
        }        
        
        return wave;
*/
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
     * http://joesul.li/van/synthesizing-hi-hats/
     * 
     * @param pointsPerSecond Points per second.
     * @param amplitude       Amplitude.
     * 
     * @return Wave.
     */
    public static double[] click(int pointsPerSecond, double amplitude)
    {
    	int fundamental = 40;
    	double[] freq 	= new double[] {2, 3, 4.16, 5.43, 6.79, 8.21};
    	double[] wave 	= new double[pointsPerSecond / 3];
    	
    	int i, j, points;
    	int steps		= 5;
    	double[] part;
    	
    	for (i = 0; i < freq.length; i++) {
    		points = MathTool.freq(pointsPerSecond, freq[i] * fundamental);
    		for (j = 0; j < wave.length / points; j++) {
    			wave = ArrayTool.fillSum(wave, WaveForms.square(points, amplitude), j * points, true);
    			//WaveFilters.band(wave, pointsPerSecond, 7, 7);
    			System.exit(0);
    		}
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

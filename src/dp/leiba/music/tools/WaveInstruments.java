package dp.leiba.music.tools;

import java.util.Arrays;

import dp.leiba.music.creation.Rhythm;

/**
 * WaveInstruments.
 *
 * http://joesul.li/van/synthesizing-hi-hats/
 * https://dev.opera.com/articles/drum-sounds-webaudio/
 */
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
        double[] wave 	= new double[0];

        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 4900, 30));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 4900, 55));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 4100, 60));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 2004, 40));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 1470, 90));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 882, 90));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 580, 100));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 397, 95));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 247, 95));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 170, 95));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 110, 95));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 85, 95));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 70, 95));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 63, 82));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 55, 80));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 49, 68));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 46, 55));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 42, 40));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 41, 27));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 38, 15));
        wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 38, 3));

        /*
        double freq = 150;
        for (; freq >= 60; freq -= 5) {
            wave = ArrayTool.concat(wave, WaveForms.sine((int) (pointsPerSecond / freq), freq - 50));
        }
        */

        /*
        for (i = 0; i < 10; i++) {
            wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 1500, amplitude));
        }

        for (i = 0; i < 15; i++) {
            wave = ArrayTool.concat(wave, WaveForms.sine(pointsPerSecond / 50, amplitude));

            if (i > 5) {
                amplitude -= 10;
            }
        }
        */

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

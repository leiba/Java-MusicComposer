package dp.leiba.music.tools;

import java.util.Arrays;

import dp.leiba.music.creation.Rhythm;
import dp.leiba.music.creation.Theory;

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
        int i;
        double fade;
        double[] signal, attack;
        int steps 		= 6;
        int root 		= 23;        
    	double[] wave 	= new double[0];    	
        
    	for (i = steps; i > 0; i--) {
    		fade 	= amplitude / 100 * (i * 100 / steps);
    		signal 	= WaveForms.sine((int) (pointsPerSecond / Theory.getNoteFreq(root)), fade);
    		
    		if (i == steps) {
    			attack = WaveForms.sine((int) (pointsPerSecond / Theory.getNoteFreq(root + Theory.TONES * 2)), fade);
    			
    			signal = ArrayTool.concat(
    				Arrays.copyOfRange(attack, 0, attack.length / 4),
    				Arrays.copyOfRange(signal, signal.length / 4, signal.length)
    			);
    		}
    		
    		wave 	= ArrayTool.concat(wave, signal);
    		root--;    		
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

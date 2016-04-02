package dp.leiba.music.tools;

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
        int steps 		= 25;
        int rootPunch   = 16;
        int root 		= rootPunch + Theory.TONES;        
    	double[] attack = new double[0];   
    	double[] rel 	= new double[0];
        
    	for (i = 10; i > 1; i--) {
    		attack = ToolArray.concat(
                    attack,
                    WaveForms.sine((int) (pointsPerSecond / Theory.getNoteFreq(rootPunch + Theory.TONES * i)), amplitude)
            );
    	}
    	
    	for (i = steps; i > 0; i--) {
    		rel = ToolArray.concat(
                    rel,
                    WaveForms.sine((int) (pointsPerSecond / Theory.getNoteFreq(root)), amplitude)
            );
    		
    		if (i <= 15) {
    			amplitude -= 0.07;
    		}
    	}

        return ToolArray.concat(attack, rel);
    }
    
    /**
     * Ride.
     * 
     * @param pointsPerSecond Points per second.
     * @param amplitude		  Amplitude.
     * 
     * @return Wave.
     */
    public static double[] ride(int pointsPerSecond, double amplitude)
    {
    	double level;
    	double percent;
    	int frequency	= 10000;
    	int points 		= pointsPerSecond / frequency;
    	double steps 	= (int) (pointsPerSecond / 2 / points);    	
    	double[] wave 	= new double[0];
    	
    	for (int i = 0; i < steps; i++) {
    		percent = 50 - i * 50 / steps;
    		level 	= ToolMath.random(amplitude / 7, amplitude / 5);
    		wave 	= ToolArray.concat(wave, WaveForms.sine(points, level / 100 * percent));

            if (percent < 1) break;
    	}

        wave = ToolFFT.fftFilter(wave, new ToolFFT.FFTFilter[] {
            ToolFFT.filter(ToolFFT.FILTER_BELL, 11000, 100),
            ToolFFT.filter(ToolFFT.FILTER_LOW, 9000, 0),
        });
    	
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
        	
        	wave = ToolArray.concat(wave, part);
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
    		points = ToolMath.freq(pointsPerSecond, freq[i] * fundamental);
    		for (j = 0; j < wave.length / points; j++) {
    			wave = ToolArray.fillSum(wave, WaveForms.square(points, amplitude), j * points, true);
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
        	
        	wave = ToolArray.concat(wave, part);
        }        
        
        return wave;
    }
}

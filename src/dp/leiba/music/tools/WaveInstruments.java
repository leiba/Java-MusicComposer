package dp.leiba.music.tools;

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
	public static final int 	TYPE_RIDE		= 1;
	public static final int 	TYPE_SNARE		= 2;
	public static final int 	TYPE_HAT		= 3;

	public static final double 	CONFIG_SAMPLE 	= 1000;
	public static final int  	CONFIG_ATTACK 	= 30;
    
	/**
	 * Factory.
	 *
	 * @param note Note.
	 * @param type Type.
	 *
	 * @return Wave.
	 */
	public static double[] factory(int note, int type)
	{
		double wave[] = new double[0];
		
		switch (type) {
	    	case TYPE_KICK :
	    		wave = kick(note);
	    		break;

            case TYPE_RIDE :
                wave = ride(note);
                break;
	    		
	    	case TYPE_SNARE :
	    		wave = snare(note);
	    		break;
	    		
	    	case TYPE_HAT :
	    		wave = hat(note);
	    		break;
		}
		
		return wave;
	}
	
    /**
     * Generate kick.
     *
     * @param note Note.
     *
     * @return Wave.
     */
    public static double[] kick(int note)
    {
        int i;
        int steps 		    = 25;
        int rootPunch       = 16;
        int root 		    = rootPunch + Theory.TONES;
        double amplitude    = Wav.AMPLITUDE;
    	double[] attack     = new double[0];
    	double[] rel 	    = new double[0];
        
    	for (i = 10; i > 1; i--) {
    		attack = ToolArray.concat(
                    attack,
                    WaveForms.sine((int) (Wav.FREQUENCY / Theory.getNoteFreq(rootPunch + Theory.TONES * i)), amplitude)
            );
    	}
    	
    	for (i = steps; i > 0; i--) {
    		rel = ToolArray.concat(
                    rel,
                    WaveForms.sine((int) (Wav.FREQUENCY / Theory.getNoteFreq(root)), amplitude)
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
     * @param note Note.
     * 
     * @return Wave.
     */
    public static double[] ride(int note)
    {
    	double level;
    	double percent;
    	double frequency	= 10000;
        double amplitude = Wav.AMPLITUDE;
    	int points 		= (int) (Wav.FREQUENCY / frequency);
    	double steps 	= (int) (Wav.FREQUENCY / 2 / points);
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
     * @param note Note.
     * 
     * @return Wave.
     */
    public static double[] snare(int note)
    {
    	int i 			= 0;
    	int points 		= (int) (Wav.FREQUENCY / CONFIG_SAMPLE);
    	int steps		= 20;
    	int fade;
    	double[] part;
        double[] wave 	= new double[0];
        
        for (; i < steps; i++) {
        	fade	= (int) ((Wav.AMPLITUDE / 100.0) * ((steps - i) * 100 / steps));
        	part 	= WaveForms.noise(points * i, fade);
        	
        	wave = ToolArray.concat(wave, part);
        }        
        
        return wave;
    }
    
    /**
     * Generate hat.
     *
     * @param note Note.
     *
     * @return Wave.
     */
    public static double[] hat(int note)
    {
        return new double[0];
    }
}

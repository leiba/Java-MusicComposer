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
	public static final int 	TYPE_BASS		= 4;

	public static final double 	CONFIG_SAMPLE 	= 1000;
	public static final int  	CONFIG_ATTACK 	= 30;

    public static final int     KICK_STEPS_ATTACK   = 10;
    public static final int     KICK_STEPS_FADE     = 15;
    public static final int     KICK_STEPS          = 25;

    public static final int     RIDE_CUT_BELL       = 11000;
    public static final int     RIDE_CUT_LOW        = 9000;

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

            case TYPE_BASS :
                wave = bass(note);
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
        int rootPunch       = Theory.getNoteKick(note);
        int root 		    = rootPunch + Theory.TONES;
        double amplitude    = Wav.AMPLITUDE;
    	double[] attack     = new double[0];
    	double[] rel 	    = new double[0];

    	for (i = KICK_STEPS_ATTACK; i > 1; i--) {
    		attack = ToolArray.concat(
                attack,
                WaveForms.sine((int) (Wav.FREQUENCY / Theory.getNoteFreq(rootPunch + Theory.TONES * i)), amplitude)
            );
    	}
    	
    	for (i = KICK_STEPS; i > 0; i--) {
    		rel = ToolArray.concat(
                rel,
                WaveForms.sine((int) (Wav.FREQUENCY / Theory.getNoteFreq(root)), amplitude)
            );
    		
    		if (i <= KICK_STEPS_FADE) {
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
    	double frequency	= Theory.getNoteFreq(Theory.getNoteRide(note));
        double amplitude    = Wav.AMPLITUDE;
    	int points 		    = (int) (Wav.FREQUENCY / frequency);
    	double steps 	    = (int) (Wav.FREQUENCY / 2 / points);
    	double[] wave 	    = new double[0];
    	
    	for (int i = 0; i < steps; i++) {
    		percent = 50 - i * 50 / steps;
    		level 	= ToolMath.random(amplitude / 7, amplitude / 5);
    		wave 	= ToolArray.concat(wave, WaveForms.sine(points, level / 100 * percent));

            if (percent < 1) break;
    	}

        wave = ToolFFT.fftFilter(wave, new ToolFFT.FFTFilter[] {
            ToolFFT.filter(ToolFFT.FILTER_BELL, RIDE_CUT_BELL,  100),
            ToolFFT.filter(ToolFFT.FILTER_LOW,  RIDE_CUT_LOW,   0),
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

    /**
     * Generate hat.
     *
     * @param note Note.
     *
     * @return Wave.
     */
    public static double[] bass(int note)
    {
        int i;
        double[] wave       = new double[0];
        double frequency    = Theory.getNoteFreq(Theory.getNoteBass(note));
        int steps           = (int) (Wav.FREQUENCY / 2 / (Wav.FREQUENCY / frequency));
        double fade         = Wav.AMPLITUDE * 1.0 / steps;

        for (i = 0; i < steps; i++) {
            wave = ToolArray.concat(wave, WaveForms.sine((int) (Wav.FREQUENCY / frequency), Wav.AMPLITUDE - fade * i));
        }

        return wave;
    }
}

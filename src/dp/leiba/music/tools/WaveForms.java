package dp.leiba.music.tools;

import java.util.Random;

/**
 * Waves.
 */
public class WaveForms
{
	public static final int	WAVE_SINE   = 0;
    public static final int WAVE_SQUARE = 1;
    public static final int WAVE_SAW	= 2;
    public static final int WAVE_NOISE	= 3;
    public static final int WAVE_REST	= 4;

    /**
     * Factory.
     * 
     * @param points    Points.
     * @param amplitude Amplitude.
     * @param form      Wave form.
     * 
     * @return Wave.
     */
    public static double[] factory(int points, double amplitude, int form)
    {
    	double[] wave = new double[0];
    	
    	switch (form) {
	    	case WAVE_SINE :
	    		wave = sine(points, amplitude);
	    		break;
	    		
	    	case WAVE_SQUARE :
	    		wave = square(points, amplitude);
	    		break;
	    		
	    	case WAVE_SAW :
	    		wave = saw(points, amplitude);
	    		break;
	    		
	    	case WAVE_NOISE :
	    		wave = noise(points, amplitude);
	    		break;
	    		
	    	case WAVE_REST :
	    		wave = rest(points);	    		
	    		break;
    	}
    	
    	return wave;
    }
    
    /**
     * Generate sine.
     *
     * @param points    Points.
     * @param amplitude Amplitude.
     *
     * @return Wave.
     */
    public static double[] sine(int points, double amplitude)
    {
        points          = points - (points % 2);
        int i           = 0;
        double percent  = 0;
        double[] wave   = new double[points];

        for (; i < points; i++) {
            percent = i * 100.0 / (points - 1);
            wave[i] = MathTool.round(Math.sin((Math.PI * 2) / 100 * percent) * amplitude, 2);
        }

        return wave;
    }

    /**
     * Generate square.
     *
     * @param points    Points.
     * @param amplitude Amplitude.
     *
     * @return Wave.
     */
    public static double[] square(int points, double amplitude)
    {
        points          = (points - (points % 2));
        int i           = 0;
        double[] wave   = new double[points];

        for (; i < wave.length; i++) {
            wave[i] = i < (points / 2) ? amplitude : -amplitude;
        }

        return wave;
    }

    /**
     * Generate saw.
     *
     * @param points    Points.
     * @param amplitude Amplitude.
     *
     * @return Wave.
     */
    public static double[] saw(int points, double amplitude)
    {
        points          = (points - (points % 2));
        int i           = 0;
        double size     = amplitude * 2;
        double[] wave   = new double[points];

        for (; i < wave.length; i++) {
            wave[i] = MathTool.round((size / (points - 1)) * i - amplitude, 2);
        }

        return wave;
    }

    /**
     * Generate noise.
     *
     * @param points    Points.
     * @param amplitude Amplitude.
     *
     * @return Wave.
     */
    public static double[] noise(int points, double amplitude)
    {
        points          = (points - (points % 2));
        int i           = 0;
        Random random   = new Random();
        double[] wave   = new double[points];

        for (; i < wave.length; i++) {
            wave[i] = MathTool.round((-amplitude) + (amplitude - (-amplitude)) * random.nextDouble(), 2);
        }

        return wave;
    }

    /**
     * Generate rest.
     *
     * @param points Points.
     *
     * @return Wave.
     */
    public static double[] rest(int points)
    {
        int i           = 0;
        double[] wave   = new double[points];

        for (; i < wave.length; i++) {
            wave[i] = 0;
        }

        return wave;
    }
}

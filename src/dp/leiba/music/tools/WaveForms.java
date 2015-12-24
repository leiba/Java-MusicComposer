package dp.leiba.music.tools;

import java.util.Random;

/**
 * Waves.
 */
public class WaveForms
{

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
    public static double[] square(int points, int amplitude)
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
}

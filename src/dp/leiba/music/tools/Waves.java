package dp.leiba.music.tools;

import java.util.Random;

/**
 * Waves.
 */
public class Waves
{

    public static int DEGREE = 360;

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
        int i           = 0;
        int k           = 0;
        int step        = DEGREE / ((points - (points % 2)) - 1);
        double[] wave   = new double[DEGREE / step + 1];

        for (; i <= DEGREE; i += step) {
            wave[k++] = MathTool.round(Math.sin(i) * amplitude, 2);
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
     * Generate triangle.
     *
     * @param points    Points.
     * @param amplitude Amplitude.
     *
     * @return Wave.
     */
    public static double[] triangle(int points, double amplitude)
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

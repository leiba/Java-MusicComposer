package dp.leiba.music.tools;

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
        int step        = DEGREE / (points - (points % 2));
        double[] wave   = new double[DEGREE / step + 1];

        for (; i <= DEGREE; i+= step) {
            wave[k++] = MathTool.round(Math.sin(i) * amplitude, 2);
        }

        return wave;
    }

    public void square(double amplitude, int points)
    {

    }

    public void triangle(double amplitude, int points)
    {

    }

    public void noise(double amplitude, int points)
    {

    }


}

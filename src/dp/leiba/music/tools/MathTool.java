package dp.leiba.music.tools;

/**
 * Math tools.
 */
public class MathTool
{
    /**
     * Round.
     *
     * @param value  Value.
     * @param places Places.
     *
     * @return Double rounded.
     */
    public static double round(double value, int places)
    {
        long factor = (long) Math.pow(10, places);
        value       = value * factor;
        long tmp    = Math.round(value);

        return (double) tmp / factor;
    }

    /**
     * Random int.
     *
     * @param min Min.
     * @param max Max.
     *
     * @return Random.
     */
    public static int random(int min, int max)
    {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    /**
     * Simple random.
     *
     * @return Is.
     */
    public static boolean is()
    {
        return random(0, 1) == 1;
    }

    /**
     * Is value even.
     *
     * @param value Value.
     *
     * @return Is even.
     */
    public static boolean isEven(int value)
    {
        return value % 2 == 0;
    }
}

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
}

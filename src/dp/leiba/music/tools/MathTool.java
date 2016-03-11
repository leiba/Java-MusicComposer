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
     * Random double.
     *
     * @param min Min.
     * @param max Max.
     *
     * @return Random.
     */
    public static double random(double min, double max)
    {
        return min + (Math.random() * ((max - min) + 1));
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
    
    /**
     * Round range.
     * 
     * @param value Value.
     * @param min   Minimum.
     * @param max   Maximum.
     * 
     * @return Rounded.
     */
    public static double range(double value, int min, int max)
    {
    	return value > max ? max : value < min ? min : value;
    }
    
    /**
     * Calculate frequency points.
     * 
     * @param pointsPerSecond Points per second.
     * @param freq 		      Frequency.
     * 
     * @return Points. 
     */
    public static int freq(int pointsPerSecond, double freq)
    {
    	return (int) (pointsPerSecond / freq);    	
    }
}

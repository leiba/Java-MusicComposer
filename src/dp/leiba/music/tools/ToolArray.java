package dp.leiba.music.tools;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Tool Array.
 */
public class ToolArray
{

    /**
     * Concatenate arrays.
     *
     * @param a A array.
     * @param b B array.
     *
     * @return Concatenate array.
     */
    public static int[] concat (int[] a, int[] b)
    {
    	int[] concat = new int[a.length + b.length];
    	
    	System.arraycopy(a, 0, concat, 0, a.length);
    	System.arraycopy(b, 0, concat, a.length, b.length);
    	
    	return concat;
    }
    
    /**
     * Concatenate arrays.
     *
     * @param a A array.
     * @param b B array.
     *
     * @return Concatenate array.
     */
    public static double[] concat (double[] a, double[] b)
    {
    	double[] concat = new double[a.length + b.length];
    	
    	System.arraycopy(a, 0, concat, 0, a.length);
    	System.arraycopy(b, 0, concat, a.length, b.length);
    	
    	return concat;
    }
    
    /**
     * Append.
     * 
     * @param to    To.
     * @param from  From.
     * @param start Start.
     * 
     * @return Appended array.
     */
    public static double[] append(double[] to, double[] from, int start)
    {
    	int i;
    	
    	for (i = 0; i < from.length; i++) {
    		to[start + i] = from[i];
    	}
    	
    	return to;
    } 
    
    /**
     * Fill array.
     * 
     * @param to    To array.
     * @param from  From array.
     * @param start Start position.
     * 
     * @return Filled array.
     */
    public static double[] fill(double[] to, double[] from, int start)
    {
        int i;

        for (i = 0; i < from.length; i++) {
            if (start < to.length) {
                to[start++] = from[i];
            }
        }

        return to;        
    }

    /**
     * Fill zero.
     *
     * @param to    To array.
     * @param start Start position.
     * @param end   End position.
     *
     * @return Filled array.
     */
    public static double[] fillZero(double[] to, int start, int end)
    {
        for (; start < to.length && start < end; start++) {
            to[start] = 0;
        }

        return to;
    }

    /**
     * Array sum.
     *
     * @param to      To array.
     * @param from    From array.
     * @param limiter Limiter.
     *
     * @return Filled array.
     */
    public static double[] sum(double[][] arrays, double[] two, boolean limiter)
    {
        int i;
        int length;

        return new double[0];
    }

    /**
     * Clear range.
     *
     * @param array Array.
     * @param min   Minimum stay value.
     * @param max   Maximum stay value.
     *
     * @return Cleared array.
     */
    public static int[] clearRange(int[] array, int min, int max)
    {
        int i;
        ArrayList<Integer> upper    = new ArrayList<Integer>();

        for (i = 0; i < array.length; i++) {
            if (array[i] >= min && array[i] <= max) {
                upper.add(array[i]);
            }
        }

        array = new int[upper.size()];

        for (i = 0; i < upper.size(); i++) {
            array[i] = upper.get(i);
        }

        return array;
    }

    /**
     * Shuffle array.
     *
     * @param array Array.
     *
     * @return Shuffled array.
     */
    public static int[][] shuffle(int[][] array)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--)
        {
            int index       = rnd.nextInt(i + 1);
            int[] a         = array[index];
            array[index]    = array[i];
            array[i]        = a;
        }

        return array;
    }

    /**
     * Max abs.
     *
     * @param array Array.
     *
     * @return Max abs.
     */
    public static double maxAbs(double[] array)
    {
        int i       = 0;
        double max  = 0;

        for (; i < array.length; i++) {
            if (Math.abs(array[i]) > max) {
                max = Math.abs(array[i]);
            }
        }

        return max;
    }

    /**
     * Print multi array.
     *
     * @param array Array.
     */
    public static void print(int[][] array) {

        for (int[] i : array) {
            for (int j : i) {
                System.out.print(j);
                System.out.print("\t");
            }

            System.out.println();
        }
    }
}

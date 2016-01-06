package dp.leiba.music.tools;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Tool Array.
 */
public class ArrayTool
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
        System.arraycopy(from, 0, to, start, from.length);
        return to;        
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

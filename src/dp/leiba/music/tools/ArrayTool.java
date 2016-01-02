package dp.leiba.music.tools;

import java.util.ArrayList;

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
}
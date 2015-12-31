package dp.leiba.music.tools;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tool Array.
 */
public class ArrayTool
{

    /**
     * Concat arrays.
     *
     * @param a     A.
     * @param b     B.
     * @param <T>   Type.
     *
     * @return Concat array.
     */
    public static <T> T[] concat (T[] a, T[] b)
    {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    /**
     * Clear lower.
     *
     * @param array Array.
     * @param low   Min stay value.
     *
     * @return Cleared array.
     */
    public static int[] clearLower(int[] array, int low)
    {
        int i;
        ArrayList<Integer> upper    = new ArrayList<Integer>();

        for (i = 0; i < array.length; i++) {
            if (array[i] >= low) {
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

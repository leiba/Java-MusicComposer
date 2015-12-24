package dp.leiba.music.tools;

import java.lang.reflect.Array;

/**
 * Tool Array.
 */
public class ToolArray
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
}

package dp.leiba.music.creation;

import com.sun.deploy.util.ArrayUtil;
import dp.leiba.music.tools.MathTool;

import java.util.Arrays;

/**
 * Music rhythm.
 */
public class Rhythm
{
    public static final int MELODY_REST     = 0;
    public static final int MELODY_ATTACK   = 1;

    /**
     * Get melody rhythm.
     *
     * @param bars  Bars.
     * @param beats Beats.
     *
     * @return Rhythm.
     */
    public static int[] getRhythmMelody(int bars, int beats)
    {
        int i           = 0;
        int[] rhythm    = new int[bars * beats];

        for (; i < rhythm.length; i++) {
            if (i == 0 || i % beats == 0) {
                rhythm[i] = MELODY_ATTACK;
            } else {
                rhythm[i] = MathTool.is() ? MELODY_ATTACK : MELODY_REST;
            }
        }

        System.out.println(Arrays.toString(rhythm));
        return rhythm;
    }
}

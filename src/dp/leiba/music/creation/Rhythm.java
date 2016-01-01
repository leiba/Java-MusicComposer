package dp.leiba.music.creation;

import dp.leiba.music.tools.MathTool;

/**
 * Music rhythm.
 */
public class Rhythm
{
    public static final int MELODY_REST     = 0;
    public static final int MELODY_ATTACK   = 1;
    public static final int MELODY_RELEASE  = 2;

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
                if (MathTool.is()) {
                    rhythm[i] = MathTool.is() ? MELODY_ATTACK : MELODY_RELEASE;
                } else {
                    rhythm[i] = MELODY_REST;
                }
            }
        }

        return rhythm;
    }
}

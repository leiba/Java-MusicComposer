package dp.leiba.music.creation;

import dp.leiba.music.tools.ArrayTool;

/**
 * Rhythm melody.
 */
public class Melody
{

    /**
     * Melody lead.
     *
     * @param bars   Bars.
     * @param beats  Beats.
     * @param rhythm Rhythm.
     * @param chords Chords.
     * @param notes  Notes.
     *
     * @return Melody.
     */
    public static int[] getLead(int bars, int beats, int[] rhythm, int[][] chords, int[] notes)
    {
        int i           = 0;
        int j           = 0;
        int[] melody    = new int[bars * beats];

        for (; i < bars; i++) {
            for (; j < beats; j++) {

            }
        }

        return melody;
    }

    /**
     * Melody pluck.
     *
     * @param bars   Bars.
     * @param beats  Beats.
     * @param rhythm Rhythm.
     * @param chords Chords.
     *
     * @return Melody.
     */
    public static int[][] getPluck(int bars, int beats, int[] rhythm, int[][] chords)
    {
        int j, index;
        int i           = 0;
        int[][] melody  = new int[bars * beats][];

        for (; i < bars; i++) {
            for (j = 0; j < beats; j++) {
                index = bars * i + j;

                if (rhythm[index] != Rhythm.MELODY_REST) {
                    melody[index] = chords[i];
                } else {
                    melody[index] = new int[] {
                        Rhythm.MELODY_REST
                    };
                }
            }
        }

        return melody;
    }

    /**
     * Melody bass.
     *
     * @param bars   Bars.
     * @param beats  Beats.
     * @param rhythm Rhythm.
     * @param chords Chords.
     *
     * @return Melody.
     */
    public static int[] getBass(int bars, int beats, int[] rhythm, int[][] chords)
    {
        int j, index;
        int i           = 0;
        int[] melody    = new int[bars * beats];

        for (; i < bars; i++) {
            for (j = 0; j < beats; j++) {
                index = bars * i + j;

                if (rhythm[index] == Rhythm.MELODY_RELEASE) {
                    melody[index] = chords[i][0] - (Theory.TONES * 2);
                } else {
                    melody[index] = Rhythm.MELODY_REST;
                }
            }
        }

        return melody;

    }
}

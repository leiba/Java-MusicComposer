package dp.leiba.music.creation;

/**
 * Rhythm melody.
 */
public class Melody extends Rhythm
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
        int i           = 0;
        int j           = 0;
        int[] melody    = new int[bars * beats];

        for (; i < bars; i++) {
            for (; j < beats; j++) {
                if (rhythm[i + j] == MELODY_RELEASE) {
                    melody[i + j] = chords[i][0] - (Theory.OCTAVES * 2);
                } else {
                    melody[i + j] = MELODY_REST;
                }
            }
        }

        return melody;

    }
}

package dp.leiba.music.creation;

import dp.leiba.music.tools.ArrayTool;
import dp.leiba.music.tools.MathTool;

import java.util.Arrays;

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
        int j, index, note;
        int i           = 0;
        int[] melody    = new int[bars * beats];

        for (; i < bars; i++) {
            for (j = 0; j < beats; j++) {
                index = bars * i + j;

                if (rhythm[index] != Rhythm.MELODY_REST) {
                    note          = chords[i][chords[i].length - 1];
                    melody[index] = getNeighbourNote(notes, note);
                } else {
                    melody[index] = Rhythm.MELODY_REST;
                }
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

    /**
     * Chords.
     * @param bars Bars.
     *
     * @return Chords.
     */
    public static int[][] getChords(int bars)
    {
        int noteFrom    = Theory.TONES * 4 + 1;
        int noteTo      = noteFrom + Theory.TONES - 1;
        int note        = MathTool.random(noteFrom, noteTo);
        boolean isMajor = MathTool.is();
        int[][] chords  = Theory.getChordHarmony(note, isMajor);

        return Arrays.copyOfRange(ArrayTool.shuffle(chords), 0, bars);
    }

    /**
     * Get neighbour note.
     *
     * @param notes Notes.
     * @param note  Note.
     *
     * @return Note.
     */
    private static int getNeighbourNote(int[] notes, int note)
    {
        int range   = Theory.TONES / 3;
        int shift   = MathTool.random(-range, range);
        int index   = Arrays.asList(notes).indexOf(note) + shift;

        return index > 0 && index < notes.length ? notes[index] : note;
    }
}

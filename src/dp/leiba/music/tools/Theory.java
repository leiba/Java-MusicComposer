package dp.leiba.music.tools;

/**
 * Music theory.
 */
public class Theory {

    public static final int     OCTAVES     = 7;
    public static final int     TONES       = 12;

    public static final double  C0          = 16.35;
    public static final double  INTERVAL    = Math.pow(2.0, 1.0 / 12);

    private static final String[] names = new String[]{
        "C",  "C#", "D",  "D#",
        "E",  "F",  "F#", "G",
        "G#", "A",  "A#", "B"
    };

    /**
     * Get note frequency.
     *
     * @param note Note.
     *
     * @return Frequency.
     */
    public static double getFreq(int note)
    {
        double freq = C0;

        for (int i = 1; i <= note - 1; i++) {
            freq *= INTERVAL;
        }

        return freq;
    }

    /**
     * Get note name.
     *
     * @param note Note.
     *
     * @return Name.
     */
    public static String getName(int note)
    {
        int octave  = getOctave(note);
        int tone    = note - (octave * TONES);

        return names[tone - 1];
    }

    /**
     * Get note octave.
     *
     * @param note Note.
     *
     * @return Octave.
     */
    public static int getOctave(int note) {
        return ((note - (note % TONES)) / TONES + ((note % TONES) > 0 ? 1 : 0)) - 1;
    }
}

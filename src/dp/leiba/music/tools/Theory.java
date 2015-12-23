package dp.leiba.music.tools;

/**
 * Music theory.
 */
public class Theory {

    public static final int     OCTAVES     = 7;
    public static final int     TONES       = 12;

    public static final double  C0          = 16.35;
    public static final double  INTERVAL    = Math.pow(2.0, 1.0 / 12);

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

        for (int i = 0; i <= note; i++) {
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
        int tone    = note - ((octave - 1) * TONES);
        
        String name = Character.toString((char) 65);
        System.out.println(tone);

        if (octave > 1) {

        }

        return "";
    }

    /**
     * Get note octave.
     *
     * @param note Note.
     *
     * @return Octave.
     */
    public static int getOctave(int note) {
        return (note - (note % TONES)) / TONES + ((note % TONES) > 0 ? 1 : 0);
    }
}

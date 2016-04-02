package dp.leiba.music.person;

import java.util.Arrays;

import dp.leiba.music.creation.Rhythm;
import dp.leiba.music.creation.Theory;

/**
 * Person.
 */
public class Person
{
    public static final String NAME = "Bit B";
    public static final String REST = "REST";

    /**
     * Say.
     *
     * @param what What.
     */
    public static void say(String what)
    {
        System.out.println(NAME + ", say: " + what);
    }

    /**
     * Debug.
     *
     * @param name  Name.
     * @param value Value.
     */
    public static void debug(String name, String value)
    {
        System.out.println(NAME + ", debug (" + name + "): " + value);
    }

    /**
     * Debug.
     *
     * @param name Name.
     * @param note Note.
     */
    public static void debug(String name, int note)
    {
        debug(name, Theory.getNoteName(note) + "(" + note + ")");
    }

    /**
     * Debug.
     *
     * @param name Name.
     * @param is   Is.
     */
    public static void debug(String name, boolean is)
    {
        debug(name, is + "");
    }

    /**
     * Debug.
     *
     * @param name  Name.
     * @param notes Notes.
     */
    public static void debug(String name, int[] notes)
    {
        int i           = 0;
        String[] debug  = new String[notes.length];

        for (; i < notes.length; i++) {
            if (notes[i] == Rhythm.RELEASE) {
                debug[i] = REST;
            } else {
                debug[i] = Theory.getNoteName(notes[i]) + "(" + notes[i] + ")";
            }
        }

        debug(name, Arrays.toString(debug));
    }

    /**
     * Debug.
     *
     * @param name  Name.
     * @param notes Notes.
     */
    public static void debug(String name, int[][] notes)
    {
        int i, j;
        String[] line;
        String[] debug  = new String[notes.length];

        for (i = 0; i < notes.length; i++) {
            line = new String[notes[i].length];

            for (j = 0; j < notes[i].length; j++) {
                line[j] = Theory.getNoteName(notes[i][j]) + "(" + notes[i][j] + ")";
            }

            debug[i] = Arrays.toString(line);
        }

        debug(name, Arrays.toString(debug));
    }
}

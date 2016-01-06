package dp.leiba.music;

import java.util.Arrays;

import dp.leiba.music.creation.Composer;
import dp.leiba.music.person.Person;

/**
 * Main.
 */
public class Main
{

    /**
     * Start point.
     *
     * @param args Input arguments.
     */
    public static void main(String[] args)
    {
    	Person.say("Hello");

        Composer composer = new Composer();
        composer.save();
        
        Person.debug("Is major", composer.getIsMajor() + "");
        Person.debug("Note", composer.getNote() + "");
        Person.debug("Notes", Arrays.toString(composer.getNotes()));
        Person.debug("Chords", "");
        Person.debug("Lead", Arrays.toString(composer.getLead()));
        Person.say("Bye");

        /*
        ArrayTool.print(Melody.getChords(4));
        */

        /*
        int[][] chords = new int[][]{
                {33, 35, 38},
                {32, 34, 37},
                {35, 38, 40},
                {37, 40, 42}
        };

        int[] notes = new int[] {
                25, 27, 28, 32, 33, 34, 35, 37, 38, 40, 42, 44, 47, 50, 52, 55
        };

        System.out.println(Arrays.toString(Melody.getLead(4, 4, Rhythm.getRhythmMelody(4, 4), chords, notes)));
        */

        /*
        int[][] chords = new int[][]{
                {33, 35, 38},
                {32, 34, 37},
                {35, 38, 40},
                {37, 40, 42}
        };

        ArrayTool.print(Melody.getPluck(4, 4, Rhythm.getRhythmMelody(4, 4), chords));
        */

        /*
        int[][] chords = new int[][]{
                {33, 35, 38},
                {32, 34, 37},
                {35, 38, 40},
                {37, 40, 42}
        };

        System.out.println(Arrays.toString(Melody.getBass(4, 4, Rhythm.getRhythmBass(4, 4), chords)));
        */

        /*
        System.out.println(Arrays.toString(Rhythm.getRhythmBass(4, 4)));
        */
        
        /*
        Rhythm.getRythmDrums(4, 4);
        */
        
        /*
        int[][] chords = Theory.getChordHarmony(10, false);
        for (int[] chord : chords) {
        	System.out.println(Arrays.toString(chord));
        }
        */        
        
        /*
        System.out.println(Arrays.toString(Theory.getHarmony(21, true, 3)));
		*/

        /*
        int bars        = 4;
        int beats       = 4;
        int[] rhythm    = Rhythm.getRhythmMelody(bars, beats);
        Wav wav         = new Wav();
        double[] wave;

        System.out.println(Arrays.toString(rhythm));
        for (int i = 0; i < rhythm.length; i++) {
            for (int j = 0; j < 10; j++) {
                if (rhythm[i] == Rhythm.MELODY_ATTACK || rhythm[i] == Rhythm.MELODY_RELEASE) {
                    wave = WaveForms.sine(100, 100);

                    if (rhythm[i] == Rhythm.MELODY_RELEASE && j > 5) {
                        wave = WaveForms.rest(100);
                    }

                    wav.setFrames(wave, 100, true);
                } else {
                    wav.setFrames(WaveForms.rest(100), 100, true);
                }
            }
        }
        wav.save("/var/www/say1.wav");
        */


        /*
        Wav wav         = new Wav();
        int amplitude   = 100;
        for(int i = 0; i < 80; i++) {
            wav.setFrames(WaveForms.rest(WaveForms.sine(100, amplitude)), amplitude + 100, true);
        }
        wav.save("/var/www/say1.wav");
        */
    }
}

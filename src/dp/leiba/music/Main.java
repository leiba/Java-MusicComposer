package dp.leiba.music;

import dp.leiba.music.creation.Rhythm;
import dp.leiba.music.creation.Theory;
import dp.leiba.music.tools.Wav;
import dp.leiba.music.tools.WaveForms;

import java.util.Arrays;

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
        System.out.println("Bit B:");
        System.out.println("Bit B: From home.");

        System.out.println(Arrays.toString(Rhythm.getRhythmBass(4, 4)));
        
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

        System.out.println("Bit B, say bye!");
    }
}
